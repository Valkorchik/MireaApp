package com.example.mireaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainMenu extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    Button addButton;
    FrameLayout toggler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_screen);
        bottomNavigationView= findViewById(R.id.bottomNavigationView);

        bottomNavigationView.setOnItemSelectedListener(item -> {
           switch (item.getItemId()){
               case R.id.home:
                   Toast.makeText(this, "You are already on that screen", Toast.LENGTH_SHORT).show();
                   break;
               case R.id.convert:
                   //Intent intentConvert=new Intent(this,Registration.class);
                 //  startActivity(intentConvert);
                   break;
               case R.id.settings:
                  // Intent intentSettings=new Intent(this,Login.class);
                 //  startActivity(intentSettings);
                   break;


           }
           return true;
       });
        addButton=findViewById(R.id.addCardButton);
        toggler=findViewById(R.id.second_card);
        addButton.setOnClickListener(view->{
            toggler.setVisibility(View.VISIBLE);
            addButton.setVisibility(View.INVISIBLE);
        });
    }
}