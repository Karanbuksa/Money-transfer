package com.example.moneytransferproj.dataclasses;

import java.util.Objects;

public class Amount {
    private Integer value;
    private String currency;

    public Amount(Integer value, String currency) {
        this.value = value;
        this.currency = currency;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Amount amount)) return false;
        return Objects.equals(value, amount.value) && Objects.equals(currency, amount.currency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, currency);
    }

    public Amount() {
    }

    @Override
    public String toString() {
        return "Amount{" +
                "value=" + value +
                ", currency='" + currency + '\'' +
                '}';
    }
}
