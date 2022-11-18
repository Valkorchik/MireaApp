package com.example.mireaapp;

import java.util.ArrayList;
import java.util.List;

public class CoinData {
    static ArrayList<CoinData> data = new ArrayList<>();
    int id;
    String name;

    public CoinData(int id, String name) {
        this.id = id;
        this.name = name;
    }


    public static void initDate() {
        int i = 0;
        for (String currency : currenciesList) {
            CoinData obj = new CoinData(i, currency);
            data.add(obj);
            i++;
        }
    }

    public static ArrayList<CoinData> getCoinDataList() {
        return data;
    }

    public static String[] currencyNames() {
        String[] names = new String[data.size()];
        for (int i = 0; i < data.size(); i++) {
            names[i] = data.get(i).name;
        }
        return names;
    }

    public String getName() {
        return this.name;
    }

    static List<String> currenciesList = new ArrayList<String>() {{
        add("AUD");
        add("BRL");
        add("CAD");
        add("CNY");
        add("EUR");
        add("CAD");
        add("HKD");
        add("IDR");
        add("ILS");
        add("INR");
        add("JPY");
        add("MXN");
        add("NOK");
        add("NZD");
        add("PLN");
        add("RON");
        add("RUB");
        add("SEK");
        add("SGD");
        add("USD");
        add("ZAR");
    }};


}