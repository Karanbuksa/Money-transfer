package com.example.moneytransferproj.repository;

import com.example.moneytransferproj.domain.Account;
import com.example.moneytransferproj.repository.parsers.CardsFileParser;
import org.springframework.stereotype.Repository;

import java.util.concurrent.ConcurrentHashMap;

@Repository
public class AccountsRepository {


    private final CardsFileParser parser;
    private final ConcurrentHashMap<String, Account> cardsConcurrentHashMap;


    public AccountsRepository(CardsFileParser parser) {
        this.parser = parser;
        this.cardsConcurrentHashMap = parser.readCardsFromFile();
    }


    public Account getAccountByCardNumber(String cardNumber) {
        return cardsConcurrentHashMap.get(cardNumber);
    }

    public void addAccount(Account account) {
        cardsConcurrentHashMap.put(account.getCardNumber(), account);
        updateAccounts();
    }

    public void removeAccountByCardNumber(String accountNumber) {
        cardsConcurrentHashMap.remove(accountNumber);
        updateAccounts();
    }

    public void updateAccounts() {
        parser.writeString(parser.listToJson(cardsConcurrentHashMap.values().stream().toList()));
    }
}
