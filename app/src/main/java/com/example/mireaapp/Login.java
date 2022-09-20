package com.example.mireaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);
        Button login = findViewById(R.id.loginButton);
        login.setOnClickListener(view->{
            Log.i("Info", "Log in button tapped");
            //TODO: Do navigation to main menu screen
        });
        Button signup=findViewById(R.id.signupButton);
        signup.setOnClickListener(view -> {
            Log.i("Info", "Sign up button tapped");
            //TODO: Add navigation to Sign Up screen
        });

    }
}