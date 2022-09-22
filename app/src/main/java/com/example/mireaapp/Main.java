package com.example.mireaapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;



public class Main extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen);
        Button login = findViewById(R.id.loginButton);
        login.setOnClickListener(view -> {
            Log.i("Info", "Log in button tapped");
            Intent intent= new Intent(this,Login.class);
            startActivity(intent);
        });


        Button signup = findViewById(R.id.signupButton);
        signup.setOnClickListener(view -> {
            Log.i("Info", "Sign up button tapped");
            Intent intent=new Intent(this,Registration.class);
            startActivity(intent);
        });
    }
}
