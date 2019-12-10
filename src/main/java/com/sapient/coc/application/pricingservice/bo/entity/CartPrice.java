package com.sapient.coc.application.pricingservice.bo.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.validation.constraints.NotBlank;

import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.Indexed;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import com.datastax.driver.core.DataType;
import com.sapient.coc.application.coreframework.bo.BaseEntity;

/*******************************************************
 * Copyright (c) 2019 CommerceOnCloud, PublicisSapient
 *
 * This file is part of CommerceOnCloud project.
 *
 * CommerceOnCloud can not be copied and/or distributed without the express
 * permission of PublicisSapient
 *******************************************************/

/**
 * cartPrice represents the pricing details of a cart
 * 
 * @author pooyadav
 *
 */
@Table("cartTotal")
public class CartPrice extends BaseEntity implements Cloneable {

    private static final long serialVersionUID = 6529685098267757670L;

	@NotBlank(message = "cartId is mandatory")
	@Indexed
	@PrimaryKeyColumn(ordinal = 1, type = PrimaryKeyType.CLUSTERED)
	private String cartId;

	@CassandraType(type = DataType.Name.SET, typeArguments = { DataType.Name.UDT }, userTypeName = "ItemPrice")
	@Column
	private List<ItemPrice> itemPrice = new ArrayList<>();

	public List<ItemPrice> getItemPrice() {
		return itemPrice;
	}

	public void setItemPrice(List<ItemPrice> itemPrice) {
		this.itemPrice = itemPrice;
	}
	@Column
	private double subtotal;
	public String getCartId() {
		return cartId;
	}

	public void setCartId(String cartId) {
		this.cartId = cartId;
	}



	public double getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(double subtotal) {
		this.subtotal = subtotal;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public double getActualTotal() {
		return actualTotal;
	}

	public void setActualTotal(double actualTotal) {
		this.actualTotal = actualTotal;
	}

	public double getShipping() {
		return shipping;
	}

	public void setShipping(double shipping) {
		this.shipping = shipping;
	}

	public double getTotalDiscount() {
		return totalDiscount;
	}

	public void setTotalDiscount(double totalDiscount) {
		this.totalDiscount = totalDiscount;
	}

	public double getTax() {
		return tax;
	}

	public void setTax(double tax) {
		this.tax = tax;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Column
	private double total;

	@Column
	private double actualTotal;

	@Column
	private double shipping;

	@Column
	private double totalDiscount;

	@Column
	private double tax;
	
	public CartPrice() {

	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CartPrice other = (CartPrice) obj;
		if (Double.doubleToLongBits(actualTotal) != Double.doubleToLongBits(other.actualTotal))
			return false;
		if (cartId == null) {
			if (other.cartId != null)
				return false;
		} else if (!cartId.equals(other.cartId))
			return false;
		if (itemPrice == null) {
			if (other.itemPrice != null)
				return false;
		} else if (!itemPrice.equals(other.itemPrice))
			return false;
		if (Double.doubleToLongBits(shipping) != Double.doubleToLongBits(other.shipping))
			return false;
		if (Double.doubleToLongBits(subtotal) != Double.doubleToLongBits(other.subtotal))
			return false;
		if (Double.doubleToLongBits(tax) != Double.doubleToLongBits(other.tax))
			return false;
		if (Double.doubleToLongBits(total) != Double.doubleToLongBits(other.total))
			return false;
		if (Double.doubleToLongBits(totalDiscount) != Double.doubleToLongBits(other.totalDiscount))
			return false;
		return true;
	}
	@Override
	public int hashCode() {
		return Objects.hash(actualTotal, cartId, totalDiscount);
	}

	public CartPrice(@NotBlank(message = "cartId is mandatory") @Indexed String cartId,
			List<ItemPrice> itemPrice, double subtotal, double total, double actualTotal, double shipping,
			double totalDiscount, double tax) {
		super();
		this.cartId = cartId;
		this.itemPrice = itemPrice;
		this.subtotal = subtotal;
		this.total = total;
		this.actualTotal = actualTotal;
		this.shipping = shipping;
		this.totalDiscount = totalDiscount;
		this.tax = tax;
	}

}
