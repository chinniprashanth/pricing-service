package com.sapient.coc.application.pricingservice.bo.vo;

import java.io.Serializable;
import java.text.DecimalFormat;

/**
 * @author pooyadav
 *
 */
public class OrderItem implements Serializable {

	private static final long serialVersionUID = -4703423167816882116L;
	private String itemId;
	private String name;
	private String itemDescription;
	private String skuId;
	private String productId;
	private String imageUrl;
	private int quantity;
	private double listPrice;
	private double salePrice;
	private double itemsTotalPrice;
	private double itemPrice;
	private double orderItemTax;
	private double shippingPrice;
	private String shippingMethod;
	// private double currentprice;
	private double itemDiscountedPrice;
	private double categoryDiscountedPrice;
	private double productDiscountedPrice;
	private String itemPromotionDescription;
	private String categoryPromotionDescription;
	private String productPromotionDescription;

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}


	public String getItemDescription() {
		return itemDescription;
	}

	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}

	public String getSkuId() {
		return skuId;
	}

	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}



	public double getListPrice() {
		DecimalFormat df = new DecimalFormat("###.##");
		return Double.parseDouble(df.format(listPrice));

	}

	public void setListPrice(double listPrice) {
		this.listPrice = listPrice;
	}

	public double getSalePrice() {
		DecimalFormat df = new DecimalFormat("###.##");
		return Double.parseDouble(df.format(salePrice));

	}

	public void setSalePrice(double salePrice) {
		this.salePrice = salePrice;
	}

	public double getItemsTotalPrice() {
		return itemsTotalPrice;
	}

	public void setItemsTotalPrice(double itemsTotalPrice) {
		this.itemsTotalPrice = itemsTotalPrice;
	}

	public double getOrderItemTax() {
		return orderItemTax;
	}

	public void setOrderItemTax(double orderItemTax) {
		this.orderItemTax = orderItemTax;
	}

	/*
	 * public double getTotalTax() { return totalTax; }
	 * 
	 * public void setTotalTax(double totalTax) { this.totalTax = totalTax; }
	 */
	public double getItemDiscountedPrice() {
		return itemDiscountedPrice;
	}

	public void setItemDiscountedPrice(double itemDiscountedPrice) {
		this.itemDiscountedPrice = itemDiscountedPrice;
	}

	public double getCategoryDiscountedPrice() {
		return categoryDiscountedPrice;
	}

	public void setCategoryDiscountedPrice(double categoryDiscountedPrice) {
		this.categoryDiscountedPrice = categoryDiscountedPrice;
	}

	public String getItemPromotionDescription() {
		return itemPromotionDescription;
	}

	public void setItemPromotionDescription(String itemPromotionDescription) {
		this.itemPromotionDescription = itemPromotionDescription;
	}

	public String getCategoryPromotionDescription() {
		return categoryPromotionDescription;
	}

	public void setCategoryPromotionDescription(String categoryPromotionDescription) {
		this.categoryPromotionDescription = categoryPromotionDescription;
	}
	
	

	public double getProductDiscountedPrice() {
		return productDiscountedPrice;
	}

	public void setProductDiscountedPrice(double productDiscountedPrice) {
		this.productDiscountedPrice = productDiscountedPrice;
	}

	public String getProductPromotionDescription() {
		return productPromotionDescription;
	}

	public void setProductPromotionDescription(String productPromotionDescription) {
		this.productPromotionDescription = productPromotionDescription;
	}

	
	/*
	 * public double getCurrentprice() { return currentprice; }
	 * 
	 * public void setCurrentprice(double currentprice) { this.currentprice =
	 * currentprice; }
	 */



	public double getShippingPrice() {
		return shippingPrice;
	}

	public void setShippingPrice(double shippingPrice) {
		this.shippingPrice = shippingPrice;
	}

	public String getShippingMethod() {
		return shippingMethod;
	}

	public void setShippingMethod(String shippingMethod) {
		this.shippingMethod = shippingMethod;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getItemPrice() {
		return itemPrice;
	}

	public void setItemPrice(double itemPrice) {
		this.itemPrice = itemPrice;
	}

}
