package com.example.mireaapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

import java.util.HashMap;
import java.util.Map;

public class CurrencyTransfer extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    TextView BTCcard1;
    TextView ETHcard1;
    TextView LTCcard1;
    TextView BTCcard2;
    TextView ETHcard2;
    TextView LTCcard2;
    NumberPicker picker;
    String selectedCurrency = "AUD";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transfer_screen);
        BTCcard1=findViewById(R.id.BTCcard1Value);
        ETHcard1=findViewById(R.id.ETHCard1Value);
        LTCcard1=findViewById(R.id.LTCCard1Value);
        BTCcard2=findViewById(R.id.BTCcard2Value);
        ETHcard2=findViewById(R.id.ETHCard2Value);
        LTCcard2=findViewById(R.id.LTCCard2Value);
        picker=findViewById(R.id.valuePicker);
        CoinData.initDate();
        picker.setMaxValue(CoinData.getCoinDataList().size()-1);
        picker.setMinValue(0);
        picker.setDisplayedValues(CoinData.currencyNames());
        picker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                Map<String,String> data= CoinData.getCoinData(CoinData.getCoinDataList().get(i1).name);
                BTCcard1.setText(String.valueOf(data.get("BTC")));
                ETHcard1.setText(String.valueOf(data.get("ETH")));
                LTCcard1.setText(String.valueOf(data.get("LTC")));


            }
        });
        bottomNavigationView= findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.home:
                    Intent intentHome=new Intent(this,MainMenu.class);
                    startActivity(intentHome);
                    finish();
                    break;
                case R.id.convert:
                    Toast.makeText(this, "You are already on that screen", Toast.LENGTH_SHORT).show();

                    break;
                case R.id.settings:
                    Intent intentSettings=new Intent(this,Settings.class);
                    startActivity(intentSettings);
                    finish();
                    break;


            }
            return true;
        });
    }
}