package com.sapient.coc.application.pricingservice.bo.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author pooyadav
 *
 */
public class SkuInventory {
    @JsonProperty("supplyKey")
    private Supplykey supplykey;

    @JsonProperty("locationType")
    private String locationtype;

    @JsonProperty("locationName")
    private String locationname;

    private int quantity;

    @JsonProperty("promiseQuantity")
    private int promisequantity;

    public Supplykey getSupplykey() {
        return supplykey;
    }

    public void setSupplykey(Supplykey supplykey) {
        this.supplykey = supplykey;
    }

    public String getLocationtype() {
        return locationtype;
    }

    public void setLocationtype(String locationtype) {
        this.locationtype = locationtype;
    }

    public String getLocationname() {
        return locationname;
    }

    public void setLocationname(String locationname) {
        this.locationname = locationname;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPromisequantity() {
        return promisequantity;
    }

    public void setPromisequantity(int promisequantity) {
        this.promisequantity = promisequantity;
    }
}
