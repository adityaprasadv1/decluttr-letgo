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
    private RatingBar productRating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        //Setting data into the Details page widgets after accessing layout widgets
        productImage = findViewById(R.id.productImage);
        productName = findViewById(R.id.productName);
        productPrice = findViewById(R.id.productPrice);
        productRating = findViewById(R.id.productRating);
        productDescription = findViewById(R.id.productDescription);

        StorageReference storeRef = FirebaseStorage.getInstance().getReferenceFromUrl(productImageFromDB);
        Glide.with(productImage.getContext()).load(storeRef).into(productImage);
        productName.setText(productNameFromDB);
        productPrice.setText("$"+productPriceFromDB);
        productRating.setRating(productRatingFromDB);
        productDescription.setText(productDescriptionFromDB);
        btnBuynow = (Button)findViewById(R.id.btnBuynow);
        btnBuynow.setOnClickListener(v -> { //moving into checkout form when user clicks buy now button
            Intent displayIntent = new Intent(activity_display.this, activity_checkout.class);

            //passing product name, price which came from DB to checkout form to show the purchase details
            displayIntent.putExtra("productName",productNameFromDB);
            displayIntent.putExtra("productPrice",productPriceFromDB);
            startActivity(displayIntent);
        });
    }
}