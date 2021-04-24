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
import android.widget.Button;
import android.widget.EditText;

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
    String stringPrice;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode ==1 && resultCode == RESULT_OK){
            Log.i("cam","test");
            Bundle extras = data.getExtras();
            String uniqueID = UUID.randomUUID().toString();
            Bitmap imgBitmap = (Bitmap) extras.get("data");
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
            price = Double.valueOf(stringPrice);
            Log.i("name",name);
            i.setName(name);
            i.setDescription(description);
            i.setPrice(price);
            btnCreate.setOnClickListener(c -> {
                FirebaseDatabase.getInstance().getReference().child("Products").push().setValue(i);
                //Toast.makeText(CreateAd.this, "Ad Created", Toast.LENGTH_LONG).show();
                dialog();
//                Intent productIntent = new Intent(CreateAd.this,Products.class);
//                startActivity(productIntent);
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
        btnCreate = findViewById(R.id.btnCreate);

        btnCam.setOnClickListener(v ->{

            if(ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(i, 1);
            }

            else if(ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
                Log.i("permission","permission");
                ActivityCompat.requestPermissions(this,new String[]{
                        Manifest.permission.CAMERA
                },200);
            }
        });
    }

    protected void dialog(){

        //Setting message manually and performing action on button click
        builder.setMessage("Continue to Products page?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                        Intent productIntent = new Intent(CreateAd.this,Products.class);
                        startActivity(productIntent);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //  Action for 'NO' Button
                        dialog.cancel();
                        Intent homeIntent = new Intent(CreateAd.this, Home.class);
                        startActivity(homeIntent);
                    }
                });
        //Creating dialog box
        AlertDialog alert = builder.create();
        //Setting the title manually
        alert.setTitle("Ad created successfully!");
        alert.show();
    }
}