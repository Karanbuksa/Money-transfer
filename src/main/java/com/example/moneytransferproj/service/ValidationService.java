package com.example.moneytransferproj.service;

import com.example.moneytransferproj.data_transfer_objects.ConfirmOperation;
import com.example.moneytransferproj.entitys.Account;
import com.example.moneytransferproj.entitys.Transaction;
import com.example.moneytransferproj.exceptions.ConfirmationException;
import com.example.moneytransferproj.logger.TransactionLogger;
import com.example.moneytransferproj.repository.CardsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ValidationService {
    private final CardsRepository cardsRepository;

    public ValidationService(CardsRepository cardsRepository){
        this.cardsRepository = cardsRepository;
    }

    public String confirm(ConfirmOperation confirmOperation, Transaction transactionBuffer) {
        if (confirmOperation.code() != null) {
            Account fromAccount = cardsRepository.getAccountByCardNumber(transactionBuffer.getCardFromNumber());
            Account toAccount = cardsRepository.getAccountByCardNumber(transactionBuffer.getCardToNumber());

            fromAccount.withdraw(transactionBuffer.getAmount() + transactionBuffer.getServiceFee());
            toAccount.deposit(transactionBuffer.getAmount());

            cardsRepository.updateCards();

            transactionBuffer.setOperationResult("Проведена");
            TransactionLogger.log(transactionBuffer);
            System.out.println("Осуществлён перевод");

            return transactionBuffer.getOperationID();
        } else {
            throw new ConfirmationException("Неверный код", transactionBuffer);
        }
    }
}
