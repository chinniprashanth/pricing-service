package com.sapient.coc.application.pricingservice.bo.vo;

import com.sapient.coc.application.coreframework.bo.BaseEntityDto;

/*******************************************************
 * Copyright (c) 2019 CommerceOnCloud, PublicisSapient
 *
 * This file is part of CommerceOnCloud project.
 *
 * CommerceOnCloud can not be copied and/or distributed without the express
 * permission of PublicisSapient
 *******************************************************/
public class CreditCard extends BaseEntityDto {
    private String number;

    private String nameOnCard;

    private CreditCardType type;

    private String cvv;

    private String expDate;

    public String getNameOnCard() {
        return nameOnCard;
    }

    public void setNameOnCard(final String nameOnCard) {
        this.nameOnCard = nameOnCard;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(final String cvv) {
        this.cvv = cvv;
    }

    public String getExpDate() {
        return expDate;
    }

    public void setExpDate(final String expDate) {
        this.expDate = expDate;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public CreditCardType getType() {
        return type;
    }

    public void setType(CreditCardType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "CreditCard{" +
                "id=" + super.getId() +
                ", type=" + type +
                '}';
    }
}
