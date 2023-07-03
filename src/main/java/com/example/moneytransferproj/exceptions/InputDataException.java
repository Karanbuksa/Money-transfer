package com.example.moneytransferproj.exceptions;

import com.example.moneytransferproj.entitys.Transaction;

public class InputDataException extends ExceptionAncestor {

    public InputDataException(String message, Transaction transaction) {
        super(message, transaction);
    }
}
