package com.sapient.coc.application.pricingservice.bo.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author pooyadav
 *
 */
public class Promotions implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long promotionId;
	private String name;
	private String description;
	private String displayText;
	private Boolean enabled;
	private Date startDate;
	private Date endDate;
	private Boolean giveToAllUsers;
	private String offerType;
	private String promotionRule;
	private String promotionType;
	private Integer priority;
	private Double discount;
	private String promotionStatus;
	private Integer buyQty;
	private Integer freeQty;
	private List<String> categoryIds = new ArrayList<>();
	private List<String> productIds = new ArrayList<>();

	public Long getPromotionId() {
		return promotionId;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public String getDisplayText() {
		return displayText;
	}

	public Date getStartDate() {
		return startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public String getOfferType() {
		return offerType;
	}

	public String getPromotionRule() {
		return promotionRule;
	}

	public void setPromotionId(Long promotionId) {
		this.promotionId = promotionId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setDisplayText(String displayText) {
		this.displayText = displayText;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public void setOfferType(String offerType) {
		this.offerType = offerType;
	}

	public void setPromotionRule(String promotionRule) {
		this.promotionRule = promotionRule;
	}

	public String getPromotionType() {
		return promotionType;
	}

	public void setPromotionType(String promotionType) {
		this.promotionType = promotionType;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public Boolean getGiveToAllUsers() {
		return giveToAllUsers;
	}

	public void setGiveToAllUsers(Boolean giveToAllUsers) {
		this.giveToAllUsers = giveToAllUsers;
	}

	public Double getDiscount() {
		return discount;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}

	public String getPromotionStatus() {
		return promotionStatus;
	}

	public void setPromotionStatus(String promotionStatus) {
		this.promotionStatus = promotionStatus;
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

	public List<String> getCategoryIds() {
		return categoryIds;
	}

	public void setCategoryIds(List<String> categoryIds) {
		this.categoryIds = categoryIds;
	}

	public List<String> getProductIds() {
		return productIds;
	}

	public void setProductIds(List<String> productIds) {
		this.productIds = productIds;
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

}