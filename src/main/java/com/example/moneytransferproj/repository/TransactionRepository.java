package com.example.moneytransferproj.repository;

import com.example.moneytransferproj.entitys.Transaction;
import com.example.moneytransferproj.repository.parsers.TransactionParser;
import org.springframework.stereotype.Repository;

import java.util.concurrent.ConcurrentHashMap;


@Repository
public class TransactionRepository {

    private final TransactionParser parser = new TransactionParser();
    private final ConcurrentHashMap<Integer, Transaction> transactionConcurrentHashMap = parser.readTransactionsFromFile();


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


