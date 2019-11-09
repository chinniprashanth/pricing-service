package com.sapient.coc.application.pricingservice.bo.vo;

/**
 * @author pooyadav
 *
 */
public class ShippingResponse extends Address {

	private static final long serialVersionUID = -7265178268629057054L;
	private String country;
	private String shippingMethod;
	private double shippingCost;
	private double discountedShippingCost;
	private String shippingPromotionDescription;

	private Address address1;

	public String getShippingMethod() {
		return shippingMethod;
	}

	public void setShippingMethod(String shippingMethod) {
		this.shippingMethod = shippingMethod;
	}

	public double getShippingCost() {
		return shippingCost;
	}

	public void setShippingCost(double shippingCost) {
		this.shippingCost = shippingCost;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public double getDiscountedShippingCost() {
		return discountedShippingCost;
	}

	public void setDiscountedShippingCost(double discountedShippingCost) {
		this.discountedShippingCost = discountedShippingCost;
	}

	public String getShippingPromotionDescription() {
		return shippingPromotionDescription;
	}

	public void setShippingPromotionDescription(String shippingPromotionDescription) {
		this.shippingPromotionDescription = shippingPromotionDescription;
	}

	public Address getAddress1() {
		return address1;
	}

	public void setAddress1(Address address1) {
		this.address1 = address1;
	}

	@Override
	public String toString() {
		return address1.getFirstName() + "-" + address1.getLastName() + "-" + address1.getAddressLine1() + "-"
				+ address1.getAddressLine2() + "-" + address1.getCity() + "-" + address1.getState() + "-" + country
				+ "-" + address1.getZipCode() + "-" + shippingMethod + "-" + shippingCost + "-";
	}

}