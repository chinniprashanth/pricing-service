package com.sapient.coc.application.pricingservice.bo.vo;

import java.io.Serializable;

import com.sapient.coc.application.coreframework.bo.Money;

/*******************************************************
 * Copyright (c) 2019 CommerceOnCloud, PublicisSapient
 *
 * This file is part of CommerceOnCloud project.
 *
 * CommerceOnCloud can not be copied and/or distributed without the express
 * permission of PublicisSapient
 *******************************************************/
/**
 * POJO to save order item price details
 * 
 * @author pooyadav
 *
 */
public class OrderItemPrice implements Serializable {


	public OrderItemPrice(String name, String productId, String skuId, String shippingMethod, double shippingPrice,
			int quantity, Money itemsTotalPrice, Money itemPrice) {
		super();
		this.name = name;
		this.productId = productId;
		this.skuId = skuId;
		this.shippingMethod = shippingMethod;
		this.shippingPrice = shippingPrice;
		this.quantity = quantity;
		this.itemsTotalPrice = itemsTotalPrice;
		this.itemPrice = itemPrice;
	}

	private static final long serialVersionUID = 1325423252873814603L;

	private String name;
	private String productId;
	private String skuId;
	private String shippingMethod;
	private double shippingPrice;
	private int quantity;
	private Money itemsTotalPrice;
	private Money itemPrice;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getSkuId() {
		return skuId;
	}

	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}

	public String getShippingMethod() {
		return shippingMethod;
	}

	public void setShippingMethod(String shippingMethod) {
		this.shippingMethod = shippingMethod;
	}

	public double getShippingPrice() {
		return shippingPrice;
	}

	public void setShippingPrice(double shippingPrice) {
		this.shippingPrice = shippingPrice;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((productId == null) ? 0 : productId.hashCode());
		result = prime * result + quantity;
		result = prime * result + ((shippingMethod == null) ? 0 : shippingMethod.hashCode());
		temp = Double.doubleToLongBits(shippingPrice);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((skuId == null) ? 0 : skuId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OrderItemPrice other = (OrderItemPrice) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (productId == null) {
			if (other.productId != null)
				return false;
		} else if (!productId.equals(other.productId))
			return false;
		if (quantity != other.quantity)
			return false;
		if (shippingMethod == null) {
			if (other.shippingMethod != null)
				return false;
		} else if (!shippingMethod.equals(other.shippingMethod))
			return false;
		if (Double.doubleToLongBits(shippingPrice) != Double.doubleToLongBits(other.shippingPrice))
			return false;
		if (skuId == null) {
			if (other.skuId != null)
				return false;
		} else if (!skuId.equals(other.skuId))
			return false;
		return true;
	}

	public Money getItemsTotalPrice() {
		return itemsTotalPrice;
	}

	public void setItemsTotalPrice(Money itemsTotalPrice) {
		this.itemsTotalPrice = itemsTotalPrice;
	}

	public Money getItemPrice() {
		return itemPrice;
	}

	public void setItemPrice(Money itemPrice) {
		this.itemPrice = itemPrice;
	}

}
