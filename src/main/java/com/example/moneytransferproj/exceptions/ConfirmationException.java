package com.example.moneytransferproj.exceptions;

import com.example.moneytransferproj.domain.Transaction;

public class ConfirmationException extends ExceptionAncestor {

    public ConfirmationException(String message, Transaction transaction) {
        super(message, transaction);

    }
}
