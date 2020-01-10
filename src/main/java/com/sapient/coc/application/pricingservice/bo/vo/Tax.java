package com.sapient.coc.application.pricingservice.bo.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

/*******************************************************
 * Copyright (c) 2019 CommerceOnCloud, PublicisSapient
 *
 * This file is part of CommerceOnCloud project.
 *
 * CommerceOnCloud can not be copied and/or distributed without the express
 * permission of PublicisSapient
 *******************************************************/

public class Tax {

	private int code;
	private String status;
	private TaxDetails taxDetails;
	@JsonProperty("traceId")
	private String traceid;
	@JsonProperty("spanId")
	private String spanid;

	public void setCode(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}

	public void setTraceid(String traceid) {
		this.traceid = traceid;
	}

	public String getTraceid() {
		return traceid;
	}

	public void setSpanid(String spanid) {
		this.spanid = spanid;
	}

	public String getSpanid() {
		return spanid;
	}

	public TaxDetails getTaxDetails() {
		return taxDetails;
	}

	public void setTaxDetails(TaxDetails taxDetails) {
		this.taxDetails = taxDetails;
	}
}
