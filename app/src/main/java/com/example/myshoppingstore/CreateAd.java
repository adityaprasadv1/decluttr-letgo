package com.example.myshoppingstore;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.ByteArrayOutputStream;
import java.util.UUID;

public class CreateAd extends AppCompatActivity {

    AlertDialog.Builder builder;
    EditText txtName;
    EditText txtDescription;
    EditText txtPrice;
    Button btnCam;
    Button btnCreate;
    StorageReference storageRef;
    String name;
    String description;
    double price;
    double condition;
    String stringPrice, stringCondition;
    Button  signoutBtn;
    FirebaseAuth mAuth;
    Spinner spin;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode ==1 && resultCode == RESULT_OK){
            Log.i("cam","test");
            Bundle extras = data.getExtras();
            String uniqueID = UUID.randomUUID().toString();
            Bitmap imgBitmap = (Bitmap) extras.get("data");
            ImageView takeImage = findViewById(R.id.takePhoto);
            takeImage.setImageBitmap(imgBitmap);
            final StorageReference imgRef = storageRef.child(uniqueID);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            imgBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            //Log.i("test","test");

            byte[] imgData = baos.toByteArray();
            imgRef.putBytes(imgData);
            CreateForm i = new CreateForm();
            i.setImage("gs://my-shopping-store-auth.appspot.com/"+imgRef.getName());

       
            name = txtName.getText().toString();
            description = txtDescription.getText().toString();
            stringPrice = txtPrice.getText().toString();
            stringCondition = spin.getSelectedItem().toString();


            price = Double.valueOf(stringPrice);
            double con = Double.parseDouble(stringCondition);


//            Log.i("name",name);
            i.setName(name);
            i.setDescription(description);
            i.setPrice(price);
            i.setCondition(con);
            Log.i("stringCondition", "" + con);
            btnCreate.setOnClickListener(c -> {
                if(txtName.getText().toString().isEmpty()){
                    txtName.setError("Please enter Product Name");
                }
                else if(txtDescription.getText().toString().isEmpty()){
                    txtDescription.setError("Please enter Product Description");
                }
                else if(txtPrice.getText().toString().isEmpty()){
                    txtPrice.setError("Please enter Product Price");
                }
                else {
//                    FirebaseDatabase.getInstance().getReference().child("Products").push().setValue(i);
                    //Toast.makeText(CreateAd.this, "Ad Created", Toast.LENGTH_LONG).show();
                    dialog(i);
                }


            });
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_ad);

        //query = FirebaseDatabase.getInstance().getReference().child("Products");
        storageRef = FirebaseStorage.getInstance().getReference();

        btnCam = findViewById(R.id.btnCam);
        txtName = findViewById(R.id.txtName);
        txtDescription=findViewById(R.id.txtDescription);
        txtPrice=findViewById(R.id.txtPrice);
        spin=findViewById(R.id.spinner1);
        btnCreate = findViewById(R.id.btnCreate);
        builder = new AlertDialog.Builder(this);

        btnCam.setOnClickListener(v ->{

            if(ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                if(txtName.getText().toString().isEmpty()){
                    txtName.setError("Please enter Product Name");
                }
                else if(txtDescription.getText().toString().isEmpty()){
                    txtDescription.setError("Please enter Product Description");
                }
                else if(txtPrice.getText().toString().isEmpty()){
                    txtPrice.setError("Please enter Product Price");
                }
                else {
                    Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(i, 1);
                }
            }

            else if(ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
                Log.i("permission","permission");
                ActivityCompat.requestPermissions(this,new String[]{
                        Manifest.permission.CAMERA
                },200);
            }
        });

        signoutBtn = findViewById(R.id.signoutButton1);
        mAuth = FirebaseAuth.getInstance();
        builder = new AlertDialog.Builder(this);


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

        //get the spinner from the xml.
        Spinner dropdown = findViewById(R.id.spinner1);
//create a list of items for the spinner.
        Double[] items = new Double[]{0.5,1.0,1.5,2.0, 2.5,3.0,3.5,4.0,4.5,5.0 };
//create an adapter to describe how the items are displayed, adapters are used in several places in android.
//There are multiple variations of this, but this is the basic variant.
        ArrayAdapter<Double> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
//set the spinners adapter to the previously created one.
        dropdown.setAdapter(adapter);
    }

    protected void dialog(CreateForm i){
        Log.i("CreateForm", "" + i);

        //Setting message manually and performing action on button click
        builder.setMessage("Continue to create an Ad?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        FirebaseDatabase.getInstance().getReference().child("Products").push().setValue(i);
                        Intent productIntent = new Intent(CreateAd.this,Products.class);
                        productIntent.putExtra("AD","Product Created Successfully");
                        startActivity(productIntent);
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //  Action for 'NO' Button
                        dialog.cancel();
                    }
                });
        builder.show();
    }

}