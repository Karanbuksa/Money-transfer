package com.example.moneytransferproj.exceptions;

import com.example.moneytransferproj.dataclasses.Transaction;

public class ConfirmationException extends MyExceptionAncestor {

    public ConfirmationException(String message, Transaction transaction) {
        super(message, transaction);

    }
}
