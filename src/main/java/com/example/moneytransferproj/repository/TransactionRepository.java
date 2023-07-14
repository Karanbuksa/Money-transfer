package com.example.moneytransferproj.repository;

import com.example.moneytransferproj.domain.Transaction;
import com.example.moneytransferproj.repository.parsers.TransactionParser;
import org.springframework.stereotype.Repository;

import java.util.concurrent.ConcurrentHashMap;


@Repository
public class TransactionRepository {

    private final ConcurrentHashMap<Integer, Transaction> transactionConcurrentHashMap;


    public TransactionRepository(TransactionParser parser) {
        transactionConcurrentHashMap = parser.readTransactionsFromFile();
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


