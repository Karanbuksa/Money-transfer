package com.example.moneytransferproj.repository.parsers;

import com.example.moneytransferproj.entitys.Account;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class CardsFileParser {


    public ConcurrentHashMap<String, Account> readCardFromFile() throws IOException {

        List<Account> cardsList = jsonToList();
        ConcurrentHashMap<String, Account> cardMap = new ConcurrentHashMap<>();
        cardsList.forEach(x -> cardMap.put(x.getCardNumber(), x));

        return cardMap;
    }

    public List<Account> jsonToList() {
        JSONParser jsonParser = new JSONParser();
        JSONArray jsonArray = new JSONArray();
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        List<Account> employeeList = new ArrayList<>();
        try (Reader fileReader = new FileReader("cards/cards.json")) {
            Object object = jsonParser.parse(fileReader);
            jsonArray = (JSONArray) object;
        } catch (ParseException | IOException ignored) {

        }
        for (Object object : jsonArray) {
            Account account = gson.fromJson(object.toString(), Account.class);
            employeeList.add(account);

        }
        return employeeList;
    }

    public String listToJson(List<Account> list) {
        Type listType = new TypeToken<List<Account>>() {
        }.getType();
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setPrettyPrinting();
        Gson gson = gsonBuilder.create();
        return gson.toJson(list, listType);
    }

    public void writeString(String string) {
        try (FileWriter fileWriter = new FileWriter("cards/cards.json")) {
            fileWriter.write(string);
            fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
