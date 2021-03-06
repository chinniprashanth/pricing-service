package com.sapient.coc.application.pricingservice.bo.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
 * POJO to send price as kafka message to order service
 * 
 * @author pooyadav
 *
 */
public class OrderKafkaResponse implements Serializable {

	public OrderKafkaResponse(String orderStatus, String userId, List<OrderItemPrice> orderItems, Date submittedTime,
			Date deliveryTime, Money subtotal, Money total, Money actualTotal, Money shipping, Money totalDiscount,
			Money tax, String id) {
		super();
		this.orderStatus = orderStatus;
		this.userId = userId;
		this.orderItems = orderItems;
		this.submittedTime = submittedTime;
		this.deliveryTime = deliveryTime;
		this.subtotal = subtotal;
		this.total = total;
		this.actualTotal = actualTotal;
		this.shipping = shipping;
		this.totalDiscount = totalDiscount;
		this.tax = tax;
		this.id = id;
	}

	private static final long serialVersionUID = -3784122413505368842L;

	private String orderStatus;
	private String userId;
	private List<OrderItemPrice> orderItems = new ArrayList<>();
	private Date submittedTime;
	private Date deliveryTime;
	private Money subtotal;
	private Money total;
	private Money actualTotal;
	private Money shipping;
	private Money totalDiscount;
	private Money tax;
	private String id;

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public List<OrderItemPrice> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(List<OrderItemPrice> orderItems) {
		this.orderItems = orderItems;
	}

	public Date getSubmittedTime() {
		return submittedTime;
	}

	public void setSubmittedTime(Date submittedTime) {
		this.submittedTime = submittedTime;
	}

	public Date getDeliveryTime() {
		return deliveryTime;
	}

	public void setDeliveryTime(Date deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

	public Money getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(Money subtotal) {
		this.subtotal = subtotal;
	}

	public Money getTotal() {
		return total;
	}

	public void setTotal(Money total) {
		this.total = total;
	}

	public Money getActualTotal() {
		return actualTotal;
	}

	public void setActualTotal(Money actualTotal) {
		this.actualTotal = actualTotal;
	}

	public Money getShipping() {
		return shipping;
	}

	public void setShipping(Money shipping) {
		this.shipping = shipping;
	}

	public Money getTotalDiscount() {
		return totalDiscount;
	}

	public void setTotalDiscount(Money totalDiscount) {
		this.totalDiscount = totalDiscount;
	}

	public Money getTax() {
		return tax;
	}

	public void setTax(Money tax) {
		this.tax = tax;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
