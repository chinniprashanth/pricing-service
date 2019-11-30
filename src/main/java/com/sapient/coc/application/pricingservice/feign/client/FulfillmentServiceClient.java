package com.sapient.coc.application.pricingservice.feign.client;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sapient.coc.application.pricingservice.bo.vo.AddressFulfillment;
import com.sapient.coc.application.pricingservice.bo.vo.Fulfillment;
import com.sapient.coc.application.pricingservice.feign.fallback.FulfillmentServiceFalBack;

import feign.Headers;
import feign.Param;
import feign.RequestLine;

@FeignClient(name = "fulfillment-service", url = "http://35.241.4.242", fallback = FulfillmentServiceFalBack.class)
@RibbonClient(name = "fulfillment-service")
public interface FulfillmentServiceClient {

	@RequestLine("GET /v1/fulfillment/methods/eligible/")
	@Headers({ "Authorization: {token}", "Accept: application/json" })
	@RequestMapping(value = " /v1/fulfillment/methods/eligible/", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<Fulfillment> getOrderFulFillmentDeatils(@Param("token") String token,
			@RequestBody AddressFulfillment address);

}

