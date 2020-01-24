package com.sapient.coc.application.pricingservice.bo.vo;

import java.util.Objects;

/**
 * Represents the Customer entity to be processed for promotions.
 *
 * @author pooyadav
 */
public class Customer extends Event {
	private String name;

	private String email;

	private String zipCode;

	private String phoneNumber;

	private String gender;

	public Customer() {

	}

	@Override
	public boolean equals(final Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		if (!super.equals(o))
			return false;
		final Customer customer = (Customer) o;
		return Objects.equals(name, customer.name) && Objects.equals(email, customer.email)
				&& Objects.equals(zipCode, customer.zipCode) && Objects.equals(phoneNumber, customer.phoneNumber)
				&& Objects.equals(gender, customer.gender);
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), name, email, zipCode, phoneNumber, gender);
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(final String zipCode) {
		this.zipCode = zipCode;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(final String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(final String gender) {
		this.gender = gender;
	}

}