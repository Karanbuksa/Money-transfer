package com.example.moneytransferproj.repository;

import com.example.moneytransferproj.dataclasses.Card;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class CardsRepository {
    private static ConcurrentHashMap<String, Card> cardsConcurrentHashMap;

    static {
        try {
            cardsConcurrentHashMap = CardsFileReaderWriter.readCardFromFile();
        } catch (IOException ignored) {

        }
    }

}
