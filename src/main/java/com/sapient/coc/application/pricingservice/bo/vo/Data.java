package com.sapient.coc.application.pricingservice.bo.vo;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty ;


public class Data {

	private String id;
    @JsonProperty("lastModified")
	private String lastModified;
    @JsonProperty("createdAt")
	private Date createdAt;
    @JsonProperty("orderStatus")
	private String orderStatus;
    @JsonProperty("userId")
	private String userId;
	@JsonProperty("items")
	private List<FulfillmentItem> items;
    public void setId(String id) {
         this.id = id;
     }
     public String getId() {
         return id;
     }

	public String getLastModified() {
		return lastModified;
	}

	public void setLastModified(String lastModified) {
		this.lastModified = lastModified;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

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

	public List<FulfillmentItem> getItems() {
		return items;
	}

	public void setItems(List<FulfillmentItem> items) {
		this.items = items;
	}



}