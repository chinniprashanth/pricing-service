package com.sapient.coc.application.pricingservice.feign.client;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sapient.coc.application.pricingservice.bo.vo.Tax;

import feign.Headers;
import feign.Param;
import feign.RequestLine;

/**
 * Copyright (c) 2019 CommerceOnCloud, PublicisSapient This file is part of
 * CommerceOnCloud project.
 * 
 * @author pooyadav
 */
@FeignClient(name = "taxService", url = "${application.tax.client.url}")
@RibbonClient(name = "taxService")
public interface TaxServiceClient {

	/**
	 * This method returns the shipping details for given orderId
	 * 
	 * @param query
	 * @return
	 */
	@RequestLine("GET /v1/tax/{zipCode}/{amount}")
	@Headers({ "Authorization: bearer {token}", "Accept: application/json", "Content-Type: application/json" })
	@RequestMapping(value = "/v1/tax/{zipCode}/{amount}", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Tax> getTax(@Param("zipCode") String zipCode, @Param("amount") String amount);
}
