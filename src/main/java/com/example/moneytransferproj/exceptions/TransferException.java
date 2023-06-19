package com.example.moneytransferproj.exceptions;

import com.example.moneytransferproj.dataclasses.Transaction;

public class TransferException extends MyExceptionAncestor {

    public TransferException(String message, Transaction transaction) {
        super(message, transaction);
    }
}
