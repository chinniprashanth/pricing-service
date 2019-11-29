package com.sapient.coc.application.pricingservice.bo.vo;


import org.springframework.data.cassandra.core.mapping.UserDefinedType;

import javax.persistence.Column;
import javax.persistence.Embeddable;


@Embeddable
@UserDefinedType("Money")
public class Money implements java.io.Serializable, Cloneable {
    //
    public static final int MAX_CURRENCY_LEN = 5;

    private static final long serialVersionUID = 1L;

    @Column(name = "currency", length = MAX_CURRENCY_LEN)
    public String currency;

    @Column(name = "amount", precision = 19, scale = 2)
    public Double amount;

    public Money() {
    }

    public Money(String currency, Double amount) {
        this.currency = currency;
        this.amount = amount;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    /**
     * @return the currency
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * @param currency the currency to set
     */
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @Override
    public String toString() {
        return "Money{" +
                "currency='" + currency + '\'' +
                ", amount=" + amount +
                '}';
    }

    /**
     * @return the amount
     */
    public Double getAmount() {
        return amount;
    }

    /**
     * @param amount the amount to set
     */
    public void setAmount(Double amount) {
        this.amount = amount;
    }
}