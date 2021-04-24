package com.example.myshoppingstore;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;

public class Home extends AppCompatActivity {

    String url1 = "https://res.cloudinary.com/rajith/image/upload/v1619300967/banner3_ujinsp.jpg";
    String url2 = "https://res.cloudinary.com/rajith/image/upload/v1619300967/banner1_vzuxo6.jpg";
    String url3 = "https://res.cloudinary.com/rajith/image/upload/v1619301896/banner2_sc7trj.jpg";
    Button btnCreateAd, btnProducts,signoutBtn;
    FirebaseAuth mAuth;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        btnCreateAd = findViewById(R.id.btnCreateAd);
        btnProducts = findViewById(R.id.btnProducts);
        signoutBtn = findViewById(R.id.signoutButton);
        mAuth = FirebaseAuth.getInstance();


        builder = new AlertDialog.Builder(this);

        // we are creating array list for storing our image urls.
        ArrayList<SliderData> sliderDataArrayList = new ArrayList<>();

        // initializing the slider view.
        SliderView sliderView = findViewById(R.id.slider);

        // adding the urls inside array list
        sliderDataArrayList.add(new SliderData(url1));
        sliderDataArrayList.add(new SliderData(url2));
        sliderDataArrayList.add(new SliderData(url3));

        // passing this array list inside our adapter class.
        SliderAdapter adapter = new SliderAdapter(this, sliderDataArrayList);

        // below method is used to set auto cycle direction in left to right direction you can change according to requirement.
        sliderView.setAutoCycleDirection(SliderView.LAYOUT_DIRECTION_LTR);

        // below method is used to setadapter to sliderview.
        sliderView.setSliderAdapter(adapter);

        // below method is use to set scroll time in seconds.
        sliderView.setScrollTimeInSec(3);

        // to set it scrollable automatically we use below method.
        sliderView.setAutoCycle(true);

        // to start autocycle below method is used.
        sliderView.startAutoCycle();

        //if user clicks on slider it should go to products/details page
        sliderView.setOnClickListener(v -> {
            Intent myIntent = new Intent(v.getContext(),Products.class);
            startActivity(myIntent);
        });

        btnCreateAd.setOnClickListener(v -> {
            Intent createIntent = new Intent(v.getContext(),CreateAd.class);
            startActivity(createIntent);
        });

        btnProducts.setOnClickListener(v -> {
            Intent productsIntent = new Intent(v.getContext(),Products.class);
            startActivity(productsIntent);
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

    @Override
    public void onBackPressed() {
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

}
