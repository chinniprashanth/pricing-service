package com.sapient.coc.application.pricingservice.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.sapient.coc.application.coreframework.bo.Money;
import com.sapient.coc.application.coreframework.exception.CoCSystemException;
import com.sapient.coc.application.pricingservice.bo.vo.CartItem;
import com.sapient.coc.application.pricingservice.bo.vo.CartResp;
import com.sapient.coc.application.pricingservice.bo.vo.CartResponse;
import com.sapient.coc.application.pricingservice.bo.vo.Data;
import com.sapient.coc.application.pricingservice.bo.vo.Fulfillment;
import com.sapient.coc.application.pricingservice.bo.vo.FulfillmentItem;
import com.sapient.coc.application.pricingservice.bo.vo.OrderItem;
import com.sapient.coc.application.pricingservice.bo.vo.OrderItemPrice;
import com.sapient.coc.application.pricingservice.bo.vo.OrderKafkaResponse;
import com.sapient.coc.application.pricingservice.bo.vo.OrderPriceResp;
import com.sapient.coc.application.pricingservice.bo.vo.ShippingResponse;
import com.sapient.coc.application.pricingservice.bo.vo.Sku;
import com.sapient.coc.application.pricingservice.feign.client.CartInfoServiceClient;
import com.sapient.coc.application.pricingservice.feign.client.FulfillmentServiceClient;
import com.sapient.coc.application.pricingservice.feign.client.ProductInfoServiceClient;
import com.sapient.coc.application.pricingservice.message.PricingEventPublisher;
import com.sapient.coc.application.pricingservice.service.PricingService;

/**
 * Copyright (c) 2019 CommerceOnCloud, PublicisSapient This file is part of
 * CommerceOnCloud project.
 * 
 * @author pooyadav
 */
@Component
public class PricingServiceImpl implements PricingService {

	public static final Logger logger = LoggerFactory.getLogger(PricingServiceImpl.class);
	
	private static final String COMMA = ",";
	private static final String PROMOTION_APPLIED = "After applying promotion:";


	@Autowired
	ProductInfoServiceClient productInfoServiceClient;

	@Autowired
	CartInfoServiceClient cartInfoServiceClient;

	@Autowired
	FulfillmentServiceClient fulfillmentServiceClient;

	@Autowired
	PricingEventPublisher pricingEventPublisher;

	@Value(value = "${spring.kafka.message.topic.name}")
	private String topicName;

	@Override
	public CartResponse fetchCartDetails(String token, String cartId) {

		CartResp cartResp = cartInfoServiceClient.getOrderDetails(token, cartId);
		List<CartItem> cartItems = Arrays.asList(cartResp.getData()); //
		List<OrderItem> orderItems = new ArrayList<OrderItem>();
		List<String> skuIds = new ArrayList<String>();
		CartResponse cartResponse = new CartResponse();
		Map<String, Integer> qtySkuId = new HashMap<String, Integer>();

		cartItems.forEach(cartItem -> {
			skuIds.add(cartItem.getSkuId());
			qtySkuId.put(cartItem.getSkuId(), cartItem.getQuantity());

		});

		String ids = skuIds.stream().collect(Collectors.joining(","));
		List<Sku> itemDetails = productInfoServiceClient.getProductDetailsForSapecificItems(ids);
		itemDetails.forEach(itemDetail -> {
			OrderItem orderItem = new OrderItem();
			orderItem.setQuantity(qtySkuId.get(itemDetail.getId()));
			orderItem.setSalePrice(new Double(itemDetail.getSaleprice()));
			orderItem.setListPrice(new Double(itemDetail.getListprice()));
			orderItem.setSkuId(itemDetail.getId());
			orderItem.setItemsTotalPrice(orderItem.getListPrice() * orderItem.getQuantity());
			orderItem.setItemDiscountedPrice(orderItem.getSalePrice() * orderItem.getQuantity());
			orderItem.setProductId(itemDetail.getParentproductid());
			orderItem.setItemDescription(itemDetail.getDescription());
			orderItem.setName(itemDetail.getName());
			orderItem.setImageUrl(itemDetail.getImages().get(0).getUrl());
			orderItems.add(orderItem);
		});
		if (cartResponse.getSubtotal() == 0) {
			orderItems.forEach(orderItem -> {
				cartResponse.setActualTotal(orderItem.getItemsTotalPrice() + cartResponse.getActualTotal());
				cartResponse.setSubtotal(orderItem.getItemDiscountedPrice() + cartResponse.getSubtotal());
			});
		}
		cartResponse.setItems(orderItems);
		return cartResponse;

		}

	@Override
	public OrderPriceResp calculateShipping(String token) {
		OrderPriceResp orderResp = new OrderPriceResp();
		OrderKafkaResponse orderKafkaResp = new OrderKafkaResponse();
		ResponseEntity<Fulfillment> fulfillmentResp =
		  fulfillmentServiceClient.getOrderFulFillmentDeatils(token);
		Data fulfillmentData = fulfillmentResp.getBody().getData();
		List<FulfillmentItem> itemList = fulfillmentData.getItems();
		Map<String, ShippingResponse> shippingDetailsMap = new HashMap<String, ShippingResponse>();
		Map<String, Double> priceMap = new HashMap<String, Double>();
		List<String> skuIds = new ArrayList<String>();
		itemList.forEach(fulfillmentItem -> {
			ShippingResponse shipResponse = new ShippingResponse();
			shipResponse.setShippingCost(new Double(10));
			shipResponse.setShippingMethod(fulfillmentItem.getFulfillmentMethod());
			shippingDetailsMap.put(fulfillmentItem.getSkuId(), shipResponse);
			skuIds.add(fulfillmentItem.getSkuId());
			priceMap.put(fulfillmentItem.getFulfillmentMethod(), new Double(10));
		});
		String ids = skuIds.stream().collect(Collectors.joining(","));
		List<OrderItem> orderItems = fetchProductDetails(ids);
		List<OrderItemPrice> orderItemPrice = new ArrayList<OrderItemPrice>();

		double total = 0;
		for (Map.Entry<String, Double> entry : priceMap.entrySet()) {
			total = entry.getValue() + total;
		}
		orderResp.setShipping(new Money("USD", total));
		orderResp.setSubmittedTime(fulfillmentData.getCreatedAt());
		orderResp.setId(fulfillmentData.getId());
		orderItems.forEach(orderItem -> {
			OrderItemPrice itemPrice = new OrderItemPrice();
			orderItem.setShippingMethod(shippingDetailsMap.get(orderItem.getSkuId()).getShippingMethod());
			orderItem.setShippingPrice(shippingDetailsMap.get(orderItem.getSkuId()).getShippingCost());
			if (orderItem.getQuantity() == 0) {
				orderItem.setQuantity(1);
			}
			orderItem.setItemsTotalPrice(orderItem.getListPrice() * orderItem.getQuantity());
			orderItem.setItemDiscountedPrice(orderItem.getSalePrice() * orderItem.getQuantity());

			if (null != orderResp.getActualTotal()) {
				orderResp.setActualTotal(
						new Money("USD", (orderItem.getItemsTotalPrice() + orderResp.getActualTotal().getAmount())));
			} else {
				orderResp.setActualTotal(new Money("USD", (orderItem.getItemsTotalPrice())));
			}
			if (null != orderResp.getSubtotal()) {
				orderResp.setSubtotal(
						new Money("USD", orderItem.getItemDiscountedPrice() + orderResp.getSubtotal().getAmount()));
			} else {
				orderResp.setSubtotal(new Money("USD", orderItem.getItemDiscountedPrice()));
			}
			itemPrice.setItemPrice(new Money("USD", orderItem.getItemPrice()));
			itemPrice.setItemsTotalPrice(new Money("USD", orderItem.getItemsTotalPrice()));
			itemPrice.setName(orderItem.getName());
			itemPrice.setProductId(orderItem.getProductId());
			itemPrice.setQuantity(orderItem.getQuantity());
			itemPrice.setShippingMethod(orderItem.getShippingMethod());
			itemPrice.setShippingPrice(orderItem.getShippingPrice());
			itemPrice.setSkuId(orderItem.getSkuId());
			orderItemPrice.add(itemPrice);

		});
		orderResp.setSubtotal(new Money("USD", orderResp.getSubtotal().getAmount() + total));
		orderKafkaResp.setActualTotal(orderResp.getActualTotal());
		orderKafkaResp.setId(orderResp.getId());
		orderKafkaResp.setOrderItems(orderItemPrice);
		orderKafkaResp.setShipping(orderResp.getShipping());
		orderKafkaResp.setSubtotal(orderResp.getSubtotal());
		orderKafkaResp.setTotal(orderResp.getTotal());
		orderKafkaResp.setTotalDiscount(orderResp.getTotalDiscount());
		orderKafkaResp.setUserId(token);
		orderResp.setOrderItems(orderItems);

		/*
		 * try { sendMessage(orderKafkaResp); } catch (CoCSystemException e) { // TODO
		 * Auto-generated catch block e.printStackTrace(); }
		 */
		return orderResp;
	}

	@Override
	public List<OrderItem> fetchProductDetails(String skuId) {
		List<OrderItem> orderItems = new ArrayList<OrderItem>();
		List<Sku> itemDetails = productInfoServiceClient.getProductDetailsForSapecificItems(skuId);
		itemDetails.forEach(itemDetail -> {
			OrderItem orderItem = new OrderItem();
			orderItem.setSalePrice(new Double(itemDetail.getSaleprice()));
			orderItem.setListPrice(new Double(itemDetail.getListprice()));
			orderItem.setSkuId(itemDetail.getId());
			// orderItem.setItemsTotalPrice(orderItem.getListPrice() * orderItem.getQty());
			// orderItem.setItemDiscountedPrice(orderItem.getSalePrice() *
			// orderItem.getQty());
			orderItem.setProductId(itemDetail.getParentproductid());
			orderItem.setItemDescription(itemDetail.getDescription());
			orderItem.setName(itemDetail.getName());
			orderItem.setImageUrl(itemDetail.getImages().get(0).getUrl());
			orderItems.add(orderItem);
		});
		return orderItems;
	}
	
	public void sendMessage(OrderKafkaResponse cart) throws CoCSystemException {
		/*
		 * 
		 * logger.debug("Sending message= {}", cart.getActualTotal());
		 * pricingEventPublisher.sendMessage(topicName, cart);
		 */}



}
