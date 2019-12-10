package com.sapient.coc.application.pricingservice.bo.vo;

/*******************************************************
 * Copyright (c) 2019 CommerceOnCloud, PublicisSapient
 *
 * This file is part of CommerceOnCloud project.
 *
 * CommerceOnCloud can not be copied and/or distributed without the express
 * permission of PublicisSapient
 *******************************************************/
/**
 * POJO to Deserialize the Product Images details
 * 
 * @author pooyadav
 *
 */
public class Inventory {

    private String availabilityStatus;
    private String url;

    public Inventory() {
    }

    public Inventory(String availabilityStatus, String url) {
        this.availabilityStatus = availabilityStatus;
        this.url = url;
    }

    public String getAvailabilityStatus() {
        return availabilityStatus;
    }

    public void setAvailabilityStatus(String availabilityStatus) {
        this.availabilityStatus = availabilityStatus;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
