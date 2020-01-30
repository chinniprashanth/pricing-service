package com.sapient.coc.application.pricingservice.bo.vo;

/*******************************************************
 * Copyright (c) 2019 CommerceOnCloud, PublicisSapient
 *
 * This file is part of CommerceOnCloud project.
 *
 * CommerceOnCloud can not be copied and/or distributed without the express
 * permission of PublicisSapient
 *******************************************************/
/**
 * POJO to Deserialize the Product Images details
 * 
 * @author pooyadav
 *
 */
public class Price {
    private String type;
	private double value;

    public Price() {
    }

	public Price(String type, Double value) {
        this.type = type;
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

	public double getValue() {
        return value;
    }

	public void setValue(double value) {
        this.value = value;
    }
}
