package com.sapient.coc.application.pricingservice.bo.entity;

import java.io.Serializable;
import java.util.Objects;

import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

import com.datastax.driver.core.DataType;
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
 * This class represents individual cart items associated with the cart
 *
 * @author pooyadav
 */
@UserDefinedType("itemPrice")
public class ItemPrice implements Serializable {


	private static final long serialVersionUID = -3027388982975213465L;

	@Column
	private String name;

	@Column
	private String productId;

	@Column
	private String skuId;

	@Column
	private Integer quantity;

	@Column
	@CassandraType(type = DataType.Name.UDT, userTypeName = "Money")
	private Money itemPrice;// item final price including all rebates
	
	@Column
	private double discountedprice;

	public double getDiscountedprice() {
		return discountedprice;
	}

	public void setDiscountedprice(double discountedprice) {
		this.discountedprice = discountedprice;
	}

	@Column
	@CassandraType(type = DataType.Name.UDT, userTypeName = "Money")
	private Money itemsTotalPrice;

	public ItemPrice() {
	}

	public ItemPrice(String name, String productId, String skuId, Integer quantity, Money itemPrice, double discountedprice) {
		this.name = name;
		this.productId = productId;
		this.quantity = quantity;
		this.skuId = skuId;
		this.itemPrice=itemPrice;
		this.discountedprice=discountedprice;
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		final ItemPrice orderItem = (ItemPrice) o;
		return Objects.equals(name, orderItem.name) && Objects.equals(productId, orderItem.productId)
				&& Objects.equals(skuId, orderItem.skuId) && Objects.equals(quantity, orderItem.quantity);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, productId, skuId, quantity, itemPrice, discountedprice);
	}

	@Override
	public String toString() {
		return "OrderItem [name=" + name + ", productId=" + productId + ", skuId=" + skuId + ", quantity=" + quantity
				+ "discountedprice="+discountedprice+"]";
	}

	public String getSkuId() {
		return skuId;
	}

	public void setSkuId(final String skuId) {
		this.skuId = skuId;
	}

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

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	
	public Money getItemPrice() {
		return itemPrice;
	}

	public void setItemPrice(Money itemPrice) {
		this.itemPrice = itemPrice;
	}

	public Money getItemsTotalPrice() {
		return itemsTotalPrice;
	}

	public void setItemsTotalPrice(Money itemsTotalPrice) {
		this.itemsTotalPrice = itemsTotalPrice;
	}

}
