package com.example.moneytransferproj.service;

import com.example.moneytransferproj.data_transfer_objects.TransferData;
import com.example.moneytransferproj.entitys.Account;
import com.example.moneytransferproj.entitys.Transaction;
import com.example.moneytransferproj.exceptions.InputDataException;
import com.example.moneytransferproj.logger.TransactionLogger;
import com.example.moneytransferproj.repository.CardsRepository;
import com.example.moneytransferproj.repository.TransactionRepository;
import org.springframework.stereotype.Service;


@Service
public class TransferService {

    private final TransactionRepository transactionRepository;
    private final CardsRepository cardsRepository;

    public TransferService(TransactionRepository transactionRepository, CardsRepository cardsRepository) {
        this.transactionRepository = transactionRepository;
        this.cardsRepository = cardsRepository;
    }

    public Transaction transfer(TransferData transferData) {
        Transaction transaction = createTransaction(transferData);
        Account fromAccount = cardsRepository.getAccountByCardNumber(transaction.getCardFromNumber());
        Account toAccount = cardsRepository.getAccountByCardNumber(transaction.getCardToNumber());

        if (fromAccount == null || toAccount == null) {
            transaction.setOperationResult("Не проведена: Карты не существует");
            TransactionLogger.log(transaction);
            throw new InputDataException("Карты не существует", transaction);
        }

        if (!fromAccount.getCvv().equals(transferData.cardFromCVV()) ||
                !fromAccount.getCardDate().equals(transferData.cardFromValidTill())) {
            transaction.setOperationResult("Не проведена: Неверные данные карты");
            TransactionLogger.log(transaction);
            throw new InputDataException("Неверные данные карты", transaction);
        }

        if (fromAccount.getBalance() < transaction.getAmount()) {
            transaction.setOperationResult("Не проведена: Недостаточно средств");
            TransactionLogger.log(transaction);
            throw new InputDataException("Недостаточно средств", transaction);
        }
        return transaction;
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
