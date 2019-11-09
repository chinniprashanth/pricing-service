package com.sapient.coc.application.pricingservice.bo.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author pooyadav
 *
 */
public class PromotionResponse extends Promotions implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<PromotionConditions> promotionConditions;

	private List<String> skuIds = new ArrayList<>();

	public List<String> getSkuIds() {
		return skuIds;
	}

	public void setSkuIds(List<String> skuIds) {
		this.skuIds = skuIds;
	}

	public List<PromotionConditions> getPromotionConditions() {
		return promotionConditions;
	}

	public void setPromotionConditions(List<PromotionConditions> promotionConditions) {
		this.promotionConditions = promotionConditions;
	}
}
