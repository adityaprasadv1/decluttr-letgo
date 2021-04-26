package com.example.myshoppingstore;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class Details extends AppCompatActivity {

    //declaring button, imageview, textview, ratingbar variables for further use
    private Button btnBuynow;
    private ImageView productImage;
    private TextView productName, productPrice, productDescription;
    RatingBar productCondtn;
    Button btnCreateAd, btnProducts,signoutBtn;
    FirebaseAuth mAuth;
    AlertDialog.Builder builder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        //Setting data into the Details page widgets after accessing layout widgets
        productImage = findViewById(R.id.productImage);
        productName = findViewById(R.id.productName);
        productPrice = findViewById(R.id.productPrice);
        productCondtn = findViewById(R.id.productCondition);
        productDescription = findViewById(R.id.productDescription);

        Intent productIntent = getIntent();
        // Accessing data passed by previous activity through intent into variables for further use
        String productImageFromDB = productIntent.getStringExtra("productImage");
        String productNameFromDB = productIntent.getStringExtra("productName");
        Double productPriceFromDB = productIntent.getDoubleExtra("productPrice", 0.0);
        String productDescriptionFromDB = productIntent.getStringExtra("productDescription");
        double productConditionFromDB = productIntent.getDoubleExtra("productCondition", 0.0);
//        double d = model.getCondition();
        float rating = (float)productConditionFromDB;

        StorageReference storeRef = FirebaseStorage.getInstance().getReferenceFromUrl(productImageFromDB);
        Glide.with(productImage.getContext()).load(storeRef).into(productImage);
        productName.setText(productNameFromDB);
        productPrice.setText("$"+productPriceFromDB);
        productDescription.setText(productDescriptionFromDB);
        productCondtn.setRating(rating);

        btnBuynow = (Button)findViewById(R.id.btnBuynow);
        btnBuynow.setOnClickListener(v -> { //moving into checkout form when user clicks buy now button
            Intent checkoutIntent = new Intent(Details.this, Checkout.class);

            //passing product name, price which came from DB to checkout form to show the purchase details
            checkoutIntent.putExtra("productName",productNameFromDB);
            checkoutIntent.putExtra("productPrice",productPriceFromDB);
            startActivity(checkoutIntent);
        });

        btnCreateAd = findViewById(R.id.btnCreateAd);
        signoutBtn = findViewById(R.id.signoutButton1);
        mAuth = FirebaseAuth.getInstance();

        builder = new AlertDialog.Builder(this);
        btnCreateAd.setOnClickListener(v -> {
            Intent createIntent = new Intent(v.getContext(),CreateAd.class);
            startActivity(createIntent);
        });

        signoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.setTitle( "Logout" )
                        .setMessage("Do you want to logout?")
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialoginterface, int i) {
                                dialoginterface.cancel();
                            }})
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialoginterface, int i) {

                                dialoginterface.dismiss();
                                mAuth = FirebaseAuth.getInstance();
                                mAuth.signOut();
                                Intent intent = new Intent(getApplicationContext(), Login.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                                        | Intent.FLAG_ACTIVITY_CLEAR_TOP
                                        | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                finish();
                            }
                        }).show();
            }
        });
    }
}