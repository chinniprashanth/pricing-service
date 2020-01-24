package com.sapient.coc.application.pricingservice.feign.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import com.sapient.coc.application.pricingservice.bo.vo.PromoResponse;
import com.sapient.coc.application.pricingservice.bo.vo.PromotionWrapper;

import feign.Headers;
import feign.Param;
import feign.RequestLine;

/*******************************************************
 * Copyright (c) 2019 CommerceOnCloud, PublicisSapient
 *
 * This file is part of CommerceOnCloud project.
 *
 * CommerceOnCloud can not be copied and/or distributed without the express
 * permission of PublicisSapient
 *******************************************************/

/**
 * Feign for Promotion service calls
 *
 * @author pooyadav
 */
@FeignClient(name = "promotion", url = "http://35.241.4.242/v1")
public interface PromotionServiceClient {

	@RequestLine("POST /promotion/apply/order/")
	@Headers({ "Authorization: {token}", "Accept: application/json", "Content-Type: application/json" })
	ResponseEntity<PromoResponse> applyOrderPromotion(@Param(value = "token") String token,
			@RequestBody PromotionWrapper promotionWrapper);

	@RequestLine("POST /promotion/apply/customer/")
	@Headers({ "Authorization: {token}", "Accept: application/json", "Content-Type: application/json" })
	ResponseEntity<PromoResponse> applyCustomerPromotion(@Param(value = "token") String token,
			@RequestBody PromotionWrapper promotionWrapper);

	@RequestLine("POST /promotion/apply/cart/")
	@Headers({ "Authorization: {token}", "Accept: application/json", "Content-Type: application/json" })
	ResponseEntity applyCartLevelPromotions(@Param(value = "token") String token,
			@RequestBody PromotionWrapper promotionWrapper);

}