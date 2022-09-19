package com.example.mireaapp;

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
            //TODO: Add navigation to Log In screen
        });


        Button signup = findViewById(R.id.signupButton);
        signup.setOnClickListener(view -> {
            Log.i("Info", "Sign up button tapped");
            //TODO: Add navigation to Sign Up screen
        });
    }
}
