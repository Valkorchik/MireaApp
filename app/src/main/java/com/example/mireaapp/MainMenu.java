package com.example.mireaapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
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
import java.util.Map;
import java.util.Random;

public class MainMenu extends AppCompatActivity {
    private static final String EDIT_TEXT_VISIBLE = "edit_text_visible";
    private boolean mEditTextVisible;
    BottomNavigationView bottomNavigationView;
    Button addButton;
    Button moneyButton;
    FrameLayout toggler;
    TextView cardNumber1;
    TextView pin1;
    TextView cardNumber2;
    TextView pin2;
    private String cardNumber=" ";
    private String pinNumber ="";
    FirebaseUser user;
    FirebaseFirestore mData=FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_screen);

        cardNumber1=findViewById(R.id.cardNumber1);
        pin1=findViewById(R.id.pinCard1);
        cardNumber2=findViewById(R.id.cardNumber2);
        pin2=findViewById(R.id.pinCard2);
        cardInfoGenerator(cardNumber1,pin1,"1");
        bottomNavigationView= findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnItemSelectedListener(item -> {
           switch (item.getItemId()){
               case R.id.home:
                   Toast.makeText(this, "You are already on that screen", Toast.LENGTH_SHORT).show();
                   break;
               case R.id.convert:
                   Intent intentConvert=new Intent(this,CurrencyTransfer.class);
                   startActivity(intentConvert);
                   finish();
                   break;
               case R.id.settings:
                  Intent intentSettings=new Intent(this,Settings.class);
                    startActivity(intentSettings);
                   finish();
                   break;


           }
            if (savedInstanceState != null) {
                mEditTextVisible = savedInstanceState.getBoolean(EDIT_TEXT_VISIBLE);
                toggler.setVisibility(mEditTextVisible ? View.VISIBLE : View.INVISIBLE);
                addButton.setVisibility(mEditTextVisible ? View.VISIBLE : View.INVISIBLE);
            }
           return true;
       });
        addButton=findViewById(R.id.addCardButton);
        toggler=findViewById(R.id.second_card);
        addButton.setOnClickListener(view->{
            toggler.setVisibility(View.VISIBLE);
            addButton.setVisibility(View.INVISIBLE);
            cardInfoGenerator(cardNumber2,pin2,"2");
        });
        moneyButton=findViewById(R.id.addMoneyButton);
        moneyButton.setOnClickListener(view -> {
            Intent moneyIntent=new Intent(this,Money.class);
            startActivity(moneyIntent);
        });
    }

    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(EDIT_TEXT_VISIBLE, mEditTextVisible);
    }
    private void cardInfoGenerator(TextView card, TextView pin, String number)
    {
        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        if (user!=null)
        {
            DocumentReference docRef = FirebaseFirestore.getInstance().collection("users").document(user.getUid());
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            String cardS = "card" + number;
                            String pinS="pin"+number;
                            if(document.getData().get(cardS) == "")
                            {
                                Random rand = new Random();
                                for(int i=1;i<17;i++)
                                {
                                    cardNumber+= String.valueOf(rand.nextInt(8)+1);
                                    if(i%4==0)
                                    {
                                        cardNumber+=" \t";
                                    }
                                }
                                for( int i=0;i<4;i++)
                                {
                                    pinNumber +=String.valueOf(rand.nextInt(10));
                                }

                                Map<String,Object> data= new HashMap<>();

                                data.put(cardS,cardNumber);
                                data.put(pinS, pinNumber);
                                pin.setText(pinNumber);
                                card.setText(cardNumber);
                                mData.collection("users").document(user.getUid().toString()).set(data, SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
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
                            }
                            else{
                                cardNumber=document.getData().get(cardS).toString();
                                pinNumber=document.getData().get(pinS).toString();
                                pin.setText(pinNumber);
                                card.setText(cardNumber);
                            }
                        } else {
                            Log.d("Document", "No such document");
                        }
                    } else {
                        Log.d("Document", "get failed with ", task.getException());
                    }
                }
            });
        }
        pin.setText(pinNumber);
        card.setText(cardNumber);
        cardNumber="";
        pinNumber="";
    }

}