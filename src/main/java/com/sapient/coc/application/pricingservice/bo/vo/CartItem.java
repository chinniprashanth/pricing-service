package com.sapient.coc.application.pricingservice.bo.vo;

import java.io.Serializable;

/**
 * POJO to Deserialize cart items
 * 
 * @author pooyadav
 *
 */
public class CartItem implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8415016997204612092L;
	private String id;
	private String lastModified;
	private String createdAt;
	private String cartEventType;
	private String userId;
	private String productId;
	private String skuId;
	private Price wasPrice;
	private int quantity;
	private String storeId;
	private String status;
	private String inventoryStatus;
	private String eventHandled;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLastModified() {
		return lastModified;
	}

	public void setLastModified(String lastModified) {
		this.lastModified = lastModified;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public String getCartEventType() {
		return cartEventType;
	}

	public void setCartEventType(String cartEventType) {
		this.cartEventType = cartEventType;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
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

	public Price getWasPrice() {
		return wasPrice;
	}

	public void setWasPrice(Price wasPrice) {
		this.wasPrice = wasPrice;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getStoreId() {
		return storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getInventoryStatus() {
		return inventoryStatus;
	}

	public void setInventoryStatus(String inventoryStatus) {
		this.inventoryStatus = inventoryStatus;
	}

	public String getEventHandled() {
		return eventHandled;
	}

	public void setEventHandled(String eventHandled) {
		this.eventHandled = eventHandled;
	}

}
