package com.example.moneytransferproj.repository;

import com.example.moneytransferproj.entitys.Account;
import com.example.moneytransferproj.repository.parsers.CardsFileParser;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class CardsRepository {


    private final CardsFileParser parser = new CardsFileParser();
    private final ConcurrentHashMap<String, Account> cardsConcurrentHashMap = parser.readCardFromFile();


    public CardsRepository() throws IOException {

    }


    public Account getAccountByCardNumber(String cardNumber) {
        return cardsConcurrentHashMap.get(cardNumber);
    }

    public void addAccount(Account account) {
        cardsConcurrentHashMap.put(account.getCardNumber(), account);
    }

    public void updateCards() {
        parser.writeString(parser.listToJson(cardsConcurrentHashMap.values().stream().toList()));
    }
}
