package com.sapient.coc.application.pricingservice.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sapient.coc.application.coreframework.exception.CoCBusinessException;
import com.sapient.coc.application.coreframework.exception.CoCSystemException;
import com.sapient.coc.application.pricingservice.bo.vo.CartResponse;
import com.sapient.coc.application.pricingservice.bo.vo.OrderItem;
import com.sapient.coc.application.pricingservice.bo.vo.OrderPriceResp;
import com.sapient.coc.application.pricingservice.bo.vo.OrderResponse;

/**
 * Copyright (c) 2019 CommerceOnCloud, PublicisSapient This file is part of
 * CommerceOnCloud project.
 * 
 * @author pooyadav
 */
public interface PricingService {
	
	public static final Logger logger = LoggerFactory.getLogger(PricingService.class);

	/**
	 * @param token
	 * @param cartId
	 * @return OrderResponse
	 * @throws CoCBusinessException
	 * @throws CoCSystemException
	 */
	default OrderResponse applyPromotions(String token, String cartId) throws CoCBusinessException, CoCSystemException {
		
		CartResponse cartResponse = fetchCartDetails(token, cartId);
		OrderResponse orderResponse = new OrderResponse();
		if (cartResponse.getTotal() == 0) {
			cartResponse.setTotal(cartResponse.getSubtotal() + cartResponse.getShipping() + cartResponse.getTax());
		}
		orderResponse.setItem(cartResponse);
		return orderResponse;
	}
	
	/**
	 * @param token
	 * @param cartId
	 * @return CartResponse
	 * @throws CoCBusinessException
	 * @throws CoCSystemException
	 */
	CartResponse fetchCartDetails(String token, String cartId) throws CoCBusinessException, CoCSystemException;

	/**
	 * @param skuId
	 * @return List<OrderItem>
	 * @throws CoCBusinessException
	 * @throws CoCSystemException
	 */
	List<OrderItem> fetchProductDetails(String skuId) throws CoCBusinessException, CoCSystemException;

	/**
	 * @param authorization
	 * @return OrderPriceResp
	 * @throws CoCBusinessException
	 * @throws CoCSystemException
	 */
	OrderPriceResp calculateShipping(String authorization) throws CoCBusinessException, CoCSystemException;
	
}
