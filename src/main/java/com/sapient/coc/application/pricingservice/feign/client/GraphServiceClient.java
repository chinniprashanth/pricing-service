package com.sapient.coc.application.pricingservice.feign.client;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sapient.coc.application.pricingservice.bo.vo.NiemenRequest;
import com.sapient.coc.application.pricingservice.service.impl.NiemenResponse;

import feign.Headers;
import feign.RequestLine;

/**
 * Copyright (c) 2019 CommerceOnCloud, PublicisSapient This file is part of
 * CommerceOnCloud project.
 * 
 * @author pooyadav
 */
@FeignClient(name = "graphql", url = "http://35.241.4.242")
@RibbonClient(name = "graphql")
public interface GraphServiceClient {

	/**
	 * This method returns the product details from Niemen client API
	 * 
	 * @param query
	 * @return
	 */
	@RequestLine("POST /graphql/")
	@Headers({ "Accept: application/json", "Content-Type: application/json" })
	@RequestMapping(value = "/graphql/", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public NiemenResponse getNiemenProduct(@RequestBody NiemenRequest niemenRequest);
}
