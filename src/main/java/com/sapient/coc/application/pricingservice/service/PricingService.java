package com.sapient.coc.application.pricingservice.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sapient.coc.application.coreframework.exception.CoCBusinessException;
import com.sapient.coc.application.coreframework.exception.CoCSystemException;
import com.sapient.coc.application.pricingservice.bo.vo.CartResponse;
import com.sapient.coc.application.pricingservice.bo.vo.OrderItem;
import com.sapient.coc.application.pricingservice.bo.vo.OrderPriceResp;

/**
 * Copyright (c) 2019 CommerceOnCloud, PublicisSapient This file is part of
 * CommerceOnCloud project.
 * 
 * @author pooyadav
 */
public interface PricingService {
	
	public static final Logger logger = LoggerFactory.getLogger(PricingService.class);

	/**
	 * This method calculates total, subtotal
	 * 
	 * @param token
	 * @param cartId
	 * @return OrderResponse
	 * @throws CoCBusinessException
	 * @throws CoCSystemException
	 */
	default CartResponse applyCartPricing(String token, String cartId, String client)
			throws CoCBusinessException, CoCSystemException {
		
		CartResponse cartResponse = fetchCartDetails(token, cartId, client);
		if (cartResponse.getTotal() == 0) {
			cartResponse.setTotal(cartResponse.getSubtotal() + cartResponse.getShipping() + cartResponse.getTax());
		}
		return cartResponse;
	}
	
	/**
	 * This method fetches cart details
	 * 
	 * @param token
	 * @param cartId
	 * @return CartResponse
	 * @throws CoCBusinessException
	 * @throws CoCSystemException
	 */
	CartResponse fetchCartDetails(String token, String cartId, String client)
			throws CoCBusinessException, CoCSystemException;

	/**
	 * This method fetches product details
	 * 
	 * @param skuId
	 * @return List<OrderItem>
	 * @throws CoCBusinessException
	 * @throws CoCSystemException
	 */
	List<OrderItem> fetchProductDetails(String skuId) throws CoCBusinessException, CoCSystemException;

	/**
	 * This method calculates order pricing along with shipping price
	 * 
	 * @param authorization
	 * @return OrderPriceResp
	 * @throws CoCBusinessException
	 * @throws CoCSystemException
	 */
	OrderPriceResp calculateOrderPrice(String authorization, String orderId)
			throws CoCBusinessException, CoCSystemException;
	
}
