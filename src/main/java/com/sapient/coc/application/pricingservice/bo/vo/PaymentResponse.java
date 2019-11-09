package com.sapient.coc.application.pricingservice.bo.vo;

/**
 * @author pooyadav
 *
 */
public class PaymentResponse extends Address {

	private static final long serialVersionUID = -272261415268728993L;
	private String cardNumber;
	private int cvv;
	private String expDate;
	private Integer expYear;
	private String expMonth;

	private Long profilePaymentId;

	private Address address;

	public Long getProfilePaymentId() {
		return profilePaymentId;
	}

	public void setProfilePaymentId(Long profilePaymentId) {
		this.profilePaymentId = profilePaymentId;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public int getCvv() {
		return cvv;
	}

	public void setCvv(int cvv) {
		this.cvv = cvv;
	}

	public String getExpDate() {
		return expDate;
	}

	public void setExpDate(String expDate) {
		this.expDate = expDate;
	}

	public Integer getExpYear() {
		return expYear;
	}

	public void setExpYear(Integer expYear) {
		this.expYear = expYear;
	}

	public String getExpMonth() {
		return expMonth;
	}

	public void setExpMonth(String expMonth) {
		this.expMonth = expMonth;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return address.getFirstName() + "-" + address.getLastName() + "-" + address.getAddressLine1() + "-"
				+ address.getAddressLine2() + "-" + address.getCity() + "-" + address.getState() + "-"
				+ address.getZipCode() + "-" + cardNumber + "-" + cvv + "-" + expDate + "-";
	}

}