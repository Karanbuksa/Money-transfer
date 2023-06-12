package com.example.moneytransferproj.service;

import com.example.moneytransferproj.dataclasses.ConfirmOperation;
import com.example.moneytransferproj.dataclasses.Transaction;
import com.example.moneytransferproj.dataclasses.TransferData;
import com.example.moneytransferproj.repository.TransactionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TransferService {

    private final TransactionRepository transactionRepository;

    public TransferService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public String transfer(TransferData transferData) {
        Transaction transaction = createTransaction(transferData);
        log.info("ID операции: %s, Номер карты списания: %s, Номер карты зачисления: %s Сумма: %d %s, Комиссия: %s"
                .formatted(transaction.getOperationID(),
                        transaction.getCardFromData(),
                        transaction.getCardToNumber(),
                        transaction.getAmount().getValue(),
                        transaction.getAmount().getCurrency(),
                        transaction.getServiceFee()));
        System.out.println("Осуществлён перевод");
        return transaction.getOperationID();
    }

    public String confirm(ConfirmOperation confirmOperation) {
        return confirmOperation.getOperationId();
    }

    public Transaction createTransaction(TransferData transferData) {
        int id = transactionRepository.getTransactionAmount() + 1;
        double serviceFee = transferData.getAmount().getValue() * 0.01;
        Transaction transaction = new Transaction(Integer.toString(id),
                transferData.getCardFromNumber(),
                transferData.getCardToNumber(),
                transferData.getAmount(),
                serviceFee);
        transactionRepository.putTransaction(Integer.toString(id), transaction);
        return transaction;
    }
}
