package com.sapient.coc.application.pricingservice.service.impl;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sapient.coc.application.pricingservice.bo.vo.NiemenProduct;

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
