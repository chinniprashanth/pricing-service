package com.sapient.coc.application.pricingservice.feign.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sapient.coc.application.pricingservice.bo.vo.Order;

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
 * 
 * @author surrao
 *
 */
@Configuration
@FeignClient(name = "orderservice", url = "${application.order.client.url}")
public interface OrderServiceClient {

	/**
	 * @param userId
	 * @return
	 */
	@RequestLine("GET /v1/order/")
	@RequestMapping(value = "/v1/order/", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@Headers({ "Authorization: {token}", "Accept: application/json", "Content-Type: application/json" })	
	ResponseEntity<Order> getCurrentOrder(@Param("token") String token);

}