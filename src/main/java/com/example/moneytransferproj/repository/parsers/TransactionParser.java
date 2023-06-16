package com.example.moneytransferproj.repository.parsers;

import com.example.moneytransferproj.dataclasses.Transaction;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TransactionParser {
    private static final String PATTERN_REGEX = "(\\d{2}-\\d{2}-\\d{4} \\d{2}:\\d{2}:\\d{2}\\.\\d{3}) - ID операции: (.*?), Номер карты списания: (.*?), Номер карты зачисления:(.*?) Сумма: (.*?) (.*?), Комиссия: (.*?) (.*?), Результат операции: (.*?)$";

    public static Transaction parseTransaction(String input) {
        Pattern pattern = Pattern.compile(PATTERN_REGEX);
        Matcher matcher = pattern.matcher(input);

        if (matcher.matches()) {
            String operationID = matcher.group(2);
            String cardFromData = matcher.group(3);
            String cardToNumber = matcher.group(4);
            double amountValue = Double.parseDouble(matcher.group(5).replace(",", "."));
            String amountCurrency = matcher.group(6);
            double serviceFee = Double.parseDouble(matcher.group(7).replace(",", "."));
            String operationResult = matcher.group(9);

            return new Transaction(operationID, cardFromData, cardToNumber, amountValue, amountCurrency, serviceFee, operationResult);
        }

        return null;
    }

    public static ConcurrentHashMap<Integer, Transaction> readTransactionsFromFile() {
        ConcurrentHashMap<Integer, Transaction> transactionMap = new ConcurrentHashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader("log/transfer.log"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Transaction transaction = TransactionParser.parseTransaction(line);
                transactionMap.put(Integer.parseInt(transaction.getOperationID()), transaction);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return transactionMap;
    }
}
