package com.sapient.coc.application.pricingservice.bo.vo;

/**
 * Event interface. Every entity on which the promotion is processed
 *
 * @author pooyadav
 */
public abstract class Event {

	private Double price;

	private Double discountedPrice;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((discountedPrice == null) ? 0 : discountedPrice.hashCode());
		result = prime * result + ((price == null) ? 0 : price.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Event other = (Event) obj;
		if (discountedPrice == null) {
			if (other.discountedPrice != null)
				return false;
		} else if (!discountedPrice.equals(other.discountedPrice))
			return false;
		if (price == null) {
			if (other.price != null)
				return false;
		} else if (!price.equals(other.price))
			return false;
		return true;
	}

	/*
	 * @Override public boolean equals(final Object o) { if (this == o) return true;
	 * if (o == null || getClass() != o.getClass()) return false; final Event event
	 * = (Event) o; return price.equals(event.price) &&
	 * discountedPrice.equals(event.discountedPrice); }
	 * 
	 * @Override public int hashCode() { return Objects.hash(price,
	 * discountedPrice); }
	 */
	public Double getPrice() {
		return price;
	}

	public void setPrice(final Double price) {
		this.price = price;
	}

	public Double getDiscountedPrice() {
		return discountedPrice;
	}

	public void setDiscountedPrice(final Double discountedPrice) {
		this.discountedPrice = discountedPrice;
	}
}
