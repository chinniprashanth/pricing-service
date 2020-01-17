package com.sapient.coc.application.pricingservice.bo.vo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.sapient.coc.application.coreframework.bo.BaseEntityDto;

/*******************************************************
 * Copyright (c) 2019 CommerceOnCloud, PublicisSapient
 *
 * This file is part of CommerceOnCloud project.
 *
 * CommerceOnCloud can not be copied and/or distributed without the express
 * permission of PublicisSapient
 *******************************************************/

@XmlRootElement(name = "Address")
@XmlAccessorType(XmlAccessType.FIELD)
public class AddressVO extends BaseEntityDto {
	
	private static final long serialVersionUID = 8269160490373210497L;
	private String addressLine1;
	private String addressLine2;
	private String city;
	private String country;
	private String zipcode;
	private String mobile;
	private String firstName;
	private String lastName;
	private String addressType;
	private String orderId;
	private boolean isDefault;
	private boolean saveToAdressBook;
	private String state;

	public AddressVO() {

	}

	public AddressVO(String addressLine1, String addressLine2, String city, String country, String zipcode, String mobile,
			String type, String state) {
		super();
		this.addressLine1 = addressLine1;
		this.addressLine2 = addressLine2;
		this.city = city;
		this.country = country;
		this.zipcode = zipcode;
		this.mobile = mobile;
		this.addressType = type;
		this.state = state;
	}

	public String getAddressLine1() {
		return addressLine1;
	}

	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}

	public String getAddressLine2() {
		return addressLine2;
	}

	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getAddressType() {
		return addressType;
	}

	public void setAddressType(String addressType) {
		this.addressType = addressType;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public boolean getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(boolean isDefault) {
		this.isDefault = isDefault;
	}

	public boolean isSaveToAdressBook() {
		return saveToAdressBook;
	}

	public void setSaveToAdressBook(boolean saveToAdressBook) {
		this.saveToAdressBook = saveToAdressBook;
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
		return "Address [addressLine1=" + addressLine1 + ", addressLine2=" + addressLine2 + ", city=" + city
				+ ", country=" + country + ", zipcode=" + zipcode + ", mobile=" + mobile + ", type=" + addressType
				+ ", state=" + state + "]";
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

}
