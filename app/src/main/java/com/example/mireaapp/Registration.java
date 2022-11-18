package com.example.mireaapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;
import java.util.Map;

public class Registration extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private final FirebaseFirestore mData = FirebaseFirestore.getInstance();
    private String email;
    private String password;
    private String name;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editTextPasswordConfirm;
    private EditText editTextName;
    private Map<String, Object> data = new HashMap<>();
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.registration_screen);
        Button signup = findViewById(R.id.signupButton);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextName = findViewById(R.id.editTextFullName);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextPasswordConfirm = findViewById(R.id.editTextPasswordConfirm);
        editTextPassword.setError("Password should be at least 6 characters");
        editTextPasswordConfirm.setError("Password should be at least 6 characters");
        progressDialog = new ProgressDialog(this);
        DocumentReference docRef = FirebaseFirestore.getInstance().collection("users").document("test");
        docRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    Log.d("Document", "DocumentSnapshot data: " + document.getData().get("email"));
                } else {
                    Log.d("Document", "No such document");
                }
            } else {
                Log.d("Document", "get failed with ", task.getException());
            }
        });

        signup.setOnClickListener(view -> {
            Log.i("Info", "Sign up button tapped");
            if (dateValidation(editTextEmail.getText().toString().trim(), editTextPassword.getText().toString().trim(), editTextPasswordConfirm.getText().toString().trim())) {
                email = editTextEmail.getText().toString().trim();
                password = editTextPassword.getText().toString().trim();
                name = editTextName.getText().toString().trim();
                data.put("email", email);
                data.put("name", name);
                data.put("card1", "");
                data.put("card2", "");
                data.put("pin1", "");
                data.put("pin2", "");
                data.put("money1", "100");
                data.put("money2", "100");
                progressDialog.setMessage("Registering Please Wait...");
                progressDialog.show();
                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        Log.d("Info", "createUserWithEmail:success");
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null) {
                            mData.collection("users").document(user.getUid()).set(data)
                                    .addOnSuccessListener(aVoid -> Log.d("Document", "DocumentSnapshot successfully written!"))
                                    .addOnFailureListener(e -> Log.w("Document", "Error writing document", e));
                            progressDialog.dismiss();
                            Intent intentLogin = new Intent(Registration.this, Login.class);
                            startActivity(intentLogin);
                            finish();
                        }

                    } else {
                        Log.w("Info", "createUserWithEmail:failure", task.getException());
                        Toast.makeText(Registration.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                });
            } else {
                Toast.makeText(Registration.this, "Check your password.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    boolean dateValidation(String email, String password, String confirmPassword) {
        if (email.isEmpty() | password.isEmpty() | confirmPassword.isEmpty()) {
            Toast.makeText(this, "Email or Password is empty", Toast.LENGTH_SHORT).show();
            return false;
        }
        return password.equals(confirmPassword) & (password.length() >= 6);
    }
}


