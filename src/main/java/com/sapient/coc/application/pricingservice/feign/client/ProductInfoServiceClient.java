package com.sapient.coc.application.pricingservice.feign.client;

import java.util.List;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import com.sapient.coc.application.pricingservice.bo.vo.ProductDetail;
import com.sapient.coc.application.pricingservice.bo.vo.Sku;
import com.sapient.coc.application.pricingservice.feign.FeignConfigurationPricing;

import feign.Headers;
import feign.Param;
import feign.RequestLine;

/**
 * Copyright (c) 2019 CommerceOnCloud, PublicisSapient This file is part of
 * CommerceOnCloud project.
 * 
 * @author pooyadav
 */
@FeignClient(name = "productDetail", configuration = FeignConfigurationPricing.class,
//, fallback = ProductInfoFallBack.class 
		url = "${application.product.client.url}")
@RibbonClient(name="product-detail")
public interface ProductInfoServiceClient {

	/**
	 * This method returns the product details for multiple items
	 * 
	 * @param query
	 * @return
	 */
	@Headers(HttpHeaders.CONTENT_TYPE + ":" + MediaType.APPLICATION_JSON_VALUE)
	@RequestLine("GET /items/{id}")
	List<Sku> getProductDetailsForSapecificItems(@Param(value = "id") String id);
	
	/**
	 * product detail for single item
	 *
	 * @param id
	 * @return
	 */
	@RequestLine("GET /item/{id}")
	@Headers("Content-Type: application/json")
	Sku getItemDetails(@Param(value = "id") String id);

	/**
	 * This method returns response from product API
	 * 
	 * @param query
	 * @return
	 */
	@Headers(HttpHeaders.CONTENT_TYPE + ":" + MediaType.APPLICATION_JSON_VALUE)
	@RequestLine("GET /v2/products/id/{id}")
	List<ProductDetail> getProductDetailsForSapecificProducts(@Param("id") String id);

}
