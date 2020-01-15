package com.sapient.coc.application.pricingservice.feign.client;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sapient.coc.application.pricingservice.bo.vo.BillingAddress;

import feign.Headers;
import feign.Param;
import feign.RequestLine;

/**
 * Copyright (c) 2019 CommerceOnCloud, PublicisSapient This file is part of
 * CommerceOnCloud project.
 * 
 * @author pooyadav
 */
@FeignClient(name = "addressService", url = "${application.address.client.url}")
@RibbonClient(name = "addressService")
public interface AddressServiceClient {

	/**
	 * This method returns the shipping details for given orderId
	 * 
	 * @param query
	 * @return
	 */
	@RequestLine("GET /v1/address/shipping/order/{orderId}")
	@Headers({ "Authorization: {token}", "Accept: application/json", "Content-Type: application/json" })
	@RequestMapping(value = "/v1/address/shipping/order/{orderId}", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BillingAddress> getShippingAddress(@Param("orderId") String orderId,
			@Param("token") String token);
}
