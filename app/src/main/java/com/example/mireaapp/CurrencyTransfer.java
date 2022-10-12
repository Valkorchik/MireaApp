package com.example.mireaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class CurrencyTransfer extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transfer_screen);
        //TODO: Добавить кард апи и смену валют снизу
        bottomNavigationView= findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.home:
                    Intent intentHome=new Intent(this,MainMenu.class);
                    startActivity(intentHome);
                    break;
                case R.id.convert:
                    Toast.makeText(this, "You are already on that screen", Toast.LENGTH_SHORT).show();

                    break;
                case R.id.settings:
                    Intent intentSettings=new Intent(this,Settings.class);
                    startActivity(intentSettings);
                    break;


            }
            return true;
        });
    }
}