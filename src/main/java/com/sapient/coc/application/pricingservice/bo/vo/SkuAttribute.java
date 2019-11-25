package com.sapient.coc.application.pricingservice.bo.vo;

/**
 * @author pooyadav
 *
 */
public class SkuAttribute {
	private String name;
	private String value;

	public SkuAttribute() {
	}

	public SkuAttribute(String name, String value) {
		this.name = name;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
