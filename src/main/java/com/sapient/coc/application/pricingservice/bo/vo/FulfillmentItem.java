package com.sapient.coc.application.pricingservice.bo.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FulfillmentItem {


	@JsonProperty("skuId")
	private String skuId;

	@JsonProperty("fulfillmentMethod")
	private String fulfillmentMethod;

	@JsonProperty("storeId")
	private String storeId;

	@JsonProperty("price")
	private Double price;

	public String getSkuId() {
		return skuId;
	}

	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}

	public String getFulfillmentMethod() {
		return fulfillmentMethod;
	}

	public void setFulfillmentMethod(String fulfillmentMethod) {
		this.fulfillmentMethod = fulfillmentMethod;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}


}
