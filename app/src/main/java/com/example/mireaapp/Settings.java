package com.example.mireaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Settings extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_screen);
        button=findViewById(R.id.changePasswordButton);
        button.setOnClickListener(view -> {
            Intent intent=new Intent(this,ChangePassword.class);
            startActivity(intent);
        });
        bottomNavigationView= findViewById(R.id.bottomNavigationView);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.home:
                    Intent intentHome=new Intent(this,MainMenu.class);
                    startActivity(intentHome);
                    break;
                case R.id.convert:
                    Intent intentConvert=new Intent(this,CurrencyTransfer.class);
                    startActivity(intentConvert);

                    break;
                case R.id.settings:
                    Toast.makeText(this, "You are already on that screen", Toast.LENGTH_SHORT).show();
                    break;


            }
            return true;
        });
    }
}