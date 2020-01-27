package com.sapient.coc.application.pricingservice.bo.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * To fetch promotion details
 * 
 * @author pooyadav
 *
 */
public class PromoResponse {

	private int code;
	private String status;
	private OrderEvent data;
	@JsonProperty("traceId")
	private String traceid;
	@JsonProperty("spanId")
	private String spanid;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public OrderEvent getData() {
		return data;
	}

	public void setData(OrderEvent data) {
		this.data = data;
	}

}
