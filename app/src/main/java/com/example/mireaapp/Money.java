package com.example.mireaapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Money extends AppCompatActivity {
    Button backButton;
    Button addMoneyCard1;
    Button addMoneyCard2;
    EditText editTextCard1;
    EditText editTextCard2;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    BottomNavigationView bottomNavigationView;
    FirebaseFirestore mData=FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.money_screen);
        editTextCard1=findViewById(R.id.editTextCard1);
        editTextCard2=findViewById(R.id.editTextCard2);
        addMoneyCard1=findViewById(R.id.addMoneyCard1);
        addMoneyCard2=findViewById(R.id.addMoneyCard2);



        backButton=findViewById(R.id.backButton);
        backButton.setOnClickListener(view -> {
            Intent intent=new Intent(this,MainMenu.class);
            startActivity(intent);
            finish();
        });
        addMoneyCard1.setOnClickListener(view -> {
            addMoney(editTextCard1.getText().toString().trim(),"1");
        });
        addMoneyCard2.setOnClickListener(view -> {
            addMoney(editTextCard2.getText().toString().trim(),"2");
        });
        bottomNavigationView= findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.home:
                    Intent intentHome = new Intent(this, CurrencyTransfer.class);
                    startActivity(intentHome);
                    finish();
                    break;
                case R.id.convert:
                    Intent intentConvert = new Intent(this, CurrencyTransfer.class);
                    startActivity(intentConvert);
                    finish();
                    break;
                case R.id.settings:
                    Intent intentSettings = new Intent(this, Settings.class);
                    startActivity(intentSettings);
                    finish();
                    break;


            }
            return true;
        });
    }
    public void addMoney(String editTextCard, String num)
    {
        DocumentReference docRef = FirebaseFirestore.getInstance().collection("users").document(user.getUid());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        int oldMoney =Integer.parseInt(document.getData().get("money"+num).toString());
                        String newMoney=String.valueOf(oldMoney+Integer.parseInt(editTextCard));
                        Map<String,Object> data =new HashMap<>();
                        data.put("money"+num,newMoney);
                        mData.collection("users").document(user.getUid().toString()).set(data, SetOptions.merge())
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d("Document", "DocumentSnapshot successfully written!");
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.w("Document", "Error writing document", e);
                                    }
                                });
                    } else {
                        Log.d("Document", "No such document");
                    }
                } else {
                    Log.d("Document", "get failed with ", task.getException());
                }

            }
        });
    }
}