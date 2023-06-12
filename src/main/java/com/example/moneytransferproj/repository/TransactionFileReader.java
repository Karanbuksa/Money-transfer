package com.example.moneytransferproj.repository;

import com.example.moneytransferproj.dataclasses.Transaction;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

public class TransactionFileReader {

    public static ConcurrentHashMap<String, Transaction> readTransactionsFromFile() {
        ConcurrentHashMap<String, Transaction> transactionMap = new ConcurrentHashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader("log/transfer.log"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Transaction transaction = TransactionParser.parseTransaction(line);
                transactionMap.put(transaction.getOperationID(), transaction);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return transactionMap;
    }
}

