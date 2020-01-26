package com.sapient.coc.application.pricingservice.feign.client;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sapient.coc.application.pricingservice.bo.vo.Fulfillment;
import com.sapient.coc.application.pricingservice.feign.fallback.FulfillmentServiceFalBack;

import feign.Headers;
import feign.Param;
import feign.RequestLine;

@FeignClient(name = "fulfillment-service", url = "${application.fulfillment.client.url}", fallback = FulfillmentServiceFalBack.class)
@RibbonClient(name = "fulfillment-service")
public interface FulfillmentServiceClient {

	/**
	 * The method fetches fulfillment details(chosen shipping method and price) for
	 * a particular order
	 * 
	 * @param token
	 * @return
	 */
	@RequestLine("GET /v1/fulfillment/methods/")
	@Headers({ "Authorization: {token}", "Accept: application/json" })
	@RequestMapping(value = " /v1/fulfillment/methods/", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<Fulfillment> getOrderFulFillmentDeatils(@Param("token") String token);

	/**
	 * The method fetches fulfillment details(chosen shipping method and price) for
	 * a particular order based on the orderId
	 * 
	 * @param token
	 * @param orderId
	 * @return
	 */
	@RequestLine("GET /v1/fulfillment/methods/{orderId}")
	@Headers({ "Authorization: {token}", "Accept: application/json" })
	@RequestMapping(value = " /v1/fulfillment/methods/{orderId}", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<Fulfillment> getOrderFulFillmentDeatils(@Param("token") String token,
			@Param("orderId") String orderId);

}

