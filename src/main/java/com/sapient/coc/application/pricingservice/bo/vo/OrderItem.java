package com.sapient.coc.application.pricingservice.bo.vo;

import java.io.Serializable;

/**
 * @author pooyadav
 *
 */
public class OrderItem implements Serializable {

	private static final long serialVersionUID = -4703423167816882116L;
	private String itemDescription;
	private String imageUrl;
	private double listPrice;
	private double salePrice;
	private double orderItemTax;
	private double itemDiscountedPrice;
	private double categoryDiscountedPrice;
	private double productDiscountedPrice;
	private String itemPromotionDescription;
	private String categoryPromotionDescription;
	private String productPromotionDescription;

	private String name;
	private String productId;
	private String skuId;
	private String shippingMethod;
	private double shippingPrice;
	private int quantity;
	private double itemsTotalPrice;
	private double itemPrice;

	public String getItemDescription() {
		return itemDescription;
	}
	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public double getListPrice() {
		return listPrice;
	}
	public void setListPrice(double listPrice) {
		this.listPrice = listPrice;
	}
	public double getSalePrice() {
		return salePrice;
	}
	public void setSalePrice(double salePrice) {
		this.salePrice = salePrice;
	}
	public double getOrderItemTax() {
		return orderItemTax;
	}
	public void setOrderItemTax(double orderItemTax) {
		this.orderItemTax = orderItemTax;
	}
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

	public double getProductDiscountedPrice() {
		return productDiscountedPrice;
	}

	public void setProductDiscountedPrice(double productDiscountedPrice) {
		this.productDiscountedPrice = productDiscountedPrice;
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
	public String getProductPromotionDescription() {
		return productPromotionDescription;
	}
	public void setProductPromotionDescription(String productPromotionDescription) {
		this.productPromotionDescription = productPromotionDescription;
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

	public double getItemsTotalPrice() {
		return itemsTotalPrice;
	}

	public void setItemsTotalPrice(double itemsTotalPrice) {
		this.itemsTotalPrice = itemsTotalPrice;
	}

	public double getItemPrice() {
		return itemPrice;
	}

	public void setItemPrice(double itemPrice) {
		this.itemPrice = itemPrice;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(categoryDiscountedPrice);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result
				+ ((categoryPromotionDescription == null) ? 0 : categoryPromotionDescription.hashCode());
		result = prime * result + ((imageUrl == null) ? 0 : imageUrl.hashCode());
		result = prime * result + ((itemDescription == null) ? 0 : itemDescription.hashCode());
		temp = Double.doubleToLongBits(itemDiscountedPrice);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(itemPrice);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((itemPromotionDescription == null) ? 0 : itemPromotionDescription.hashCode());
		temp = Double.doubleToLongBits(itemsTotalPrice);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(listPrice);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		temp = Double.doubleToLongBits(orderItemTax);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(productDiscountedPrice);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((productId == null) ? 0 : productId.hashCode());
		result = prime * result + ((productPromotionDescription == null) ? 0 : productPromotionDescription.hashCode());
		result = prime * result + quantity;
		temp = Double.doubleToLongBits(salePrice);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		OrderItem other = (OrderItem) obj;
		if (Double.doubleToLongBits(categoryDiscountedPrice) != Double.doubleToLongBits(other.categoryDiscountedPrice))
			return false;
		if (categoryPromotionDescription == null) {
			if (other.categoryPromotionDescription != null)
				return false;
		} else if (!categoryPromotionDescription.equals(other.categoryPromotionDescription))
			return false;
		if (imageUrl == null) {
			if (other.imageUrl != null)
				return false;
		} else if (!imageUrl.equals(other.imageUrl))
			return false;
		if (itemDescription == null) {
			if (other.itemDescription != null)
				return false;
		} else if (!itemDescription.equals(other.itemDescription))
			return false;
		if (Double.doubleToLongBits(itemDiscountedPrice) != Double.doubleToLongBits(other.itemDiscountedPrice))
			return false;
		if (Double.doubleToLongBits(itemPrice) != Double.doubleToLongBits(other.itemPrice))
			return false;
		if (itemPromotionDescription == null) {
			if (other.itemPromotionDescription != null)
				return false;
		} else if (!itemPromotionDescription.equals(other.itemPromotionDescription))
			return false;
		if (Double.doubleToLongBits(itemsTotalPrice) != Double.doubleToLongBits(other.itemsTotalPrice))
			return false;
		if (Double.doubleToLongBits(listPrice) != Double.doubleToLongBits(other.listPrice))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (Double.doubleToLongBits(orderItemTax) != Double.doubleToLongBits(other.orderItemTax))
			return false;
		if (Double.doubleToLongBits(productDiscountedPrice) != Double.doubleToLongBits(other.productDiscountedPrice))
			return false;
		if (productId == null) {
			if (other.productId != null)
				return false;
		} else if (!productId.equals(other.productId))
			return false;
		if (productPromotionDescription == null) {
			if (other.productPromotionDescription != null)
				return false;
		} else if (!productPromotionDescription.equals(other.productPromotionDescription))
			return false;
		if (quantity != other.quantity)
			return false;
		if (Double.doubleToLongBits(salePrice) != Double.doubleToLongBits(other.salePrice))
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

	@Override
	public String toString() {
		return "OrderItem [itemDescription=" + itemDescription + ", imageUrl=" + imageUrl
				+ ", listPrice=" + listPrice + ", salePrice=" + salePrice + ", orderItemTax=" + orderItemTax
				+ ", itemDiscountedPrice=" + itemDiscountedPrice + ", categoryDiscountedPrice="
				+ categoryDiscountedPrice + ", productDiscountedPrice=" + productDiscountedPrice
				+ ", itemPromotionDescription=" + itemPromotionDescription + ", categoryPromotionDescription="
				+ categoryPromotionDescription + ", productPromotionDescription=" + productPromotionDescription
				+ ", name=" + name + ", productId=" + productId + ", skuId=" + skuId + ", shippingMethod="
				+ shippingMethod + ", shippingPrice=" + shippingPrice + ", quantity=" + quantity + ", itemsTotalPrice="
				+ itemsTotalPrice + ", itemPrice=" + itemPrice + "]";
	}


}
