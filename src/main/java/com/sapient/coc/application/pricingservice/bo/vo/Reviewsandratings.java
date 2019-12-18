package com.sapient.coc.application.pricingservice.bo.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

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
public class Reviewsandratings {
    @JsonProperty("avgRating")
    private int avgrating;

    @JsonProperty("totalReviews")
    private String totalreviews;

    public int getAvgrating() {
        return avgrating;
    }

    public void setAvgrating(int avgrating) {
        this.avgrating = avgrating;
    }

    public String getTotalreviews() {
        return totalreviews;
    }

    public void setTotalreviews(String totalreviews) {
        this.totalreviews = totalreviews;
    }
}
