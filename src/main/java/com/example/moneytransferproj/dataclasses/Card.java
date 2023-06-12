package com.example.moneytransferproj.dataclasses;

import java.util.Objects;

public class Card {
    private String cardNumber;
    private String cardDate;
    private String CVC;

    public Card() {
    }

    public Card(String cardNumber, String cardDate, String CVC) {
        this.cardNumber = cardNumber;
        this.cardDate = cardDate;
        this.CVC = CVC;
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

    public String getCVC() {
        return CVC;
    }

    public void setCVC(String CVC) {
        this.CVC = CVC;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Card card)) return false;
        return Objects.equals(cardNumber, card.cardNumber) && Objects.equals(cardDate, card.cardDate) && Objects.equals(CVC, card.CVC);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cardNumber, cardDate, CVC);
    }

    @Override
    public String toString() {
        return "Card{" +
                "cardNumber='" + cardNumber + '\'' +
                ", cardDate='" + cardDate + '\'' +
                ", CVC='" + CVC + '\'' +
                '}';
    }
}
