package com.example.mireaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;

public class ChangePassword extends AppCompatActivity {
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_password_screen);
        button=findViewById(R.id.changePassword);
        button.setOnClickListener(view -> {
            //TODO: Сделать сохранение пароля в БД и круг
            finish();
        });

    }
}