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
        btnContinueShopping.setOnClickListener(v -> { //moving to home page when the user clicks on "Need more coffee?" button
            Intent continueIntent = new Intent(Confirmation.this, Details.class);
            startActivity(continueIntent);
            finish(); //stopping user from going back to confirmation page after clicking "Need more coffee?" button by closing/finishing this activity
        });
    }
}