package com.sapient.coc.application.pricingservice.bo.vo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * To fetch fulfillment details
 * 
 * @author pooyadav
 *
 */
public class FulfillmentDetails {

	@JsonProperty("address")
	private Address address;

	@JsonProperty("items")
	private List<FulfillmentItem> items;

}
