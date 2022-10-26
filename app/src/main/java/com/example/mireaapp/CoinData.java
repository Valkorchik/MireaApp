package com.example.mireaapp;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.net.HttpURLConnection;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.crypto.CipherInputStream;

public class CoinData {
    static ArrayList<CoinData> data;
    int id;
    String name;
    public CoinData(int id, String name)
    {
        this.id=id;
        this.name=name;
    }
    public static void initDate()
    {
        int i=0;
        for (String currency: currenciesList)
        {
            CoinData obj=new CoinData(i,currency);
            data.add(obj);
            i++;
        }
    }
    public static ArrayList<CoinData> getCoinDataList()
    {
        return data;
    }
    public static String[] currencyNames(){
        String[] names =new String[data.size()];
        for (int i=0;i<data.size();i++)
        {
            names[i]=data.get(i).name;
        }
        return names;
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
    static List<String> cryptoList =new ArrayList<String>()
    {
        {
            add("BTC");
            add("ETH");
            add("LTC");
        }
    };
    static final String coinAPIURL = "https://rest.coinapi.io/v1/exchangerate";
    static final String apiKey = "F610029C-8CAB-49B6-9094-6290437BDFBD";
    static Map<String, String> getCoinData(String selectedCurrency) {
        Map<String, String> cryptoPrices = new HashMap<>();
        HttpURLConnection connection=null;
        for (String crypto : cryptoList) {
            String url = String.format("%s/%s/%s?apikey=%s", coinAPIURL,crypto,selectedCurrency,apiKey) ;
            try {
                connection=(HttpURLConnection)  new URL(url).openConnection();
                connection.setRequestMethod("GET");
                connection.setUseCaches(false);
                connection.connect();
                if(HttpURLConnection.HTTP_OK==connection.getResponseCode())
                {
                    BufferedReader in= new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    StringBuffer decodedData=new StringBuffer();
                    String line;
                    while((line=in.readLine())!=null)
                    {
                        decodedData.append(line);
                    }
                    JSONObject response=new JSONObject(decodedData.toString());
                    int price=response.getInt("rate");
                    cryptoPrices.put(crypto,String.valueOf(price));
                }
            }catch (Throwable cause){
                cause.printStackTrace();
            }finally {
                if (connection!=null){
                    connection.disconnect();
                }
            }
        }
        return cryptoPrices;
    }

}
