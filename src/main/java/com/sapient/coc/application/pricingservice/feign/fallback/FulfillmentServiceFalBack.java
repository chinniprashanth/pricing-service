package com.sapient.coc.application.pricingservice.feign.fallback;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.sapient.coc.application.pricingservice.bo.vo.AddressFulfillment;
import com.sapient.coc.application.pricingservice.bo.vo.Fulfillment;
import com.sapient.coc.application.pricingservice.feign.client.FulfillmentServiceClient;

@Component
public class FulfillmentServiceFalBack implements FulfillmentServiceClient {

	@Override
	public ResponseEntity<Fulfillment> getOrderFulFillmentDeatils(String token, AddressFulfillment address) {
		// TODO Auto-generated method stub
		return null;
	}

}
