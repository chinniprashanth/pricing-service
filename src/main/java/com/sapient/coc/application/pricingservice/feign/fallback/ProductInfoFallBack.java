package com.sapient.coc.application.pricingservice.feign.fallback;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Component;

import com.sapient.coc.application.pricingservice.bo.vo.Product;
import com.sapient.coc.application.pricingservice.bo.vo.ProductDetail;
import com.sapient.coc.application.pricingservice.bo.vo.Sku;
import com.sapient.coc.application.pricingservice.feign.client.ProductInfoServiceClient;

/**
 * Copyright (c) 2019 CommerceOnCloud, PublicisSapient This file is part of
 * CommerceOnCloud project.
 * 
 * @author pooyadav
 */
@Component
public class ProductInfoFallBack implements ProductInfoServiceClient {

	@Override
	public List<Sku> getProductDetailsForSapecificItems(String skuIds) {

		return new ArrayList<Sku>();
	}

	@Override
	public List<ProductDetail> getProductDetailsForSapecificProducts(String id) {
		return Collections.emptyList();
	}

	@Override
	public Sku getItemDetails(String id) {
		// TODO Auto-generated method stub
		return new Sku();
	}

	@Override
	public Product getProductDetails(String id) {
		// TODO Auto-generated method stub
		return null;
	}

}
