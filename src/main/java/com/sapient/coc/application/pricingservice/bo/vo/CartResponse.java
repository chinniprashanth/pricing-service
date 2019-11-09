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
}