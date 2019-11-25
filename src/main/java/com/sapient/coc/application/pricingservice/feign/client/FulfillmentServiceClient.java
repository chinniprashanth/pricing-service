package com.sapient.coc.application.pricingservice.feign.client;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;

import com.sapient.coc.application.pricingservice.bo.vo.Fulfillment;
import com.sapient.coc.application.pricingservice.feign.FeignConfigurationPricing;
import com.sapient.coc.application.pricingservice.feign.fallback.FulfillmentServiceFalBack;

import feign.Headers;
import feign.Param;
import feign.RequestLine;

@FeignClient(name = "fulfillment-service", url = "http://localhost:12787", fallback = FulfillmentServiceFalBack.class, configuration = FeignConfigurationPricing.class)
@RibbonClient(name = "fulfillment-service")
public interface FulfillmentServiceClient {

	@RequestLine("GET v1/fulfillment/methods")
	@Headers({ "Authorization:{token}", "Accept: application/json" })
	ResponseEntity<Fulfillment> getOrderFulFillmentDeatils(@Param("token") String token);

}

