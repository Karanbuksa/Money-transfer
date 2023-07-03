package com.example.moneytransferproj.exceptions;

import com.example.moneytransferproj.entitys.Transaction;

public abstract class ExceptionAncestor extends RuntimeException {
    private final Transaction transaction;

    public ExceptionAncestor(String message, Transaction transaction) {
        super(message);
        this.transaction = transaction;
    }

    public Transaction getTransaction() {
        return transaction;
    }
}
