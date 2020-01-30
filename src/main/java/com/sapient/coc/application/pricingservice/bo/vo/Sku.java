package com.sapient.coc.application.pricingservice.bo.vo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author pooyadav
 *
 */
public class Sku {
    private String id;

    private String type;

    private String name;

    private String description;

    private String fulfillment;

    @JsonProperty("parentProductID")
    private String parentproductid;

    private List<Images> images;

    private List<Attributes> attributes;

    private boolean active;

    @JsonProperty("reviewsAndRatings")
    private Reviewsandratings reviewsandratings;

	@JsonProperty("prices")
	private List<Price> price;

	private String category_id;

	@JsonProperty("category_ids")
	private List<String> category_ids;

	@JsonProperty("category_names")
	private List<String> category_names;

	/*
	 * @JsonProperty("salePrice") private Double saleprice;
	 * 
	 * @JsonProperty("listPrice") private Double listprice;
	 */

	/*
	 * @JsonProperty("itemPromotionDescription") private String
	 * itempromotiondescription;
	 * 
	 * @JsonProperty("categoryPromotionDescription") private String
	 * categorypromotiondescription;
	 * 
	 * @JsonProperty("productPromotionDescription") private String
	 * productpromotiondescription;
	 * 
	 * @JsonProperty("inventory-service") private List<SkuInventory> inventory;
	 * 
	 * @JsonProperty("inventoryAvailability") private int inventoryavailability;
	 */
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isActive() {
        return active;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFulfillment() {
        return fulfillment;
    }

    public void setFulfillment(String fulfillment) {
        this.fulfillment = fulfillment;
    }

    public String getParentproductid() {
        return parentproductid;
    }

    public void setParentproductid(String parentproductid) {
        this.parentproductid = parentproductid;
    }

    public List<Images> getImages() {
        return images;
    }

    public void setImages(List<Images> images) {
        this.images = images;
    }

    public List<Attributes> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<Attributes> attributes) {
        this.attributes = attributes;
    }

    public boolean getActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Reviewsandratings getReviewsandratings() {
        return reviewsandratings;
    }

    public void setReviewsandratings(Reviewsandratings reviewsandratings) {
        this.reviewsandratings = reviewsandratings;
    }

	public List<Price> getPrice() {
		return price;
	}

	public void setPrice(List<Price> price) {
		this.price = price;
	}

	public String getCategory_id() {
		return category_id;
	}

	public void setCategory_id(String category_id) {
		this.category_id = category_id;
	}

	public List<String> getCategory_ids() {
		return category_ids;
	}

	public void setCategory_ids(List<String> category_ids) {
		this.category_ids = category_ids;
	}

	public List<String> getCategory_names() {
		return category_names;
	}

	public void setCategory_names(List<String> category_names) {
		this.category_names = category_names;
	}



}
