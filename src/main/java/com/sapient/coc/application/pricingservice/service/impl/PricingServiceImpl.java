package com.sapient.coc.application.pricingservice.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
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
import com.sapient.coc.application.coreframework.exception.CoCBusinessException;
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
	
	private static final String ERROR_PRODUCT_DETAIL_MISSING = "Product detail is missing for the skuId";
	private static final String ERROR_GETTING_CART = "Can't Get current cart from cart service";
	private static final String ERROR_FULFILLMENT_MISSING = "Can't get fulfillment details for the token";
	private static final String NO_SKU = "No sku id present in cart";


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
	public CartResponse fetchCartDetails(String token, String cartId) throws CoCBusinessException, CoCSystemException {

		CartResp cartResp = null;
		CartResponse cartResponse = new CartResponse();
		try {
			cartResp = cartInfoServiceClient.getOrderDetails(token, cartId);
		} catch (Exception exc) {
			logger.error(ERROR_GETTING_CART);
			throw new CoCSystemException(ERROR_GETTING_CART);
		}
		if (null != cartResp && null != cartResp.getData()) {
		List<CartItem> cartItems = Arrays.asList(cartResp.getData()); //
		List<OrderItem> orderItems = new ArrayList<OrderItem>();
		List<String> skuIds = new ArrayList<String>();
		Map<String, Integer> qtySkuId = new HashMap<String, Integer>();

		cartItems.forEach(cartItem -> {
			skuIds.add(cartItem.getSkuId());
			qtySkuId.put(cartItem.getSkuId(), cartItem.getQuantity());

		});

		String ids = skuIds.stream().collect(Collectors.joining(","));
			if (!ids.isEmpty()) {
			List<Sku> itemDetails = new ArrayList<Sku>();
		try {
			itemDetails = productInfoServiceClient.getProductDetailsForSapecificItems(ids);
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
						cartResponse.setItems(orderItems);
					}
		} catch (Exception e) {
				logger.error(ERROR_PRODUCT_DETAIL_MISSING);
				throw new CoCSystemException(ERROR_PRODUCT_DETAIL_MISSING);
		}

			} else {
				logger.error(NO_SKU);
				throw new CoCSystemException(NO_SKU);
			}

		} else {
			logger.error(ERROR_GETTING_CART);
			throw new CoCSystemException(ERROR_GETTING_CART);
		}
		return cartResponse;
	}

	@Override
	public OrderPriceResp calculateShipping(String token) throws CoCBusinessException, CoCSystemException {
		OrderPriceResp orderResp = new OrderPriceResp();
		OrderKafkaResponse orderKafkaResp = null;
		ResponseEntity<Fulfillment> fulfillmentResp = fulfillmentServiceClient.getOrderFulFillmentDeatils(token);
		Data fulfillmentData = fulfillmentResp.getBody().getData();
		List<FulfillmentItem> itemList = fulfillmentData.getItems();
		Map<String, ShippingResponse> shippingDetailsMap = new HashMap<String, ShippingResponse>();
		Map<String, Double> priceMap = new HashMap<String, Double>();
		Map<String, Integer> skuQuantityMap = new HashMap<String, Integer>();
		List<String> skuIds = new ArrayList<String>();
		itemList.forEach(fulfillmentItem -> {
			ShippingResponse shipResponse = new ShippingResponse();
			shipResponse.setShippingCost(fulfillmentItem.getPrice());
			shipResponse.setShippingMethod(fulfillmentItem.getFulfillmentMethod());
			shippingDetailsMap.put(fulfillmentItem.getSkuId(), shipResponse);
			skuIds.add(fulfillmentItem.getSkuId());
			priceMap.put(fulfillmentItem.getFulfillmentMethod(), fulfillmentItem.getPrice());
			skuQuantityMap.put(fulfillmentItem.getSkuId(), fulfillmentItem.getQuantity());

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
			OrderItemPrice itemPrice = null;
			orderItem.setShippingMethod(shippingDetailsMap.get(orderItem.getSkuId()).getShippingMethod());
			orderItem.setShippingPrice(shippingDetailsMap.get(orderItem.getSkuId()).getShippingCost());
			orderItem.setQuantity(skuQuantityMap.get(orderItem.getSkuId()));
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
			itemPrice = new OrderItemPrice(orderItem.getName(), orderItem.getProductId(), orderItem.getSkuId(),
					orderItem.getShippingMethod(), orderItem.getShippingPrice(), orderItem.getQuantity(),
					new Money("USD", orderItem.getItemPrice()), new Money("USD", orderItem.getItemsTotalPrice()));
			orderItemPrice.add(itemPrice);

		});
		orderResp.setSubtotal(new Money("USD", orderResp.getSubtotal().getAmount() + total));
		orderKafkaResp = new OrderKafkaResponse("P", token, orderItemPrice, fulfillmentData.getCreatedAt(), new Date(),
				orderResp.getSubtotal(), orderResp.getTotal(), orderResp.getActualTotal(), orderResp.getShipping(),
				orderResp.getTotalDiscount(), new Money("USD", 0.0), orderResp.getId());
		orderResp.setOrderItems(orderItems);
		try {
			sendMessage(orderKafkaResp);
		} catch (CoCSystemException e) {
			logger.error("Error publishing Kafka messgae");
			throw new CoCSystemException("Error publishing Kafka messgae");
		}
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
			orderItem.setProductId(itemDetail.getParentproductid());
			orderItem.setItemDescription(itemDetail.getDescription());
			orderItem.setName(itemDetail.getName());
			orderItem.setImageUrl(itemDetail.getImages().get(0).getUrl());
			orderItem.setItemId(itemDetail.getId());
			orderItems.add(orderItem);
		});
		return orderItems;
	}
	
	public void sendMessage(OrderKafkaResponse cart) throws CoCSystemException {

		logger.debug("Sending order pricing message= {}", cart.getActualTotal());
		pricingEventPublisher.sendMessage(topicName, cart);
	}



}
