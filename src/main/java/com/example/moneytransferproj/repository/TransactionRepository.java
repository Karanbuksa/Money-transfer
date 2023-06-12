package com.example.moneytransferproj.repository;

import com.example.moneytransferproj.dataclasses.Transaction;
import org.springframework.stereotype.Repository;

import java.util.concurrent.ConcurrentHashMap;


@Repository
public class TransactionRepository {


    private static final ConcurrentHashMap<String, Transaction> transactionConcurrentHashMap = TransactionFileReader.readTransactionsFromFile();



    public TransactionRepository() {
    }

    public Transaction getTransaction(String id) {
        return transactionConcurrentHashMap.get(id);
    }

    public void putTransaction(String id, Transaction transaction) {
        transactionConcurrentHashMap.put(id, transaction);
    }

    public Integer getTransactionAmount() {
        return transactionConcurrentHashMap.size();
    }
}


