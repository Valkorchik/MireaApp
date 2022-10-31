package com.example.mireaapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CurrencyTransfer extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
     static TextView BTCcard1;
     static TextView ETHcard1;
     static TextView LTCcard1;
     Button buttonSave;
     int state;
    NumberPicker picker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transfer_screen);
        BTCcard1=findViewById(R.id.BTCcard1Value);
        ETHcard1=findViewById(R.id.ETHCard1Value);
        LTCcard1=findViewById(R.id.LTCCard1Value);
        buttonSave=findViewById(R.id.buttonSave);
        picker=findViewById(R.id.valuePicker);
        CoinData.initDate();
        picker.setMaxValue(CoinData.getCoinDataList().size()-1);
        picker.setMinValue(0);
        picker.setDisplayedValues(CoinData.currencyNames());
        picker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                state=i1;

            }
        });
        buttonSave.setOnClickListener(view -> {
            new CoinCalculate().execute(CoinData.getCoinDataList().get(state).getName());
        });
        bottomNavigationView= findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.home:
                    Intent intentHome=new Intent(this,MainMenu.class);
                    startActivity(intentHome);
                    finish();
                    onDestroy();
                    break;
                case R.id.convert:
                    Toast.makeText(this, "You are already on that screen", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.settings:
                    Intent intentSettings=new Intent(this,Settings.class);
                    startActivity(intentSettings);
                    finish();
                    onDestroy();
                    break;


            }
            return true;
        });
    }

    static class CoinCalculate  extends AsyncTask<String, Void, Map<String,String>>
    {
        List<String> cryptoList = new ArrayList<String>() {
            {
                add("BTC");
                add("ETH");
                add("LTC");
            }};

        static final String coinAPIURL = "https://rest.coinapi.io/v1/exchangerate";
        static final String apiKey = "559C7CDB-459A-4337-9023-C3DA04D4FF78";

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
        private  String readAll(Reader rd) throws IOException {
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


