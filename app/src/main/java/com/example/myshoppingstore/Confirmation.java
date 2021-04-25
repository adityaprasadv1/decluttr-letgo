package com.example.myshoppingstore;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Confirmation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);

        //getting intent from previous activity to collect data passed from it
        Intent confirmationIntent = getIntent();
        String productNameFromDB = confirmationIntent.getStringExtra("productName");

        TextView txtThanksProductName;
        txtThanksProductName = findViewById(R.id.txtThanksProductName);

        txtThanksProductName.setText(productNameFromDB);

        Button btnContinueShopping;
        btnContinueShopping = findViewById(R.id.btnContinueShopping);
        btnContinueShopping.setOnClickListener(v-> {
            Intent launchNextActivity =new Intent(v.getContext(), Home.class);
            launchNextActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            launchNextActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            launchNextActivity.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(launchNextActivity);
        });
    }
}