package com.example.moneytransferproj.exceptions;

import com.example.moneytransferproj.dataclasses.Transaction;

public class InputDataException extends MyExceptionAncestor {

    public InputDataException(String message, Transaction transaction) {
        super(message, transaction);
    }
}
