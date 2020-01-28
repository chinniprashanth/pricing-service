package com.sapient.coc.application.pricingservice.niemen.bo.vo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sapient.coc.application.pricingservice.bo.vo.Sku;

/*******************************************************
 * Copyright (c) 2019 CommerceOnCloud, PublicisSapient
 *
 * This file is part of CommerceOnCloud project.
 *
 * CommerceOnCloud can not be copied and/or distributed without the express
 * permission of PublicisSapient
 *******************************************************/

public class NiemenProduct {

	private String code;
	private String status;

	@JsonProperty("data")
	private List<Sku> sku;


	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<Sku> getSku() {
		return sku;
	}

	public void setSku(List<Sku> sku) {
		this.sku = sku;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
