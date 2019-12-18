package com.sapient.coc.application.pricingservice.bo.vo;



import java.io.Serializable;

import com.sapient.coc.application.coreframework.bo.Money;


/**
 * POJO to fetch cart details
 * 
 * @author pooyadav
 *
 */
public class Cart implements Serializable {

	private static final long serialVersionUID = 9088377971804860566L;

	private String userId;
    private String productId;
    private String skuId;
    private Money wasPrice;
    private Integer quantity;
    private Integer storeId;
    private String status;
    private String inventoryStatus;
   
    @Override
    public String toString() {
        return "Cart{" +
                ", userId='" + userId + '\'' +
                ", productId='" + productId + '\'' +
                ", skuId='" + skuId + '\'' +
                ", wasPrice=" + wasPrice +
                ", quantity=" + quantity +
                ", storeId=" + storeId +
                ", status='" + status + '\'' +
				", inventoryStatus='" + inventoryStatus +
                '}';
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

	public Money getWasPrice() {
		return wasPrice;
	}

	public void setWasPrice(Money wasPrice) {
		this.wasPrice = wasPrice;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Integer getStoreId() {
		return storeId;
	}

	public void setStoreId(Integer storeId) {
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
   
}
