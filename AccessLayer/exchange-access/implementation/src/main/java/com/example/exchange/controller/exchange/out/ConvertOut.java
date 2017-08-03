package com.example.exchange.controller.exchange.out;

import com.example.exchange.common.pojo.Currency;

import java.math.BigDecimal;

/**
 * Created by Piotr Heilman on 2017-07-31.
 */
public class ConvertOut {
    private BigDecimal value;
    private Currency currency;

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }
}
