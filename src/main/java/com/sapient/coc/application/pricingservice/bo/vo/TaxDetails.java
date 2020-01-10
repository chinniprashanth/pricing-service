package com.sapient.coc.application.pricingservice.bo.vo;

import java.math.BigDecimal;
import java.util.Objects;

public class TaxDetails {


	private Integer zipCode;

	private Integer taxPercent;

	private BigDecimal taxAmount;

	public BigDecimal getTaxAmount() {
		return taxAmount;
	}

	public void setTaxAmount(final BigDecimal taxAmount) {
		this.taxAmount = taxAmount;
	}

	public Integer getZipCode() {
		return zipCode;
	}

	public void setZipCode(final Integer zipCode) {
		this.zipCode = zipCode;
	}

	public Integer getTaxPercent() {
		return taxPercent;
	}

	public void setTaxPercent(final Integer taxPercent) {
		this.taxPercent = taxPercent;
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		final Tax tax = (Tax) o;
		return Objects.equals(zipCode, zipCode) && Objects.equals(taxPercent, taxPercent);
	}

	@Override
	public int hashCode() {
		return Objects.hash(zipCode, taxPercent);
	}

}
