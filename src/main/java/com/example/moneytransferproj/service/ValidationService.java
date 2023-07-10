package com.example.moneytransferproj.service;

import com.example.moneytransferproj.data_transfer_objects.ConfirmOperation;
import com.example.moneytransferproj.entitys.Account;
import com.example.moneytransferproj.entitys.Transaction;
import com.example.moneytransferproj.exceptions.ConfirmationException;
import com.example.moneytransferproj.logger.TransactionLogger;
import com.example.moneytransferproj.repository.AccountsRepository;
import org.springframework.stereotype.Service;


@Service
public class ValidationService {
    private final AccountsRepository accountsRepository;

    public ValidationService(AccountsRepository accountsRepository) {
        this.accountsRepository = accountsRepository;
    }

    public String confirm(ConfirmOperation confirmOperation, Transaction transactionBuffer) {
        if (confirmOperation.code() != null) {
            Account fromAccount = accountsRepository.getAccountByCardNumber(transactionBuffer.getCardFromNumber());
            Account toAccount = accountsRepository.getAccountByCardNumber(transactionBuffer.getCardToNumber());

            fromAccount.withdraw(transactionBuffer.getAmount() + transactionBuffer.getServiceFee());
            toAccount.deposit(transactionBuffer.getAmount());

            accountsRepository.updateAccounts();

            transactionBuffer.setOperationResult("Проведена");
            TransactionLogger.log(transactionBuffer);
            System.out.println("Осуществлён перевод");

            return transactionBuffer.getOperationID();
        } else {
            throw new ConfirmationException("Неверный код", transactionBuffer);
        }
    }
}
