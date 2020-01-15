package com.sapient.coc.application.pricingservice.feign.client;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;

import com.sapient.coc.application.pricingservice.bo.vo.CartResp;
import com.sapient.coc.application.pricingservice.feign.fallback.CartInfoServiceFallBack;

import feign.Headers;
import feign.Param;
import feign.RequestLine;

/**
 * Copyright (c) 2019 CommerceOnCloud, PublicisSapient This file is part of
 * CommerceOnCloud project.
 * 
 * @author pooyadav
 */
@FeignClient(name = "cartservice", url = "${application.cart.client.url}", fallback = CartInfoServiceFallBack.class)
@RibbonClient(name = "cartservice")
public interface CartInfoServiceClient {

	/**
	 * This method returns the order details for given order id
	 * 
	 * @param query
	 * @return
	 */
	@RequestLine("GET /v1/cart/{cartId}")
	@Headers({ "Authorization:{token}", "Accept: application/json" })
	CartResp getOrderDetails(@Param("token") String token, @Param("cartId") String cartId);
}
