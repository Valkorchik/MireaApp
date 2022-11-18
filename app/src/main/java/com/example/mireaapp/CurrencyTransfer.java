package com.example.mireaapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CurrencyTransfer extends AppCompatActivity {
    private static TextView BTCcard1;
    private static TextView ETHcard1;
    private static TextView LTCcard1;
    private int state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transfer_screen);
        BTCcard1 = findViewById(R.id.BTCcard1Value);
        ETHcard1 = findViewById(R.id.ETHCard1Value);
        LTCcard1 = findViewById(R.id.LTCCard1Value);
        Button buttonSave = findViewById(R.id.buttonSave);
        NumberPicker picker = findViewById(R.id.valuePicker);
        CoinData.initDate();
        picker.setMaxValue(CoinData.getCoinDataList().size() - 1);
        picker.setMinValue(0);
        picker.setDisplayedValues(CoinData.currencyNames());
        picker.setOnValueChangedListener((numberPicker, i, i1) -> state = i1);
        buttonSave.setOnClickListener(view -> {
            new CoinCalculate().execute(CoinData.getCoinDataList().get(state).getName());
        });
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.home:
                    Intent intentHome = new Intent(this, MainMenu.class);
                    startActivity(intentHome);
                    finish();
                    break;
                case R.id.convert:
                    Toast.makeText(this, "You are already on that screen", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.settings:
                    Intent intentSettings = new Intent(this, Settings.class);
                    startActivity(intentSettings);
                    finish();
                    break;


            }
            return true;
        });
    }

    static class CoinCalculate extends AsyncTask<String, Void, Map<String, String>> {
        List<String> cryptoList = new ArrayList<String>() {
            {
                add("BTC");
                add("ETH");
                add("LTC");
            }
        };

        static final String coinAPIURL = "https://rest.coinapi.io/v1/exchangerate";
        static final String apiKey = "C905DB37-BA86-43E7-9473-84CD2E658DCA";

        @Override
        protected Map<String, String> doInBackground(String... selectedCurrency) {

            Map<String, String> cryptoPrices = new HashMap<>();
            for (String crypto : cryptoList) {
                String url = String.format("%s/%s/%s?apikey=%s", coinAPIURL, crypto, selectedCurrency[0], apiKey);
                InputStream is = null;
                try {
                    is = new URL(url).openStream();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    BufferedReader decodedData = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
                    String jsonText = readAll(decodedData);
                    JSONObject json = new JSONObject(jsonText);
                    int price = json.getInt("rate");
                    System.out.println(price);
                    cryptoPrices.put(crypto, String.valueOf(price));

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (is != null) {
                            is.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return cryptoPrices;
        }

        private String readAll(Reader rd) throws IOException {
            StringBuilder sb = new StringBuilder();
            int cp;
            while ((cp = rd.read()) != -1) {
                sb.append((char) cp);
            }
            return sb.toString();
        }

        protected void onPostExecute(Map<String, String> feed) {
            BTCcard1.setText(String.valueOf(feed.get("BTC")));
            ETHcard1.setText(String.valueOf(feed.get("ETH")));
            LTCcard1.setText(String.valueOf(feed.get("LTC")));
        }

    }
}


