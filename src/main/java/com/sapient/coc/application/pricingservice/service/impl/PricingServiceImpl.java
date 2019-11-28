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
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.sapient.coc.application.pricingservice.bo.vo.CartItem;
import com.sapient.coc.application.pricingservice.bo.vo.CartResp;
import com.sapient.coc.application.pricingservice.bo.vo.CartResponse;
import com.sapient.coc.application.pricingservice.bo.vo.Data;
import com.sapient.coc.application.pricingservice.bo.vo.Fulfillment;
import com.sapient.coc.application.pricingservice.bo.vo.FulfillmentItem;
import com.sapient.coc.application.pricingservice.bo.vo.OrderItem;
import com.sapient.coc.application.pricingservice.bo.vo.OrderResponse;
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

	@Override
	public CartResponse fetchCartDetails(String token, String cartId) {

		CartResp cartResp = cartInfoServiceClient.getOrderDetails(token, cartId);
		List<CartItem> cartItems = Arrays.asList(cartResp.getData());
		// List<CartItem> cartItems = cartResp.getData();
		List<OrderItem> orderItems = new ArrayList<OrderItem>();
		List<String> skuIds = new ArrayList<String>();
		CartResponse cartResponse = new CartResponse();
		Map<String, Integer> qtySkuId = new HashMap<String,Integer>();

		cartItems.forEach(cartItem -> {
			skuIds.add(cartItem.getSkuId());
			qtySkuId.put(cartItem.getSkuId(), cartItem.getQuantity());

		});

		String ids = skuIds.stream().collect(Collectors.joining(","));
		List<Sku> itemDetails = productInfoServiceClient.getProductDetailsForSapecificItems(ids);
		itemDetails.forEach(itemDetail -> {
			OrderItem orderItem = new OrderItem();
			orderItem.setItemId(itemDetail.getId());
			orderItem.setQty(qtySkuId.get(itemDetail.getId()));
			orderItem.setSalePrice(new Double(itemDetail.getSaleprice()));
			orderItem.setListPrice(new Double(itemDetail.getListprice()));
			orderItem.setSkuId(itemDetail.getId());
			orderItem.setItemsTotalPrice(orderItem.getListPrice() * orderItem.getQty());
			orderItem.setItemDiscountedPrice(orderItem.getSalePrice() * orderItem.getQty());
			orderItem.setProductId(itemDetail.getParentproductid());
			orderItem.setItemDescription(itemDetail.getDescription());
			orderItem.setItemName(itemDetail.getName());
			orderItem.setImageUrl(itemDetail.getImages().get(0).getUrl());
			orderItems.add(orderItem);
		});
		if (cartResponse.getSubtotal() == 0) {
			orderItems.forEach(orderItem -> {
				cartResponse.setActualTotal(orderItem.getItemsTotalPrice() + cartResponse.getActualTotal());
				cartResponse.setSubtotal(
						orderItem.getItemDiscountedPrice() + cartResponse.getSubtotal());
			});
		}
		cartResponse.setItems(orderItems);
		return cartResponse;
	}

	@Override
	public OrderResponse calculateShipping(String token) {
		OrderResponse orderResp = new OrderResponse();
		ResponseEntity<Fulfillment> fulfillmentResp =
		  fulfillmentServiceClient.getOrderFulFillmentDeatils(token);
		Data fulfillmentData = fulfillmentResp.getBody().getData();
		List<FulfillmentItem> itemList = fulfillmentData.getItems();
		Map<String, ShippingResponse> shippingDetailsMap = new HashMap<String, ShippingResponse>();
		Map<String, Double> priceMap = new HashMap<String, Double>();
		List<String> skuIds = new ArrayList<String>();
		itemList.forEach(fulfillmentItem -> {
			ShippingResponse shipResponse = new ShippingResponse();
			shipResponse.setShippingCost(fulfillmentItem.getPrice());
			shipResponse.setShippingMethod(fulfillmentItem.getFulfillmentMethod());
			shippingDetailsMap.put(fulfillmentItem.getSkuId(), shipResponse);
			skuIds.add(fulfillmentItem.getSkuId());
			priceMap.put(fulfillmentItem.getFulfillmentMethod(), fulfillmentItem.getPrice());
		});
		String ids = skuIds.stream().collect(Collectors.joining(","));
		List<OrderItem> orderItems = fetchProductDetails(ids);
		CartResponse cartResponse = new CartResponse();
		double total = 0;
		for (Map.Entry<String, Double> entry : priceMap.entrySet()) {
			total = entry.getValue() + total;
		}
		cartResponse.setShipping(total);
		orderItems.forEach(orderItem -> {
			orderItem.setShippingMethod(shippingDetailsMap.get(orderItem.getSkuId()).getShippingMethod());
			orderItem.setShippingPrice(shippingDetailsMap.get(orderItem.getSkuId()).getShippingCost());
			if (orderItem.getQty() == 0) {
				orderItem.setQty(1);
			}
			orderItem.setItemsTotalPrice(orderItem.getListPrice() * orderItem.getQty());
			orderItem.setItemDiscountedPrice(orderItem.getSalePrice() * orderItem.getQty());

			cartResponse.setActualTotal(orderItem.getItemsTotalPrice() + cartResponse.getActualTotal());
			cartResponse
					.setSubtotal(orderItem.getItemDiscountedPrice() + cartResponse.getSubtotal()
					);
		});
		cartResponse.setSubtotal(cartResponse.getSubtotal() + total);
		cartResponse.setItems(orderItems);
		orderResp.setItem(cartResponse);
		return orderResp;
	}

	@Override
	public List<OrderItem> fetchProductDetails(String skuId) {
		List<OrderItem> orderItems = new ArrayList<OrderItem>();
		List<Sku> itemDetails = productInfoServiceClient.getProductDetailsForSapecificItems(skuId);
		itemDetails.forEach(itemDetail -> {
			OrderItem orderItem = new OrderItem();
			orderItem.setItemId(itemDetail.getId());
			orderItem.setSalePrice(new Double(itemDetail.getSaleprice()));
			orderItem.setListPrice(new Double(itemDetail.getListprice()));
			orderItem.setSkuId(itemDetail.getId());
			// orderItem.setItemsTotalPrice(orderItem.getListPrice() * orderItem.getQty());
			// orderItem.setItemDiscountedPrice(orderItem.getSalePrice() *
			// orderItem.getQty());
			orderItem.setProductId(itemDetail.getParentproductid());
			orderItem.setItemDescription(itemDetail.getDescription());
			orderItem.setItemName(itemDetail.getName());
			orderItem.setImageUrl(itemDetail.getImages().get(0).getUrl());
			orderItems.add(orderItem);
		});
		return orderItems;
	}
	



}
