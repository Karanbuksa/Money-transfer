package com.example.moneytransferproj.dto;

public record TransferData(String cardFromNumber, String cardFromValidTill, String cardFromCVV,
                           String cardToNumber, Amount amount) {
}