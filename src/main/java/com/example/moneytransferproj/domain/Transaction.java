package com.example.moneytransferproj.domain;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class Transaction {

    private String operationID;
    private String cardFromNumber;
    private String cardToNumber;
    private double amount;
    private String currency;
    private double serviceFee;
    private String operationResult;


}
