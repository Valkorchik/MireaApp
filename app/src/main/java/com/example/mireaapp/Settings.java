package com.example.mireaapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Settings extends AppCompatActivity {
    private TextView money;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_screen);
        money = findViewById(R.id.totalMoney);
        moneyView();
        nameView();
        Button buttonChangePassword = findViewById(R.id.changePasswordButton);
        Button logOut = findViewById(R.id.LogOutButton);
        logOut.setOnClickListener(view -> {
            FirebaseAuth.getInstance().signOut();
            Intent intentLogOut = new Intent(this, Main.class);
            startActivity(intentLogOut);
            finish();
        });

        buttonChangePassword.setOnClickListener(view -> {
            Intent intent = new Intent(this, ChangePassword.class);
            startActivity(intent);
        });
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.home:

                    Intent intentHome = new Intent(this, MainMenu.class);
                    startActivity(intentHome);
                    finish();
                    break;
                case R.id.convert:
                    Intent intentConvert = new Intent(this, CurrencyTransfer.class);
                    startActivity(intentConvert);
                    finish();

                    break;
                case R.id.settings:
                    Toast.makeText(this, "You are already on that screen", Toast.LENGTH_SHORT).show();
                    break;


            }
            return true;
        });
    }

    public void moneyView() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;
        DocumentReference docRef = FirebaseFirestore.getInstance().collection("users").document(user.getUid());
        docRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    int money1 = Integer.parseInt(document.getData().get("money1").toString());
                    int money2 = Integer.parseInt(document.getData().get("money2").toString());
                    money.setText(money1 + money2 + "$");
                } else {
                    Log.d("Document", "No such document");
                }
            } else {
                Log.d("Document", "get failed with ", task.getException());
            }

        });
    }

    public void nameView() {
        TextView name;
        TextView surname;
        name = findViewById(R.id.name);
        surname = findViewById(R.id.surname);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DocumentReference docRef = FirebaseFirestore.getInstance().collection("users").document(user.getUid());
        docRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    String nameD = document.getData().get("name").toString();
                    String[] parts = nameD.split(" ");
                    name.setText(parts[0]);
                    surname.setText(parts[1]);
                } else {
                    Log.d("Document", "No such document");
                }
            } else {
                Log.d("Document", "get failed with ", task.getException());
            }

        })
        ;
    }
}
