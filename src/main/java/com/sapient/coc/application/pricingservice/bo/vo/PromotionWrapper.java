package com.sapient.coc.application.pricingservice.bo.vo;

import java.util.List;

/*******************************************************
 * Copyright (c) 2019 CommerceOnCloud, PublicisSapient
 *
 * This file is part of CommerceOnCloud project.
 *
 * CommerceOnCloud can not be copied and/or distributed without the express
 * permission of PublicisSapient
 *******************************************************/

/**
 * Wrapper class which accepts the Promotion and Entity as input and passes
 * these objects for evaluation of a promotion
 *
 * @author pooyadav
 */
public class PromotionWrapper {
	private Promotion promotion;

	private List<String> promoTypeList;

	private Event event;

	private OrderEvent orderEvent;

	private Customer customerEvent;

	private Sku itemEvent;

	private Product productEvent;

	private Category categoryEvent;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((categoryEvent == null) ? 0 : categoryEvent.hashCode());
		result = prime * result + ((customerEvent == null) ? 0 : customerEvent.hashCode());
		result = prime * result + ((event == null) ? 0 : event.hashCode());
		result = prime * result + ((itemEvent == null) ? 0 : itemEvent.hashCode());
		result = prime * result + ((orderEvent == null) ? 0 : orderEvent.hashCode());
		result = prime * result + ((productEvent == null) ? 0 : productEvent.hashCode());
		result = prime * result + ((promoTypeList == null) ? 0 : promoTypeList.hashCode());
		result = prime * result + ((promotion == null) ? 0 : promotion.hashCode());
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
		PromotionWrapper other = (PromotionWrapper) obj;
		if (categoryEvent == null) {
			if (other.categoryEvent != null)
				return false;
		} else if (!categoryEvent.equals(other.categoryEvent))
			return false;
		if (customerEvent == null) {
			if (other.customerEvent != null)
				return false;
		} else if (!customerEvent.equals(other.customerEvent))
			return false;
		if (event == null) {
			if (other.event != null)
				return false;
		} else if (!event.equals(other.event))
			return false;
		if (itemEvent == null) {
			if (other.itemEvent != null)
				return false;
		} else if (!itemEvent.equals(other.itemEvent))
			return false;
		if (orderEvent == null) {
			if (other.orderEvent != null)
				return false;
		} else if (!orderEvent.equals(other.orderEvent))
			return false;
		if (productEvent == null) {
			if (other.productEvent != null)
				return false;
		} else if (!productEvent.equals(other.productEvent))
			return false;
		if (promoTypeList == null) {
			if (other.promoTypeList != null)
				return false;
		} else if (!promoTypeList.equals(other.promoTypeList))
			return false;
		if (promotion == null) {
			if (other.promotion != null)
				return false;
		} else if (!promotion.equals(other.promotion))
			return false;
		return true;
	}

	public Customer getCustomerEvent() {
		return customerEvent;
	}

	public void setCustomerEvent(final Customer customerEvent) {
		this.customerEvent = customerEvent;
	}

	public Sku getItemEvent() {
		return itemEvent;
	}

	public void setItemEvent(final Sku itemEvent) {
		this.itemEvent = itemEvent;
	}

	public Product getProductEvent() {
		return productEvent;
	}

	public void setProductEvent(final Product productEvent) {
		this.productEvent = productEvent;
	}

	public Category getCategoryEvent() {
		return categoryEvent;
	}

	public void setCategoryEvent(final Category categoryEvent) {
		this.categoryEvent = categoryEvent;
	}

	public OrderEvent getOrderEvent() {
		return orderEvent;
	}

	public void setOrderEvent(final OrderEvent orderEvent) {
		this.orderEvent = orderEvent;
	}

	public List<String> getPromoTypeList() {
		return promoTypeList;
	}

	public void setPromoTypeList(final List<String> promoTypeList) {
		this.promoTypeList = promoTypeList;
	}

	public Promotion getPromotion() {
		return promotion;
	}

	public void setPromotion(final Promotion promotion) {
		this.promotion = promotion;
	}

	@Override
	public String toString() {
		return "PromotionWrapper{" + "promotion=" + promotion + ", promoTypeList=" + promoTypeList + ", event=" + event
				+ '}';
	}

	public Event getEvent() {
		return event;
	}

	public void setEvent(final Event event) {
		this.event = event;
	}

}
