package com.sapient.coc.application.pricingservice.bo.vo;

import java.util.Currency;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.validation.constraints.NotNull;

/*******************************************************
 * Copyright (c) 2019 CommerceOnCloud, PublicisSapient
 *
 * This file is part of CommerceOnCloud project.
 *
 * CommerceOnCloud can not be copied and/or distributed without the express
 * permission of PublicisSapient
 *******************************************************/

/**
 * Promotion Item - Represents Promotion object
 *
 * @author pooyadav
 */
public class Promotion {

	private static final long serialVersionUID = 5522329358494275418L;

	private String promoCode;

	private Currency currency;

	private Double discountPercent;

	private Integer quantity;

	private Promotion.eventType eventType;

	private String name;

	private String dsl;

	private String skuIds;

	private Double discountAmount;

	private String rule;

	private Date startDate;

	private Date endDate;

	private String description;

	private PromoType promoType;

	/**
	 * Promotion Rules as User Defined Type.
	 */

	private Set<Condition> conditions;

	private Boolean active = false;

	public Promotion() {

	}

	public Promotion(@NotNull(message = "Promo Code is mandatory") final String promoCode, final Double discountPercent,
			final Double discountAmount, final Currency currency,
			@NotNull(message = "Start Date is mandatory") final Date startDate,
			@NotNull(message = "End Date is mandatory") final Date endDate, final String description,
			@NotNull(message = "Promotion Type is mandatory") final PromoType promoType, final Boolean active,
			final Set<Condition> conditions, final String dsl, final Integer quantity, final String skuId) {
		this.promoCode = promoCode;
		this.discountPercent = discountPercent;
		this.discountAmount = discountAmount;
		this.currency = currency;
		this.quantity = quantity;
		this.startDate = startDate;
		this.endDate = endDate;
		this.dsl = dsl;
		this.description = description;
		this.promoType = promoType;
		this.conditions = conditions;
		this.active = active;
		this.skuIds = skuId;
	}

	public String getSkuIds() {
		return skuIds;
	}

	public void setSkuIds(final String skuIds) {
		this.skuIds = skuIds;
	}

	public String getDsl() {
		return dsl;
	}

	public void setDsl(final String dsl) {
		this.dsl = dsl;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(final Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(final Date endDate) {
		this.endDate = endDate;
	}

	public Promotion.eventType getEventType() {
		return eventType;
	}

	public void setEventType(final Promotion.eventType eventType) {
		this.eventType = eventType;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String toStr() {
		return "Promotion{" + "promoCode='" + promoCode + '\'' + ", currency=" + currency + ", discountPercent="
				+ discountPercent + ", eventType=" + eventType + ", name='" + name + '\'' + ", discountAmount="
				+ discountAmount + ", rule='" + rule + '\'' + ", startDate=" + startDate + ", endDate=" + endDate
				+ ", description='" + description + '\'' + ", promoType=" + promoType + ", conditions=" + conditions
				+ ", active=" + active + '}';
	}

	public String getPromoCode() {
		return promoCode;
	}

	public void setPromoCode(final String promoCode) {
		this.promoCode = promoCode;
	}

	public Double getDiscountPercent() {
		return discountPercent;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((active == null) ? 0 : active.hashCode());
		result = prime * result + ((conditions == null) ? 0 : conditions.hashCode());
		result = prime * result + ((currency == null) ? 0 : currency.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((discountAmount == null) ? 0 : discountAmount.hashCode());
		result = prime * result + ((discountPercent == null) ? 0 : discountPercent.hashCode());
		result = prime * result + ((dsl == null) ? 0 : dsl.hashCode());
		result = prime * result + ((endDate == null) ? 0 : endDate.hashCode());
		result = prime * result + ((eventType == null) ? 0 : eventType.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((promoCode == null) ? 0 : promoCode.hashCode());
		result = prime * result + ((promoType == null) ? 0 : promoType.hashCode());
		result = prime * result + ((quantity == null) ? 0 : quantity.hashCode());
		result = prime * result + ((rule == null) ? 0 : rule.hashCode());
		result = prime * result + ((skuIds == null) ? 0 : skuIds.hashCode());
		result = prime * result + ((startDate == null) ? 0 : startDate.hashCode());
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
		Promotion other = (Promotion) obj;
		if (active == null) {
			if (other.active != null)
				return false;
		} else if (!active.equals(other.active))
			return false;
		if (conditions == null) {
			if (other.conditions != null)
				return false;
		} else if (!conditions.equals(other.conditions))
			return false;
		if (currency != other.currency)
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (discountAmount == null) {
			if (other.discountAmount != null)
				return false;
		} else if (!discountAmount.equals(other.discountAmount))
			return false;
		if (discountPercent == null) {
			if (other.discountPercent != null)
				return false;
		} else if (!discountPercent.equals(other.discountPercent))
			return false;
		if (dsl == null) {
			if (other.dsl != null)
				return false;
		} else if (!dsl.equals(other.dsl))
			return false;
		if (eventType != other.eventType)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (promoCode == null) {
			if (other.promoCode != null)
				return false;
		} else if (!promoCode.equals(other.promoCode))
			return false;
		if (promoType != other.promoType)
			return false;
		if (quantity == null) {
			if (other.quantity != null)
				return false;
		} else if (!quantity.equals(other.quantity))
			return false;
		if (rule == null) {
			if (other.rule != null)
				return false;
		} else if (!rule.equals(other.rule))
			return false;
		if (skuIds == null) {
			if (other.skuIds != null)
				return false;
		} else if (!skuIds.equals(other.skuIds))
			return false;

		return true;
	}

	public void setDiscountPercent(final Double discountPercent) {
		this.discountPercent = discountPercent;
	}

	public Double getDiscountAmount() {
		return discountAmount;
	}

	public void setDiscountAmount(final Double discountAmount) {
		this.discountAmount = discountAmount;
	}

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(final Currency currency) {
		this.currency = currency;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public PromoType getPromoType() {
		return promoType;
	}

	public void setPromoType(final PromoType promoType) {
		this.promoType = promoType;
	}

	public Set<Condition> getConditions() {
		return conditions;
	}

	public void setConditions(final Set<Condition> conditions) {
		this.conditions = conditions;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(final Integer quantity) {
		this.quantity = quantity;
	}

	public String getRule() {
		return rule;
	}

	public void setRule(final String rule) {
		this.rule = rule;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(final Boolean active) {
		this.active = active;
	}

	/**
	 * transform the POJO into a formal statement
	 *
	 * @return
	 */
	public String toString() {
		StringBuilder statementBuilder = new StringBuilder();

		for (Condition condition : getConditions()) {

			String operator = null;

			switch (condition.getOperator()) {
			case EQUAL_TO:
				operator = "==";
				break;
			case EQUALS:
				operator = ".equals('";
				break;
			case NOT_EQUAL_TO:
				operator = "!=";
				break;
			case GREATER_THAN:
				operator = ">";
				break;
			case LESS_THAN:
				operator = "<";
				break;
			case GREATER_THAN_OR_EQUAL_TO:
				operator = ">=";
				break;
			case LESS_THAN_OR_EQUAL_TO:
				operator = "<=";
				break;
			}

			statementBuilder.append(condition.getField()).append(" ").append(operator).append(" ");

			if (condition.getValue() instanceof String) {
				statementBuilder.append("'").append(condition.getValue()).append("'");
			} else {
				statementBuilder.append(condition.getValue());
			}

			statementBuilder.append(" && ");
		}

		String statement = statementBuilder.toString();

		// remove trailing &&
		return statement.substring(0, statement.length() - 4);
	}

	public enum eventType {

		ORDER("ORDER"), PROFILE("PROFILE");

		private static Map<String, eventType> constants = new HashMap<String, eventType>();

		static {
			for (Promotion.eventType c : values()) {
				constants.put(c.value, c);
			}
		}

		private final String value;

		eventType(String value) {
			this.value = value;
		}

		public static Promotion.eventType fromValue(String value) {
			Promotion.eventType constant = constants.get(value);
			if (constant == null) {
				throw new IllegalArgumentException(value);
			} else {
				return constant;
			}
		}
	}
}
