package com.sapient.coc.application.pricingservice.bo.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.sapient.coc.application.coreframework.bo.Money;

public class OrderPriceResp implements Serializable {

	private static final long serialVersionUID = 2335688676823292750L;

	private String orderStatus;
	private String userId;
	private List<OrderItem> orderItems = new ArrayList<>();
	// private String lastModified;
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

	public List<OrderItem> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(List<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}



	@Override
	public String toString() {
		return "OrderPriceResp [orderStatus=" + orderStatus + ", userId=" + userId + ", orderItems=" + orderItems
				+ ", submittedTime=" + submittedTime + ", deliveryTime=" + deliveryTime + ", subtotal=" + subtotal
				+ ", total=" + total + ", actualTotal=" + actualTotal + ", shipping=" + shipping + ", totalDiscount="
				+ totalDiscount + ", tax=" + tax + ", id=" + id + "]";
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((actualTotal == null) ? 0 : actualTotal.hashCode());
		result = prime * result + ((deliveryTime == null) ? 0 : deliveryTime.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((orderItems == null) ? 0 : orderItems.hashCode());
		result = prime * result + ((orderStatus == null) ? 0 : orderStatus.hashCode());
		result = prime * result + ((shipping == null) ? 0 : shipping.hashCode());
		result = prime * result + ((submittedTime == null) ? 0 : submittedTime.hashCode());
		result = prime * result + ((subtotal == null) ? 0 : subtotal.hashCode());
		result = prime * result + ((tax == null) ? 0 : tax.hashCode());
		result = prime * result + ((total == null) ? 0 : total.hashCode());
		result = prime * result + ((totalDiscount == null) ? 0 : totalDiscount.hashCode());
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
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
		OrderPriceResp other = (OrderPriceResp) obj;
		if (actualTotal == null) {
			if (other.actualTotal != null)
				return false;
		} else if (!actualTotal.equals(other.actualTotal))
			return false;
		if (deliveryTime == null) {
			if (other.deliveryTime != null)
				return false;
		} else if (!deliveryTime.equals(other.deliveryTime))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (orderItems == null) {
			if (other.orderItems != null)
				return false;
		} else if (!orderItems.equals(other.orderItems))
			return false;
		if (orderStatus == null) {
			if (other.orderStatus != null)
				return false;
		} else if (!orderStatus.equals(other.orderStatus))
			return false;
		if (shipping == null) {
			if (other.shipping != null)
				return false;
		} else if (!shipping.equals(other.shipping))
			return false;
		if (submittedTime == null) {
			if (other.submittedTime != null)
				return false;
		} else if (!submittedTime.equals(other.submittedTime))
			return false;
		if (subtotal == null) {
			if (other.subtotal != null)
				return false;
		} else if (!subtotal.equals(other.subtotal))
			return false;
		if (tax == null) {
			if (other.tax != null)
				return false;
		} else if (!tax.equals(other.tax))
			return false;
		if (total == null) {
			if (other.total != null)
				return false;
		} else if (!total.equals(other.total))
			return false;
		if (totalDiscount == null) {
			if (other.totalDiscount != null)
				return false;
		} else if (!totalDiscount.equals(other.totalDiscount))
			return false;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		return true;
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

}