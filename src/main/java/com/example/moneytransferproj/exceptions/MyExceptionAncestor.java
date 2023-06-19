package com.example.moneytransferproj.exceptions;

import com.example.moneytransferproj.dataclasses.Transaction;

public abstract class MyExceptionAncestor extends RuntimeException {
    private final Transaction transaction;

    public MyExceptionAncestor(String message, Transaction transaction) {
        super(message);
        this.transaction = transaction;
    }

    public Transaction getTransaction() {
        return transaction;
    }
}
