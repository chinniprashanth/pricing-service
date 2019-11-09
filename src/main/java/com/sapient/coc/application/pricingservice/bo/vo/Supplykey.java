package com.sapient.coc.application.pricingservice.bo.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author pooyadav
 *
 */
public class Supplykey {

	@JsonProperty("skuId")
	private String skuid;

	@JsonProperty("supplyBucket")
	private String supplybucket;

	@JsonProperty("locationId")
	private String locationid;

	public String getSkuid() {
		return skuid;
	}

	public void setSkuid(String skuid) {
		this.skuid = skuid;
	}

	public String getSupplybucket() {
		return supplybucket;
	}

	public void setSupplybucket(String supplybucket) {
		this.supplybucket = supplybucket;
	}

	public String getLocationid() {
		return locationid;
	}

	public void setLocationid(String locationid) {
		this.locationid = locationid;
	}

}