package com.sapient.coc.application.pricingservice.bo.vo;

import java.util.HashMap;
import java.util.Map;

/*******************************************************
 * Copyright (c) 2019 CommerceOnCloud, PublicisSapient
 *
 * This file is part of CommerceOnCloud project.
 *
 * CommerceOnCloud can not be copied and/or distributed without the express
 * permission of PublicisSapient
 *******************************************************/

/**
 * Promotion Rules - Represents each rule defined for a promotion
 *
 * @author pooyadav
 */
public class Condition {

	private String field;

	private Operator operator;

	private String description;

	private Object value;

	private PromoModifier modifier;

	public String getField() {
		return field;
	}

	public void setField(final String field) {
		this.field = field;
	}

	public Operator getOperator() {
		return operator;
	}

	public void setOperator(final Operator operator) {
		this.operator = operator;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(final Object value) {
		this.value = value;
	}

	public PromoModifier getModifier() {
		return modifier;
	}

	public void setModifier(final PromoModifier modifier) {
		this.modifier = modifier;
	}

	public enum Operator {
		NOT_EQUAL_TO("NOT_EQUAL_TO"), EQUAL_TO("EQUAL_TO"), GREATER_THAN("GREATER_THAN"), LESS_THAN("LESS_THAN"),
		GREATER_THAN_OR_EQUAL_TO("GREATER_THAN_OR_EQUAL_TO"), EQUALS(".equalsIgnoreCase("), CONTAINS(".contains("),
		LESS_THAN_OR_EQUAL_TO("LESS_THAN_OR_EQUAL_TO");

		private static Map<String, Operator> constants = new HashMap<String, Operator>();

		static {
			for (Condition.Operator c : values()) {
				constants.put(c.value, c);
			}
		}

		private final String value;

		Operator(String value) {
			this.value = value;
		}

		public static Condition.Operator fromValue(String value) {
			Condition.Operator constant = constants.get(value);
			if (constant == null) {
				throw new IllegalArgumentException(value);
			} else {
				return constant;
			}
		}

		@Override
		public String toString() {
			return this.value;
		}
	}
}
