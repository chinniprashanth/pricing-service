package com.sapient.coc.application.pricingservice.bo.vo;

import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.sapient.coc.application.coreframework.bo.BaseEntityDto;

/*******************************************************
 * Copyright (c) 2019 CommerceOnCloud, PublicisSapient
 *
 * This file is part of CommerceOnCloud project.
 *
 * CommerceOnCloud can not be copied and/or distributed without the express
 * permission of PublicisSapient
 *******************************************************/

@XmlRootElement(name = "User")
@XmlAccessorType(XmlAccessType.FIELD)
public class Profile extends BaseEntityDto {

    private String email;

    private String firstName;

    private String lastName;

	@XmlTransient
    private String phoneNumber;

	@XmlTransient
    private Set<CreditCard> creditCards;

	@XmlTransient
    private Set<AddressVO> addresses;

    public Profile() {
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(final String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Set<CreditCard> getCreditCards() {
        return creditCards;
    }

    public void setCreditCards(final Set<CreditCard> creditCards) {
        this.creditCards = creditCards;
    }

    public Set<AddressVO> getAddresses() {
        return addresses;
    }

    public void setAddresses(final Set<AddressVO> addresses) {
        this.addresses = addresses;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


    @Override
    public String toString() {
        return "Profile{" +
                "id=" + super.getId() +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}