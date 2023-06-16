package com.example.moneytransferproj.repository;

import com.example.moneytransferproj.dataclasses.Account;
import com.example.moneytransferproj.repository.parsers.CardsFileParser;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class CardsRepository {
    private static ConcurrentHashMap<String, Account> cardsConcurrentHashMap;

    static {
        try {
            cardsConcurrentHashMap = CardsFileParser.readCardFromFile();
        } catch (IOException ignored) {

        }
    }

    public CardsRepository() {
    }

    public Account getAccountByCardNumber(String cardNumber) {
        return cardsConcurrentHashMap.get(cardNumber);
    }

    public void addAccount(Account account) {
        cardsConcurrentHashMap.put(account.getCardNumber(), account);
    }

    public void updateCards() {
        CardsFileParser.writeString(CardsFileParser.listToJson(cardsConcurrentHashMap.values().stream().toList()));
    }
}
