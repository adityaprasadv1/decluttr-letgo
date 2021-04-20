package com.example.myshoppingstore;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class Details extends AppCompatActivity {

    //declaring button, imageview, textview, ratingbar variables for further use
    private Button btnBuynow;
    private ImageView productImage;
    private TextView productName, productPrice, productDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        //Setting data into the Details page widgets after accessing layout widgets
        productImage = findViewById(R.id.productImage);
        productName = findViewById(R.id.productName);
        productPrice = findViewById(R.id.productPrice);
        productDescription = findViewById(R.id.productDescription);

        Intent productIntent = getIntent();
        // Accessing data passed by previous activity through intent into variables for further use
        String productImageFromDB = productIntent.getStringExtra("productImage");
        String productNameFromDB = productIntent.getStringExtra("productName");
        Double productPriceFromDB = productIntent.getDoubleExtra("productPrice", 0.0);
        String productDescriptionFromDB = productIntent.getStringExtra("productDescription");

        StorageReference storeRef = FirebaseStorage.getInstance().getReferenceFromUrl(productImageFromDB);
        Glide.with(productImage.getContext()).load(storeRef).into(productImage);
        productName.setText(productNameFromDB);
        productPrice.setText("$"+productPriceFromDB);
        productDescription.setText(productDescriptionFromDB);

        btnBuynow = (Button)findViewById(R.id.btnBuynow);
        btnBuynow.setOnClickListener(v -> { //moving into checkout form when user clicks buy now button
            Intent checkoutIntent = new Intent(Details.this, activity_checkout.class);

            //passing product name, price which came from DB to checkout form to show the purchase details
            checkoutIntent.putExtra("productName",productNameFromDB);
            checkoutIntent.putExtra("productPrice",productPriceFromDB);
            startActivity(checkoutIntent);
        });
    }
}