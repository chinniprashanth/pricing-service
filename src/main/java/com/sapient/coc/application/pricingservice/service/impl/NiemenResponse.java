package com.sapient.coc.application.pricingservice.service.impl;

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
