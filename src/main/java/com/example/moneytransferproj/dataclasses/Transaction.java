package com.example.moneytransferproj.dataclasses;

import java.util.Objects;

public class Transaction {

    private String operationID;
    private String cardFromNumber;
    private String cardToNumber;
    private double amount;
    private String currency;
    private double serviceFee;

    private String operationResult;

    public Transaction(String operationID, String cardFromNumber, String cardToNumber, double amount, String currency, double serviceFee, String operationResult) {
        this.operationID = operationID;
        this.cardFromNumber = cardFromNumber;
        this.cardToNumber = cardToNumber;
        this.amount = amount;
        this.currency = currency;
        this.serviceFee = serviceFee;
        this.operationResult = operationResult;
    }

    public Transaction() {
    }

    public String getOperationID() {
        return operationID;
    }

    public void setOperationID(String operationID) {
        this.operationID = operationID;
    }

    public String getCardFromNumber() {
        return cardFromNumber;
    }

    public void setCardFromNumber(String cardFromNumber) {
        this.cardFromNumber = cardFromNumber;
    }

    public String getCardToNumber() {
        return cardToNumber;
    }

    public void setCardToNumber(String cardToNumber) {
        this.cardToNumber = cardToNumber;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public double getServiceFee() {
        return serviceFee;
    }

    public void setServiceFee(double serviceFee) {
        this.serviceFee = serviceFee;
    }

    public String getOperationResult() {
        return operationResult;
    }

    public void setOperationResult(String operationResult) {
        this.operationResult = operationResult;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Transaction that)) return false;
        return Double.compare(that.amount, amount) == 0 && Double.compare(that.serviceFee, serviceFee) == 0 && Objects.equals(operationID, that.operationID) && Objects.equals(cardFromNumber, that.cardFromNumber) && Objects.equals(cardToNumber, that.cardToNumber) && Objects.equals(currency, that.currency) && Objects.equals(operationResult, that.operationResult);
    }

    @Override
    public int hashCode() {
        return Objects.hash(operationID, cardFromNumber, cardToNumber, amount, currency, serviceFee, operationResult);
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "operationID='" + operationID + '\'' +
                ", cardFromNumber='" + cardFromNumber + '\'' +
                ", cardToNumber='" + cardToNumber + '\'' +
                ", amount=" + amount +
                ", currency='" + currency + '\'' +
                ", serviceFee=" + serviceFee +
                ", operationResult='" + operationResult + '\'' +
                '}';
    }
}
