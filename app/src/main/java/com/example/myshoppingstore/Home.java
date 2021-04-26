package com.example.myshoppingstore;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;

public class Home extends AppCompatActivity {

    String url1 = "https://res.cloudinary.com/rajith/image/upload/v1619300967/banner3_ujinsp.jpg";
    String url2 = "https://res.cloudinary.com/rajith/image/upload/v1619303634/banner1_xnipmz.jpg";
    String url3 = "https://res.cloudinary.com/rajith/image/upload/v1619301896/banner2_sc7trj.jpg";
    Button btnCreateAd, btnProducts,signoutBtn;
    FirebaseAuth mAuth;
    AlertDialog.Builder builder;
    ImageView cardImage1, cardImage2, cardImage3, cardImage4;
    TextView textView1, textView2, textView3, textView4;
    DatabaseReference ref1, ref2, ref3, ref4;
    private CardView cardView1, cardView2, cardView3, cardView4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        btnCreateAd = findViewById(R.id.btnCreateAd);
        btnProducts = findViewById(R.id.btnProducts);
        signoutBtn = findViewById(R.id.signoutButton1);
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


//       Card images from firebase
        cardImage1= findViewById(R.id.cardImg1);
        cardView1 = findViewById(R.id.card1);
        cardImage2 = findViewById(R.id.cardImg2);
        cardView2 = findViewById(R.id.card2);
        cardImage3 = findViewById(R.id.cardImg3);
        cardView3 = findViewById(R.id.card3);
        cardImage4 = findViewById(R.id.cardImg4);
        cardView4 = findViewById(R.id.card4);

        ref1 = FirebaseDatabase.getInstance().getReference().child("Products").child("-MZ5HhbfeoHTRgF6sFZu");
        ref1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String nameText = snapshot.child("name").getValue().toString();
                String price = snapshot.child("price").getValue().toString();
                double dPrice = Double.parseDouble(price);
                String description = snapshot.child("description").getValue().toString();
                String condition = snapshot.child("condition").getValue().toString();
                double dCondition = Double.parseDouble(condition);
//                storing image url to a string
                String image = snapshot.child("image").getValue().toString();
                StorageReference storeRef = FirebaseStorage.getInstance().getReferenceFromUrl(image);

                Glide.with(cardImage1.getContext())
                        .load(storeRef)
                        .into(cardImage1);

                cardView1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(v.getContext(), Details.class);
                        i.putExtra("productName",nameText );
                        i.putExtra("productDescription",description );
                        i.putExtra("productImage",image );
                        i.putExtra("productPrice",dPrice );
                        i.putExtra("productCondition",dCondition );
                        i.putExtra("productId","-MZ5HhbfeoHTRgF6sFZu" );
                        v.getContext().startActivity(i);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        ref2 = FirebaseDatabase.getInstance().getReference().child("Products").child("-MZ5bQDVP0KiRiFHZ8_c");
        ref2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String nameText = snapshot.child("name").getValue().toString();
                String price = snapshot.child("price").getValue().toString();
                double dPrice = Double.parseDouble(price);
                String description = snapshot.child("description").getValue().toString();
                String condition = snapshot.child("condition").getValue().toString();
                double dCondition = Double.parseDouble(condition);
//                storing image url to a string
                String image = snapshot.child("image").getValue().toString();
                StorageReference storeRef = FirebaseStorage.getInstance().getReferenceFromUrl(image);

                Glide.with(cardImage2.getContext())
                        .load(storeRef)
                        .into(cardImage2);

                cardView2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(v.getContext(), Details.class);
                        i.putExtra("productName",nameText );
                        i.putExtra("productDescription",description );
                        i.putExtra("productImage",image );
                        i.putExtra("productPrice",dPrice );
                        i.putExtra("productCondition",dCondition );
                        i.putExtra("productId","-MZ5bQDVP0KiRiFHZ8_c" );
                        v.getContext().startActivity(i);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        ref3 = FirebaseDatabase.getInstance().getReference().child("Products").child("-MZ5fcABkP9n3plRPEh6");
        ref3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String nameText = snapshot.child("name").getValue().toString();
                String price = snapshot.child("price").getValue().toString();
                double dPrice = Double.parseDouble(price);
                String description = snapshot.child("description").getValue().toString();
                String condition = snapshot.child("condition").getValue().toString();
                double dCondition = Double.parseDouble(condition);
//                storing image url to a string
                String image = snapshot.child("image").getValue().toString();
                StorageReference storeRef = FirebaseStorage.getInstance().getReferenceFromUrl(image);

                Glide.with(cardImage3.getContext())
                        .load(storeRef)
                        .into(cardImage3);

                cardView3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(v.getContext(), Details.class);
                        i.putExtra("productName",nameText );
                        i.putExtra("productDescription",description );
                        i.putExtra("productImage",image );
                        i.putExtra("productPrice",dPrice );
                        i.putExtra("productCondition",dCondition );
                        i.putExtra("productId","-MZ5fcABkP9n3plRPEh6" );
                        v.getContext().startActivity(i);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        ref4 = FirebaseDatabase.getInstance().getReference().child("Products").child("-MZ8KVndoPCPwyKLs5J9");
        ref4.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String nameText = snapshot.child("name").getValue().toString();
                String price = snapshot.child("price").getValue().toString();
                double dPrice = Double.parseDouble(price);
                String description = snapshot.child("description").getValue().toString();
                String condition = snapshot.child("condition").getValue().toString();
                double dCondition = Double.parseDouble(condition);
//                storing image url to a string
                String image = snapshot.child("image").getValue().toString();
                StorageReference storeRef = FirebaseStorage.getInstance().getReferenceFromUrl(image);

                Glide.with(cardImage4.getContext())
                        .load(storeRef)
                        .into(cardImage4);

                cardView4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(v.getContext(), Details.class);
                        i.putExtra("productName",nameText );
                        i.putExtra("productDescription",description );
                        i.putExtra("productImage",image );
                        i.putExtra("productPrice",dPrice );
                        i.putExtra("productCondition",dCondition );
                        i.putExtra("productId","-MZ8KVndoPCPwyKLs5J9" );
                        v.getContext().startActivity(i);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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
