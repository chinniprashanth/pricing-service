package com.sapient.coc.application.pricingservice.bo.vo;

/**
 * @author pooyadav
 *
 */
public class PromotionFact {

	private String categoryId;
	private String skuId;
	private double price;
	private Integer buyQty;
	private Integer freeQty;
	private double discountPrice;
	private String shippingMethod;
	private double shippingCost;
	private String promotionDescription;
	private double orderTotal;

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getSkuId() {
		return skuId;
	}

	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Integer getBuyQty() {
		return buyQty;
	}

	public void setBuyQty(Integer buyQty) {
		this.buyQty = buyQty;
	}

	public Integer getFreeQty() {
		return freeQty;
	}

	public void setFreeQty(Integer freeQty) {
		this.freeQty = freeQty;
	}

	public double getDiscountPrice() {
		return discountPrice;
	}

	public void setDiscountPrice(double discountPrice) {
		this.discountPrice = discountPrice;
	}

	public String getShippingMethod() {
		return shippingMethod;
	}

	public void setShippingMethod(String shippingMethod) {
		this.shippingMethod = shippingMethod;
	}

	public double getShippingCost() {
		return shippingCost;
	}

	public void setShippingCost(double shippingCost) {
		this.shippingCost = shippingCost;
	}

	public String getPromotionDescription() {
		return promotionDescription;
	}

	public void setPromotionDescription(String promotionDescription) {
		this.promotionDescription = promotionDescription;
	}

	public double getOrderTotal() {
		return orderTotal;
	}

	public void setOrderTotal(double orderTotal) {
		this.orderTotal = orderTotal;
	}

}
