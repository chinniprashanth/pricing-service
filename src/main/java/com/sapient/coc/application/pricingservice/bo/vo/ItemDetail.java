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
public class ItemDetail {

	private String id;
	private Item item;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}
}
