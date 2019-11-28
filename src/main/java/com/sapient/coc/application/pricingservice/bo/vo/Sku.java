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

    @JsonProperty("salePrice")
    private int saleprice;

    @JsonProperty("listPrice")
    private int listprice;

    @JsonProperty("itemPromotionDescription")
    private String itempromotiondescription;

    @JsonProperty("categoryPromotionDescription")
    private String categorypromotiondescription;

    @JsonProperty("productPromotionDescription")
    private String productpromotiondescription;

    @JsonProperty("inventory-service")
    private List<SkuInventory> inventory;

    @JsonProperty("inventoryAvailability")
    private int inventoryavailability;

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

    public int getSaleprice() {
        return saleprice;
    }

    public void setSaleprice(int saleprice) {
        this.saleprice = saleprice;
    }

    public int getListprice() {
        return listprice;
    }

    public void setListprice(int listprice) {
        this.listprice = listprice;
    }

    public String getItempromotiondescription() {
        return itempromotiondescription;
    }

    public void setItempromotiondescription(String itempromotiondescription) {
        this.itempromotiondescription = itempromotiondescription;
    }

    public List<SkuInventory> getInventory() {
        return inventory;
    }

    public void setInventory(final List<SkuInventory> inventory) {
        this.inventory = inventory;
    }

    public String getCategorypromotiondescription() {
        return categorypromotiondescription;
    }

    public void setCategorypromotiondescription(String categorypromotiondescription) {
        this.categorypromotiondescription = categorypromotiondescription;
    }

    public String getProductpromotiondescription() {
        return productpromotiondescription;
    }

    public void setProductpromotiondescription(String productpromotiondescription) {
        this.productpromotiondescription = productpromotiondescription;
    }

    public int getInventoryavailability() {
        return inventoryavailability;
    }

    public void setInventoryavailability(int inventoryavailability) {
        this.inventoryavailability = inventoryavailability;
    }

}
