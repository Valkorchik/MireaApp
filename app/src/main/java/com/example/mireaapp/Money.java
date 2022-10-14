package com.example.mireaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Money extends AppCompatActivity {
    Button backButton;
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.money_screen);
        backButton=findViewById(R.id.backButton);
        backButton.setOnClickListener(view -> {
            Intent intent=new Intent(this,MainMenu.class);
            startActivity(intent);
            finish();
        });
        bottomNavigationView= findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.home:
                    Intent intentHome = new Intent(this, CurrencyTransfer.class);
                    startActivity(intentHome);
                    finish();
                    break;
                case R.id.convert:
                    Intent intentConvert = new Intent(this, CurrencyTransfer.class);
                    startActivity(intentConvert);
                    finish();
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
}