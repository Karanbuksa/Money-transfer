package com.example.moneytransferproj.data_transfer_objects;

public record TransferData(String cardFromNumber, String cardFromValidTill, String cardFromCVV,
                           String cardToNumber, Amount amount) {
}