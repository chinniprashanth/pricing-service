package com.sapient.coc.application.pricingservice.bo.vo;

import java.util.Set;

/**
 * Represents the Order entity to be processed for promotions.
 *
 * @author pooyadav
 */
public class OrderEvent {

	private Double price;

	private Double discountedPrice;

	private Customer customer;

	private Set<Sku> Items;

	public OrderEvent() {
		super();
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(final Customer customer) {
		this.customer = customer;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((Items == null) ? 0 : Items.hashCode());
		result = prime * result + ((customer == null) ? 0 : customer.hashCode());
		result = prime * result + ((price == null) ? 0 : price.hashCode());
		result = prime * result + ((discountedPrice == null) ? 0 : discountedPrice.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		OrderEvent other = (OrderEvent) obj;
		if (Items == null) {
			if (other.Items != null)
				return false;
		} else if (!Items.equals(other.Items))
			return false;
		if (customer == null) {
			if (other.customer != null)
				return false;
		} else if (!customer.equals(other.customer))
			return false;
		return true;
	}

	public Set<Sku> getItems() {
		return Items;
	}

	public void setItems(final Set<Sku> items) {
		Items = items;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getDiscountedPrice() {
		return discountedPrice;
	}

	public void setDiscountedPrice(Double discountedPrice) {
		this.discountedPrice = discountedPrice;
	}

}