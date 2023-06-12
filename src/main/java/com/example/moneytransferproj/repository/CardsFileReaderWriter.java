package com.example.moneytransferproj.repository;

import com.example.moneytransferproj.dataclasses.Card;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class CardsFileReaderWriter {
    public static ConcurrentHashMap<String, Card> readCardFromFile() throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();

        List<Card> cardsList = objectMapper.readValue(new FileInputStream("cards/cards.json"), List.class);

        ConcurrentHashMap<String, Card> cardMap = new ConcurrentHashMap<>();

        cardsList.forEach(x -> cardMap.put(x.getCardNumber(), x));

        return cardMap;
    }
}
