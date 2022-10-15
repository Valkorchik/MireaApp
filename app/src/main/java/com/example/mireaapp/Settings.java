package com.example.mireaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class Settings extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    Button buttonChangePassword;
    Button logOut;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_screen);
        buttonChangePassword=findViewById(R.id.changePasswordButton);
        logOut=findViewById(R.id.LogOutButton);
        logOut.setOnClickListener(view -> {
            FirebaseAuth.getInstance().signOut();
            Intent intentLogOut=new Intent(this,Main.class);
            startActivity(intentLogOut);
            finish();
        });

        buttonChangePassword.setOnClickListener(view -> {
            Intent intent=new Intent(this,ChangePassword.class);
            startActivity(intent);
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
                    Intent intentConvert=new Intent(this,CurrencyTransfer.class);
                    startActivity(intentConvert);
                    finish();

                    break;
                case R.id.settings:
                    Toast.makeText(this, "You are already on that screen", Toast.LENGTH_SHORT).show();
                    break;


            }
            return true;
        });
    }
}