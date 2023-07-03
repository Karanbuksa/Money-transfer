package com.example.moneytransferproj.exceptions;

import com.example.moneytransferproj.entitys.Transaction;

public class TransferException extends ExceptionAncestor {

    public TransferException(String message, Transaction transaction) {
        super(message, transaction);
    }
}
