package com.example.moneytransferproj.exceptions;

import com.example.moneytransferproj.domain.Transaction;

public class InputDataException extends ExceptionAncestor {

    public InputDataException(String message, Transaction transaction) {
        super(message, transaction);
    }
}
