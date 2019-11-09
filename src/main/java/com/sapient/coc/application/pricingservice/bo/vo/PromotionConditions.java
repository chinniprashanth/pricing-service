package com.sapient.coc.application.pricingservice.bo.vo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author pooyadav
 *
 */
public class PromotionConditions implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1034226122027489556L;
	private String field;
	private transient Object value;
	private PromotionConditions.Operator conditionalOperator;
	private String conditionalOperatorValue;
	private String logicalOperator;

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public PromotionConditions.Operator getConditionalOperator() {
		return conditionalOperator;
	}

	public void setConditionalOperator(PromotionConditions.Operator conditionalOperator) {
		this.conditionalOperator = conditionalOperator;
	}

	public  enum Operator {
		NOT_EQUAL_TO("NOT_EQUAL_TO"), EQUAL_TO("EQUAL_TO"), GREATER_THAN("GREATER_THAN"), LESS_THAN(
				"LESS_THAN"), GREATER_THAN_OR_EQUAL_TO("GREATER_THAN_OR_EQUAL_TO"), LESS_THAN_OR_EQUAL_TO(
						"LESS_THAN_OR_EQUAL_TO"), CONTAINS("CONTAINS"), IN("IN");
		private final String value;
		private static Map<String, Operator> constants = new HashMap<>();

		static {
			for (PromotionConditions.Operator c : values()) {
				constants.put(c.value, c);
			}
		}

		private Operator(String value) {
			this.value = value;
		}

		@Override
		public String toString() {
			return this.value;
		}

		public static PromotionConditions.Operator fromValue(String value) {
			PromotionConditions.Operator constant = constants.get(value);
			if (constant == null) {
				throw new IllegalArgumentException(value);
			} else {
				return constant;
			}
		}
	}

	public String getConditionalOperatorValue() {
		return conditionalOperatorValue;
	}

	public void setConditionalOperatorValue(String conditionalOperatorValue) {
		this.conditionalOperatorValue = conditionalOperatorValue;
	}

	public String getLogicalOperator() {
		return logicalOperator;
	}

	public void setLogicalOperator(String logicalOperator) {
		this.logicalOperator = logicalOperator;
	}

}
