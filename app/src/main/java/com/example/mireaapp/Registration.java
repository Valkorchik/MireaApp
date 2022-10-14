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
import com.google.firebase.auth.FirebaseUser;

public class Registration extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private String email;
    private String password;
    EditText editTextEmail;
    EditText editTextPassword;
    EditText editTextPasswordConfirm;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.registration_screen);
        Button signup=findViewById(R.id.signupButton);
        editTextEmail=findViewById(R.id.editTextEmail);
        editTextPassword=findViewById(R.id.editTextPassword);
        editTextPasswordConfirm=findViewById(R.id.editTextPasswordConfirm);
        editTextPassword.setError("Password should be at least 6 characters");
        editTextPasswordConfirm.setError("Password should be at least 6 characters");
        progressDialog = new ProgressDialog(this);

        signup.setOnClickListener(view -> {
            Log.i("Info", "Sign up button tapped");
            if (editTextPassword.getText().toString().equals(editTextPasswordConfirm.getText().toString()) & (editTextPassword.getText().toString().length() >= 6)) {

                email = editTextEmail.getText().toString().trim();
                password = editTextPassword.getText().toString().trim();
                progressDialog.setMessage("Registering Please Wait...");
                progressDialog.show();
                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d("Info", "createUserWithEmail:success");
                            progressDialog.dismiss();
                            Intent intent = new Intent(Registration.this, Login.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Log.w("Info", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(Registration.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }
                });
            }
            else {
                Toast.makeText(Registration.this, "Check your password.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}


