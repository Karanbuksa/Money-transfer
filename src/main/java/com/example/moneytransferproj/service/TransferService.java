package com.example.moneytransferproj.service;

import com.example.moneytransferproj.dto.TransferData;
import com.example.moneytransferproj.domain.Account;
import com.example.moneytransferproj.domain.Transaction;
import com.example.moneytransferproj.exceptions.InputDataException;
import com.example.moneytransferproj.repository.AccountsRepository;
import com.example.moneytransferproj.repository.TransactionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TransferService {

    private final TransactionRepository transactionRepository;
    private final AccountsRepository accountsRepository;

    public TransferService(TransactionRepository transactionRepository, AccountsRepository accountsRepository) {
        this.transactionRepository = transactionRepository;
        this.accountsRepository = accountsRepository;
    }

    public Transaction transfer(TransferData transferData) {
        Transaction transaction = createTransaction(transferData);
        Account fromAccount = accountsRepository.getAccountByCardNumber(transaction.getCardFromNumber());
        Account toAccount = accountsRepository.getAccountByCardNumber(transaction.getCardToNumber());

        if (fromAccount == null || toAccount == null) {
            transaction.setOperationResult("Не проведена: Карты не существует");
            log(transaction);
            throw new InputDataException("Карты не существует", transaction);
        }

        if (!fromAccount.getCvv().equals(transferData.cardFromCVV()) ||
                !fromAccount.getCardDate().equals(transferData.cardFromValidTill())) {
            transaction.setOperationResult("Не проведена: Неверные данные карты");
            log(transaction);
            throw new InputDataException("Неверные данные карты", transaction);
        }

        if (fromAccount.getBalance() < transaction.getAmount()) {
            transaction.setOperationResult("Не проведена: Недостаточно средств");
            log(transaction);
            throw new InputDataException("Недостаточно средств", transaction);
        }
        return transaction;
    }

    private static void log(Transaction transaction) {
        log.info("ID операции: %s, Номер карты списания: %s, Номер карты зачисления: %s, Результат операции: %s"
                .formatted(transaction.getOperationID(),
                        transaction.getCardFromNumber(),
                        transaction.getCardToNumber(),
                        transaction.getOperationResult()));
    }

    public Transaction createTransaction(TransferData transferData) {
        final int idIncrement = 1;
        final double amountPercentage = 0.01;
        final double serviceFeePercentage = 0.0001;

        int id = transactionRepository.getTransactionAmount() + idIncrement;
        double amount = transferData.amount().value() * amountPercentage;
        double serviceFee = transferData.amount().value() * serviceFeePercentage;
        Transaction transaction = new Transaction();
        transaction.setOperationID(Integer.toString(id));
        transaction.setCardFromNumber(transferData.cardFromNumber());
        transaction.setCardToNumber(transferData.cardToNumber());
        transaction.setCurrency(transferData.amount().currency());
        transaction.setAmount(amount);
        transaction.setServiceFee(serviceFee);
        transactionRepository.putTransaction(id, transaction);

        return transaction;
    }

}
