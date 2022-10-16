package com.example.mireaapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private String email;
    private String password;
    EditText editTextEmail;
    EditText editTextPassword;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);
        mAuth=FirebaseAuth.getInstance();
        Button login = findViewById(R.id.loginButton);
        editTextEmail=findViewById(R.id.editTextEmail);
        editTextPassword=findViewById(R.id.editTextPassword);
        editTextPassword.setError("Password should be at least 6 characters");
        progressDialog = new ProgressDialog(this);
        login.setOnClickListener(view->{
            Log.i("Info", "Log in button tapped");
            if (dateValidation(editTextEmail.getText().toString().trim(),editTextPassword.getText().toString().trim())) {
                email = editTextEmail.getText().toString().trim();
                password = editTextPassword.getText().toString().trim();

                progressDialog.setMessage("Logging In Please Wait...");
                progressDialog.show();
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Log.d("Info", "signInWithEmail:success");
                                    progressDialog.dismiss();
                                    Intent intent = new Intent(Login.this, MainMenu.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Log.w("Info", "signInWithEmail:failure", task.getException());
                                    Toast.makeText(Login.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();

                                }
                            }
                        });
            }

        });
        Button signup=findViewById(R.id.signupButton);
        signup.setOnClickListener(view -> {
            Log.i("Info", "Sign up button tapped");
            Intent intent=new Intent(this,Registration.class);
            startActivity(intent);
            finish();
        });

    }
    boolean dateValidation(String email, String password) {
        if (email.isEmpty() | password.isEmpty())
        {
            Toast.makeText(this, "Email or Password is empty", Toast.LENGTH_SHORT).show();
            return false;
        }
        return (password.length() >= 6);
    }
}
