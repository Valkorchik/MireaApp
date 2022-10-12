package com.example.mireaapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainMenu extends AppCompatActivity {
    private static final String EDIT_TEXT_VISIBLE = "edit_text_visible";
    private boolean mEditTextVisible;
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
                   Intent intentConvert=new Intent(this,CurrencyTransfer.class);
                   startActivity(intentConvert);
                   break;
               case R.id.settings:
                  Intent intentSettings=new Intent(this,Settings.class);
                 startActivity(intentSettings);
                   break;


           }
            if (savedInstanceState != null) {
                mEditTextVisible = savedInstanceState.getBoolean(EDIT_TEXT_VISIBLE);
                toggler.setVisibility(mEditTextVisible ? View.VISIBLE : View.INVISIBLE);
                addButton.setVisibility(mEditTextVisible ? View.VISIBLE : View.INVISIBLE);
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

    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(EDIT_TEXT_VISIBLE, mEditTextVisible);
    }

}