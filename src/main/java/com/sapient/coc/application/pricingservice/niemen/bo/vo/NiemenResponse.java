package com.sapient.coc.application.pricingservice.niemen.bo.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NiemenResponse {
	@JsonProperty("data")
	private NiemenDetail niemProduct;

	public NiemenDetail getNiemProduct() {
		return niemProduct;
	}

	public void setNiemProduct(NiemenDetail niemProduct) {
		this.niemProduct = niemProduct;
	}



}
