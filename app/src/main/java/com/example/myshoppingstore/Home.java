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

    String url1 = "https://www.geeksforgeeks.org/wp-content/uploads/gfg_200X200-1.png";
    String url2 = "https://qphs.fs.quoracdn.net/main-qimg-8e203d34a6a56345f86f1a92570557ba.webp";
    String url3 = "https://bizzbucket.co/wp-content/uploads/2020/08/Life-in-The-Metro-Blog-Title-22.png";
    Button btnCreateAd, btnProducts,signoutBtn;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        btnCreateAd = findViewById(R.id.btnCreateAd);
        btnProducts = findViewById(R.id.btnProducts);
        signoutBtn = findViewById(R.id.signoutButton);
        mAuth = FirebaseAuth.getInstance();

        AlertDialog.Builder builder;
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
                builder.setTitle( "Signout" )
                        .setMessage("Do you want to signout?")
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
