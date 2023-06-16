package com.example.moneytransferproj.repository;

import com.example.moneytransferproj.dataclasses.Transaction;
import com.example.moneytransferproj.repository.parsers.TransactionParser;
import org.springframework.stereotype.Repository;

import java.util.concurrent.ConcurrentHashMap;


@Repository
public class TransactionRepository {


    private static final ConcurrentHashMap<Integer, Transaction> transactionConcurrentHashMap = TransactionParser.readTransactionsFromFile();


    public TransactionRepository() {
    }

    public Transaction getTransaction(Integer id) {
        return transactionConcurrentHashMap.get(id);
    }

    public void putTransaction(Integer id, Transaction transaction) {
        transactionConcurrentHashMap.put(id, transaction);
    }

    public Integer getTransactionAmount() {
        return transactionConcurrentHashMap.size();
    }
}


