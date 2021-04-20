package com.example.myshoppingstore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Handler().postDelayed(new Runnable() { //using predefined postDelayed method to keep activity waiting for 3 seconds before doing any operation
            @Override
            public void run() { //moving into home page when 3 seconds delay time is reached
                Intent splashIntent = new Intent(MainActivity.this, Login.class);
                startActivity(splashIntent);
                finish(); //stopping app from going back to the splash screen by closing/finishing this activity
            }
        }, 3000); //time set to 3 seconds
    }
}