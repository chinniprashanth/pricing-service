package com.sapient.coc.application.pricingservice.bo.vo;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class CartResponse implements Serializable {

	private static final long serialVersionUID = 2335688676823292750L;
	private List<OrderItem> items = new ArrayList<>();
	private double subtotal;
	private double tax;
	private double shipping;
	private double total;
	private double totalDiscount;
	private String orderPromotionDescription;
	private String segmentPromotionDescription;
	private double actualTotal;

	public List<OrderItem> getItems() {
		return items;
	}

	public void setItems(List<OrderItem> items) {
		this.items = items;
	}

	public double getSubtotal() {
		DecimalFormat df = new DecimalFormat("###.##");
		return Double.parseDouble(df.format(subtotal));
	}

	public void setSubtotal(double subtotal) {
		this.subtotal = subtotal;
	}

	public double getTax() {
		return tax;
	}

	public void setTax(double tax) {
		this.tax = tax;
	}

	public double getShipping() {
		return shipping;
	}

	public void setShipping(double shipping) {
		this.shipping = shipping;
	}

	public double getTotal() {
		DecimalFormat df = new DecimalFormat("###.##");
		return Double.parseDouble(df.format(total));
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public void addItem(OrderItem item) {
		items.add(item);
	}

	public double getTotalDiscount() {
		return totalDiscount;
	}

	public void setTotalDiscount(double totalDiscount) {
		this.totalDiscount = totalDiscount;
	}

	public String getOrderPromotionDescription() {
		return orderPromotionDescription;
	}

	public void setOrderPromotionDescription(String orderPromotionDescription) {
		this.orderPromotionDescription = orderPromotionDescription;
	}

	public String getSegmentPromotionDescription() {
		return segmentPromotionDescription;
	}

	public void setSegmentPromotionDescription(String segmentPromotionDescription) {
		this.segmentPromotionDescription = segmentPromotionDescription;
	}

	public double getActualTotal() {
		return actualTotal;
	}

	public void setActualTotal(double actualTotal) {
		this.actualTotal = actualTotal;
	}

	@Override
	public String toString() {
		StringBuilder value = new StringBuilder();
		items.forEach(action -> value.append(action + "-"));
		value.append(subtotal + "-");
		value.append(tax + "-");
		value.append(shipping + "-");
		value.append(total + "-");
		return value.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(actualTotal);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((items == null) ? 0 : items.hashCode());
		result = prime * result + ((orderPromotionDescription == null) ? 0 : orderPromotionDescription.hashCode());
		result = prime * result + ((segmentPromotionDescription == null) ? 0 : segmentPromotionDescription.hashCode());
		temp = Double.doubleToLongBits(shipping);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(subtotal);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(tax);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(total);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(totalDiscount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		CartResponse other = (CartResponse) obj;
		if (Double.doubleToLongBits(actualTotal) != Double.doubleToLongBits(other.actualTotal))
			return false;
		if (items == null) {
			if (other.items != null)
				return false;
		} else if (!items.equals(other.items))
			return false;
		if (orderPromotionDescription == null) {
			if (other.orderPromotionDescription != null)
				return false;
		} else if (!orderPromotionDescription.equals(other.orderPromotionDescription))
			return false;
		if (segmentPromotionDescription == null) {
			if (other.segmentPromotionDescription != null)
				return false;
		} else if (!segmentPromotionDescription.equals(other.segmentPromotionDescription))
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
}