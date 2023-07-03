package com.example.moneytransferproj.entitys;

import java.util.Objects;

public class Account {
    private String cardNumber;
    private String cardDate;
    private String cvv;
    private Double balance;

    public Account() {
    }

    public Account(String cardNumber, String cardDate, String cvv, Double balance) {
        this.cardNumber = cardNumber;
        this.cardDate = cardDate;
        this.cvv = cvv;
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

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
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
        return Objects.equals(cardNumber, account.cardNumber) && Objects.equals(cardDate, account.cardDate) && Objects.equals(cvv, account.cvv) && Objects.equals(balance, account.balance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cardNumber, cardDate, cvv, balance);
    }

    @Override
    public String toString() {
        return "Card{" +
                "cardNumber='" + cardNumber + '\'' +
                ", cardDate='" + cardDate + '\'' +
                ", CVV='" + cvv + '\'' +
                ", balance='" + balance + '\'' +
                '}';
    }
}
