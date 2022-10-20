package com.example.mireaapp;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ChangePassword extends AppCompatActivity {
    Button button;
    EditText newPassword;
    EditText newPasswordVerify;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_password_screen);
        newPassword = findViewById(R.id.editTextNewPassword);
        newPasswordVerify = findViewById(R.id.editTextNewPasswordVerify);
        newPassword.setError("Password should be at least 6 characters");
        newPasswordVerify.setError("Password should be at least 6 characters");

        button = findViewById(R.id.changePassword);
        button.setOnClickListener(view -> {
            if (validatePassword(newPassword.getText().toString().trim(), newPasswordVerify.getText().toString().trim())) {
                user.updatePassword(newPassword.getText().toString().trim())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Log.d("User", "User password updated.");
                                }
                            }
                        });
                finish();
            }


        });

    }

    public boolean validatePassword(String newPassword,String newPasswordVerify) {
        if (newPassword.length() < 6 | newPasswordVerify.length() < 6) {
            Toast.makeText(this, "Passwords should be at least 6 characters", Toast.LENGTH_SHORT).show();
            return false;
        }
        if ( newPassword.isEmpty() | newPasswordVerify.isEmpty())
        {
            Toast.makeText(this, "Password is empty", Toast.LENGTH_SHORT).show();
            return false;
        }
        return newPassword.equals(newPasswordVerify) & (newPasswordVerify.length() >= 6);
    }
}