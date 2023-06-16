package com.example.moneytransferproj.dataclasses;

import java.util.Objects;

public class Account {
    private String cardNumber;
    private String cardDate;
    private String CVV;
    private Double balance;

    public Account() {
    }

    public Account(String cardNumber, String cardDate, String CVV, Double balance) {
        this.cardNumber = cardNumber;
        this.cardDate = cardDate;
        this.CVV = CVV;
        this.balance = balance;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardDate() {
        return cardDate;
    }

    public void setCardDate(String cardDate) {
        this.cardDate = cardDate;
    }

    public String getCVV() {
        return CVV;
    }

    public void setCVV(String CVV) {
        this.CVV = CVV;
    }

    public void withdraw(Double sum) {
        balance -= sum;
    }

    public void deposit(Double sum) {
        balance += sum;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Account account)) return false;
        return Objects.equals(cardNumber, account.cardNumber) && Objects.equals(cardDate, account.cardDate) && Objects.equals(CVV, account.CVV) && Objects.equals(balance, account.balance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cardNumber, cardDate, CVV, balance);
    }

    @Override
    public String toString() {
        return "Card{" +
                "cardNumber='" + cardNumber + '\'' +
                ", cardDate='" + cardDate + '\'' +
                ", CVV='" + CVV + '\'' +
                ", balance='" + balance + '\'' +
                '}';
    }
}
