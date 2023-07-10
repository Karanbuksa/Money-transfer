package com.example.moneytransferproj.repository.parsers;

import com.example.moneytransferproj.entitys.Transaction;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class TransactionParser {
    private String path = "log/transfer.log";

    public void setPath(String path) {
        this.path = path;
    }

    public Transaction parseTransaction(String input) {
        String patternRegex = "(\\d{2}-\\d{2}-\\d{4} \\d{2}:\\d{2}:\\d{2}\\.\\d{3}) - ID операции: (.*?), Номер карты списания: (.*?), Номер карты зачисления: (.*?), Результат операции: (.*?)$";
        Pattern pattern = Pattern.compile(patternRegex);
        Matcher matcher = pattern.matcher(input);

        if (matcher.matches()) {
            String operationID = matcher.group(2);
            String cardFromData = matcher.group(3);
            String cardToNumber = matcher.group(4);
            String operationResult = matcher.group(5);

            return new Transaction(operationID, cardFromData, cardToNumber, 0, null, 0, operationResult);
        }

        return null;
    }

    public ConcurrentHashMap<Integer, Transaction> readTransactionsFromFile() {
        ConcurrentHashMap<Integer, Transaction> transactionMap = new ConcurrentHashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Transaction transaction = parseTransaction(line);
                transactionMap.put(Integer.parseInt(transaction.getOperationID()), transaction);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return transactionMap;
    }
}
