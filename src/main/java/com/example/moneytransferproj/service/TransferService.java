package com.example.moneytransferproj.service;

import com.example.moneytransferproj.dataclasses.Account;
import com.example.moneytransferproj.dataclasses.ConfirmOperation;
import com.example.moneytransferproj.dataclasses.Transaction;
import com.example.moneytransferproj.dataclasses.TransferData;
import com.example.moneytransferproj.exceptions.ConfirmationException;
import com.example.moneytransferproj.exceptions.InputDataException;
import com.example.moneytransferproj.repository.CardsRepository;
import com.example.moneytransferproj.repository.TransactionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TransferService {

    private final TransactionRepository transactionRepository;
    private final CardsRepository cardsRepository;

    private static Transaction transactionBuffer;

    public TransferService(TransactionRepository transactionRepository, CardsRepository cardsRepository) {
        this.transactionRepository = transactionRepository;
        this.cardsRepository = cardsRepository;
    }

    public String transfer(TransferData transferData) {
        Transaction transaction = createTransaction(transferData);
        Account fromAccount = cardsRepository.getAccountByCardNumber(transaction.getCardFromNumber());
        Account toAccount = cardsRepository.getAccountByCardNumber(transaction.getCardToNumber());

        if (fromAccount == null || toAccount == null) {
            transaction.setOperationResult("Не проведена: Карты не существует");
            log(transaction);
            throw new InputDataException("Карты не существует",transaction);
        }

        if (!fromAccount.getCVV().equals(transferData.getCardFromCVV()) ||
                !fromAccount.getCardDate().equals(transferData.getCardFromValidTill())) {
            transaction.setOperationResult("Не проведена: Неверные данные карты");
            log(transaction);
            throw new InputDataException("Неверные данные карты",transaction);
        }

        if (fromAccount.getBalance() < transaction.getAmount()) {
            transaction.setOperationResult("Не проведена: Недостаточно средств");
            log(transaction);
            throw new InputDataException("Недостаточно средств",transaction);
        }

        transactionBuffer = transaction;

        return transaction.getOperationID();
    }


    public String confirm(ConfirmOperation confirmOperation) {
        if (confirmOperation.getCode() != null) {
            Account fromAccount = cardsRepository.getAccountByCardNumber(transactionBuffer.getCardFromNumber());
            Account toAccount = cardsRepository.getAccountByCardNumber(transactionBuffer.getCardToNumber());

            fromAccount.withdraw(transactionBuffer.getAmount() + transactionBuffer.getServiceFee());
            toAccount.deposit(transactionBuffer.getAmount());
            cardsRepository.updateCards();
            transactionBuffer.setOperationResult("Проведена");
            log(transactionBuffer);
            System.out.println("Осуществлён перевод");

            return transactionBuffer.getOperationID();
        } else {
            throw new ConfirmationException("Неверный код", transactionBuffer);
        }
    }

    public Transaction createTransaction(TransferData transferData) {
        int id = transactionRepository.getTransactionAmount() + 1;
        double amount = transferData.getAmount().getValue() * 0.01;
        double serviceFee = transferData.getAmount().getValue() * 0.0001;
        Transaction transaction = new Transaction();
        transaction.setOperationID(Integer.toString(id));
        transaction.setCardFromNumber(transferData.getCardFromNumber());
        transaction.setCardToNumber(transferData.getCardToNumber());
        transaction.setCurrency(transferData.getAmount().getCurrency());
        transaction.setAmount(amount);
        transaction.setServiceFee(serviceFee);
        transactionRepository.putTransaction(id, transaction);
        return transaction;
    }

    private static void log(Transaction transaction) {
        log.info("ID операции: %s, Номер карты списания: %s, Номер карты зачисления: %s Сумма: %.2f %s, Комиссия: %.2f %s, Результат операции: %s"
                .formatted(transaction.getOperationID(),
                        transaction.getCardFromNumber(),
                        transaction.getCardToNumber(),
                        transaction.getAmount(),
                        transaction.getCurrency(),
                        transaction.getServiceFee(),
                        transaction.getCurrency(),
                        transaction.getOperationResult()));
    }
}
