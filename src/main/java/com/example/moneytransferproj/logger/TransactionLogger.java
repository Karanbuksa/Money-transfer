package com.example.moneytransferproj.logger;

import com.example.moneytransferproj.entitys.Transaction;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TransactionLogger {
    public static void log(Transaction transaction) {
        log.info("ID операции: %s, Номер карты списания: %s, Номер карты зачисления: %s, Результат операции: %s"
                .formatted(transaction.getOperationID(),
                        transaction.getCardFromNumber(),
                        transaction.getCardToNumber(),
                        transaction.getOperationResult()));
    }
}
