package com.sapient.coc.application.pricingservice.niemen.bo.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NiemenDetail {
	@JsonProperty("getNMProduct")
	private NiemenProduct niemProduct;

	public NiemenProduct getNiemProduct() {
		return niemProduct;
	}

	public void setNiemProduct(NiemenProduct niemProduct) {
		this.niemProduct = niemProduct;
	}
}
