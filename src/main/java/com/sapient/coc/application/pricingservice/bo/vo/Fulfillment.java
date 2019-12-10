package com.sapient.coc.application.pricingservice.bo.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * To fetch fulfillment details
 * 
 * @author pooyadav
 *
 */
public class Fulfillment {

	private int code;
	private String status;
	private Data data;

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

	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}
	
	@JsonProperty("traceId")
	private String traceid;

	@JsonProperty("spanId")
	private String spanid;

}
