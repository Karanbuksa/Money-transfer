package com.example.moneytransferproj.dataclasses;

import java.util.Objects;

public class Transaction {

    private String operationID;
    private String cardFromData;
    private String cardToNumber;
    private Amount amount;
    private double serviceFee;

    public Transaction(String operationID, String cardFromData, String cardToNumber, Amount amount, double serviceFee) {
        this.operationID = operationID;
        this.cardFromData = cardFromData;
        this.cardToNumber = cardToNumber;
        this.amount = amount;
        this.serviceFee = serviceFee;
    }

    public Transaction() {
    }

    public String getOperationID() {
        return operationID;
    }

    public void setOperationID(String operationID) {
        this.operationID = operationID;
    }

    public String getCardFromData() {
        return cardFromData;
    }

    public void setCardFromData(String cardFromData) {
        this.cardFromData = cardFromData;
    }

    public String getCardToNumber() {
        return cardToNumber;
    }

    public void setCardToNumber(String cardToNumber) {
        this.cardToNumber = cardToNumber;
    }

    public Amount getAmount() {
        return amount;
    }

    public void setAmount(Amount amount) {
        this.amount = amount;
    }

    public double getServiceFee() {
        return serviceFee;
    }

    public void setServiceFee(Long serviceFee) {
        this.serviceFee = serviceFee;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Transaction that)) return false;
        return Objects.equals(operationID, that.operationID) && Objects.equals(cardFromData, that.cardFromData) && Objects.equals(cardToNumber, that.cardToNumber) && Objects.equals(amount, that.amount) && Objects.equals(serviceFee, that.serviceFee);
    }

    @Override
    public int hashCode() {
        return Objects.hash(operationID, cardFromData, cardToNumber, amount, serviceFee);
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "operationID='" + operationID + '\'' +
                ", cardFromData='" + cardFromData + '\'' +
                ", cardToNumber='" + cardToNumber + '\'' +
                ", amount=" + amount +
                ", serviceFee=" + serviceFee +
                '}';
    }
}
