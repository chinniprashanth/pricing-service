package com.sapient.coc.application.pricingservice.feign.client;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import com.sapient.coc.application.pricingservice.bo.vo.AddressFulfillment;
import com.sapient.coc.application.pricingservice.bo.vo.Fulfillment;
import com.sapient.coc.application.pricingservice.feign.fallback.FulfillmentServiceFalBack;

import feign.Headers;
import feign.Param;
import feign.RequestLine;

@FeignClient(name = "fulfillment-service", url = "http://35.241.4.242", fallback = FulfillmentServiceFalBack.class)
@RibbonClient(name = "fulfillment-service")
public interface FulfillmentServiceClient {

	@RequestLine("POST /v1/fulfillment/methods/eligible/")
	@Headers({ "Authorization: {token}", "Accept: application/json" })
	ResponseEntity<Fulfillment> getOrderFulFillmentDeatils(@Param("token") String token,
			@RequestBody AddressFulfillment address);

}

