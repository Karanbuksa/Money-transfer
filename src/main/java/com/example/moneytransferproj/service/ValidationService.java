package com.example.moneytransferproj.service;

import com.example.moneytransferproj.dto.ConfirmOperation;
import com.example.moneytransferproj.domain.Account;
import com.example.moneytransferproj.domain.Transaction;
import com.example.moneytransferproj.exceptions.ConfirmationException;
import com.example.moneytransferproj.repository.AccountsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
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
            log.info("ID операции: %s, Номер карты списания: %s, Номер карты зачисления: %s, Результат операции: %s"
                    .formatted(transactionBuffer.getOperationID(),
                            transactionBuffer.getCardFromNumber(),
                            transactionBuffer.getCardToNumber(),
                            transactionBuffer.getOperationResult()));
            System.out.println("Осуществлён перевод");

            return transactionBuffer.getOperationID();
        } else {
            throw new ConfirmationException("Неверный код", transactionBuffer);
        }
    }
}
