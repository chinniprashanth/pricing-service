package com.sapient.coc.application.pricingservice.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sapient.coc.application.pricingservice.bo.vo.CartResponse;
import com.sapient.coc.application.pricingservice.bo.vo.OrderResponse;

/**
 * Copyright (c) 2019 CommerceOnCloud, PublicisSapient This file is part of
 * CommerceOnCloud project.
 * 
 * @author pooyadav
 */
public interface PricingService {
	
	public static final Logger logger = LoggerFactory.getLogger(PricingService.class);

	default OrderResponse applyPromotions(String token, String cartId) {
		
		CartResponse cartResponse = fetchCartDetails(token, cartId);
		OrderResponse orderResponse = new OrderResponse();
		if (cartResponse.getTotal() == 0) {
			cartResponse.setTotal(cartResponse.getSubtotal() + cartResponse.getShipping() + cartResponse.getTax());
		}
		orderResponse.setItem(cartResponse);
		return orderResponse;
	}
	
	// Map<String, List<OrderResponse>> applyShippingPromotion(String orderId);

	// Map<String, List<PromotionFact>> applyItemPromotionForGivenItems(List<String>
	// skuIds, Integer buyQty);
	
	CartResponse fetchCartDetails(String token, String cartId);

	// Map<String, List<PromotionFact>>
	// applyCategoryAndItemPromotionForGivenItems(String skuIds, Integer buyQty);

	// Map<String, OrderItem> getPromotionalProduct();
	
}
