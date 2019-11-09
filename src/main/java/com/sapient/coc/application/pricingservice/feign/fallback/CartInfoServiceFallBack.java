package com.sapient.coc.application.pricingservice.feign.fallback;

import org.springframework.stereotype.Component;

import com.sapient.coc.application.pricingservice.bo.vo.CartResp;
import com.sapient.coc.application.pricingservice.feign.client.CartInfoServiceClient;

/**
 * Copyright (c) 2019 CommerceOnCloud, PublicisSapient This file is part of
 * CommerceOnCloud project.
 * 
 * @author pooyadav
 */
@Component
public class CartInfoServiceFallBack implements CartInfoServiceClient {

	@Override
	public CartResp getOrderDetails(String token, String cartId) {
		return new CartResp();
	}

}
