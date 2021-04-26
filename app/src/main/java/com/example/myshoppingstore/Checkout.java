package com.example.myshoppingstore;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.security.acl.Group;

public class Checkout extends AppCompatActivity {
    private FirebaseUser mAuth;
    Button cancelButton, buy;
    ImageView back;
    TextView nameTextview, priceTextView;
    String selectedtext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checkout_form);

        cancelButton = findViewById(R.id.cancelButtonID);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        Intent detailsIntent = getIntent();
        String name = detailsIntent.getStringExtra("productName");
        Double price = detailsIntent.getDoubleExtra("productPrice", 0);
        Log.i("price", ""+ price);
//        String brand = i.getStringExtra("brand");
//        String image = i.getStringExtra("image");

        nameTextview =findViewById(R.id.buyMobileText);
        nameTextview.setText(name);
        priceTextView=findViewById(R.id.total);
        priceTextView.setText("Total : $"+price);

//        EditText edit = findViewById(R.id.editTextQuantity);
//
//        edit.addTextChangedListener(new TextWatcher() {
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//
//            @Override
//            public void beforeTextChanged(CharSequence s, int start,
//                                          int count, int after) {
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start,
//                                      int before, int count) {
//                if(s.length() != 0) {
//                    int number = Integer.parseInt(s.toString());
//                    if(number <= 10) {
//                        int result = (number * Integer.parseInt(price));
//                        String res = String.valueOf(result);
//                        priceTextView.setText("Total : $" + res);
//                    }else{
//                        edit.setText("10");
//                    }
//                }
//                else {
//                    priceTextView.setText("Total : $"+price);
//                }
//            }
//        });

        RadioGroup  group= findViewById(R.id.radioGroup);
        EditText cardEdittext, expiryEdittext, cvvEditText;
        cardEdittext = findViewById(R.id.cardNumber);
        expiryEdittext = findViewById(R.id.expiry);
        cvvEditText = findViewById(R.id.cvv);
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                View radioButton = radioGroup.findViewById(i);
                int index = radioGroup.indexOfChild(radioButton);
                RadioButton r = (RadioButton) radioGroup.getChildAt(index);
                selectedtext = r.getText().toString();
                if(selectedtext.equals("Cash")){
                    cardEdittext.setVisibility(View.GONE); expiryEdittext.setVisibility(View.GONE); cvvEditText.setVisibility(View.GONE);
                }else{
                    cardEdittext.setVisibility(View.VISIBLE); expiryEdittext.setVisibility(View.VISIBLE);
                    cvvEditText.setVisibility(View.VISIBLE);

                }
            }
        });

        EditText editName = findViewById(R.id.editTextName);
        EditText editMobile = findViewById(R.id.editTextMobile);
        EditText editAddress = findViewById(R.id.editAddress);

        EditText cardNum = findViewById(R.id.cardNumber);
        EditText expiry = findViewById(R.id.expiry);
        EditText cvv = findViewById(R.id.cvv);
        RadioButton cash = findViewById(R.id.cash);

        buy = findViewById(R.id.buyNow2);
        buy.setOnClickListener(v -> {

            if(editName.getText().toString().isEmpty()){
                editName.requestFocus();
                editName.setError("Please enter a name");
            }else if(editMobile.getText().toString().isEmpty()){
                editMobile.requestFocus();
                editMobile.setError("Please enter mobile number");
            }else if(editAddress.getText().toString().isEmpty()){
                editAddress.requestFocus();
                editAddress.setError("Please enter address");
            }else if(!cash.isChecked()){
                if(cardEdittext.getText().toString().isEmpty()){
                    cardEdittext.requestFocus();
                    cardEdittext.setError("Please enter card number");
                }else if(expiryEdittext.getText().toString().isEmpty()){
                    expiryEdittext.requestFocus();
                    expiryEdittext.setError("Please enter address");
                }else if(cvvEditText.getText().toString().isEmpty()){
                    cvvEditText.requestFocus();
                    cvvEditText.setError("Please enter cvv number");
                }else{
                    mAuth = FirebaseAuth.getInstance().getCurrentUser();
                    String emailId = mAuth.getEmail();
                    sendEmail("decluttrletgo@gmail.com","decluttr2021",emailId,"Successfully Purchased","Dear Valued Customer,\n\nThank you for buying "+name+" from Decluttr LetGo! You have spent $"+price+" for the purchase. Hope you loved the purchase!\n\nKeep checking our Mobile Application to buy wide range of exciting products and to sell your own great products! We will see you there soon.\n\n- Team Decluttr LetGo,\nRegion of Waterloo.");

                    Intent confirmationIntent = new Intent(v.getContext(), Confirmation.class);
                    v.getContext().startActivity(confirmationIntent);
                    finish();
                }
            }
            else{
                mAuth = FirebaseAuth.getInstance().getCurrentUser();
                  String emailId = mAuth.getEmail();
                sendEmail("decluttrletgo@gmail.com","decluttr2021",emailId,"Successfully Purchased","Dear Valued Customer,\\n\\nThank you for buying \"+name+\" from Decluttr LetGo! You have spent $\"+price+\" for the purchase. Hope you loved the purchase!\\n\\nKeep checking our Mobile Application to buy wide range of exciting products and to sell your own great products! We will see you there soon.\\n\\n- Team Decluttr LetGo,\\nRegion of Waterloo.");


                Intent confirmationIntent = new Intent(v.getContext(), Confirmation.class);
                confirmationIntent.putExtra("productName",name);
                v.getContext().startActivity(confirmationIntent);
                finish();
            }
//
        });
    }

    private void sendEmail(final String Sender,final String Password,final String Receiver,final String Title,final String Message)
    {

        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    GMailSender sender = new GMailSender(Sender,Password);
                    sender.sendMail(Title, "<b>"+Message+"</b>", Sender, Receiver);
                } catch (Exception e) {
                    Log.e("SendMail", e.getMessage(), e);
                }
            }

        }).start();
    }

}
