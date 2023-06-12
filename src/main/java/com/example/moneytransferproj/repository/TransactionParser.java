package com.example.moneytransferproj.repository;

import com.example.moneytransferproj.dataclasses.Amount;
import com.example.moneytransferproj.dataclasses.Transaction;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TransactionParser {
    private static final String PATTERN_REGEX = "(\\d{2}-\\d{2}-\\d{4} \\d{2}:\\d{2}:\\d{2}\\.\\d{3}) - ID операции: (.*?), Номер карты списания: (.*?), Номер карты зачисления:(.*?) Сумма: (.*?) (.*?), Комиссия: (.*?)$";
    public static Transaction parseTransaction(String input) {
        Pattern pattern = Pattern.compile(PATTERN_REGEX);
        Matcher matcher = pattern.matcher(input);

        if (matcher.matches()) {
            String operationID = matcher.group(2);
            String cardFromData = matcher.group(3);
            String cardToNumber = matcher.group(4);
            Integer amountValue = Integer.parseInt(matcher.group(5));
            String amountCurrency = matcher.group(6);
            double serviceFee = Double.parseDouble(matcher.group(7));

            Amount amount = new Amount(amountValue, amountCurrency);
            return new Transaction(operationID, cardFromData, cardToNumber, amount, serviceFee);
        }

        return null;
    }
}
