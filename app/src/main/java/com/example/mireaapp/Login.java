package com.example.mireaapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);
        //TODO: БД для хранения данных
        Button login = findViewById(R.id.loginButton);
        login.setOnClickListener(view->{
            Log.i("Info", "Log in button tapped");
            Intent intent=new Intent(this,MainMenu.class);
            startActivity(intent);
        });
        Button signup=findViewById(R.id.signupButton);
        signup.setOnClickListener(view -> {
            Log.i("Info", "Sign up button tapped");
            Intent intent=new Intent(this,Registration.class);
            startActivity(intent);
        });

    }
}