package com.example.mireaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class Main extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen);
    }
    public void logIn(View view){
        Button login= findViewById(R.id.loginButton);
        Log.i("Info","Log in button tapped");
        //TODO: Add navigation to Log In screen
    }
    public void signUp(View view){
        Button signup= findViewById(R.id.signupButton);
        Log.i("Info","Sign up button tapped");
        //TODO: Add navigation to Sign Up screen
    }
}
