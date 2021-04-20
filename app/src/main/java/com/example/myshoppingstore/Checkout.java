package com.example.myshoppingstore;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;

public class Checkout extends AppCompatActivity {

    //Declaring button, textview, edittext, radio button variables for further use
    TextView productName, totalAmount, txtQuantityMessage;
    EditText txtFirstName, txtLastName, txtPhone, txtAddress, txtQuantity, txtCardNumber, txtExpiry, txtCVV;
    RadioButton radioCredit, radioDebit, radioCash;
    Button btnProceed, btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); //starting from where it stopped
        setContentView(R.layout.checkout_form); //setting appropriate layout when the activity is loaded
        getSupportActionBar().hide(); //hiding the top action bar in the app

        //getting intent from previous activity to collect data passed from it
        Intent checkoutIntent = getIntent();
        Double productPriceFromDB = checkoutIntent.getDoubleExtra("productPrice", 0.0);
        String productNameFromDB = checkoutIntent.getStringExtra("productName");

        //assigning layout widget IDs to the variables for easy access and further use
        productName =findViewById(R.id.checkoutProductName);
        productName.setText(productNameFromDB);

        txtFirstName = findViewById(R.id.txtFirstName);
        txtLastName = findViewById(R.id.txtLastName);
        txtPhone = findViewById(R.id.txtPhone);
        txtAddress = findViewById(R.id.txtAddress);
        txtQuantity = findViewById(R.id.txtQuantity);
        txtQuantityMessage = findViewById(R.id.txtQuantityMessage);
        totalAmount = findViewById(R.id.totalAmount);
        txtCardNumber = findViewById(R.id.txtCardNumber);
        txtExpiry = findViewById(R.id.txtExpiry);
        txtCVV = findViewById(R.id.txtCVV);
        radioCredit = findViewById(R.id.radioCredit);
        radioDebit = findViewById(R.id.radioDebit);
        radioCash = findViewById(R.id.radioCash);
        btnProceed = findViewById(R.id.btnProceed);
        btnCancel = findViewById(R.id.btnCancel);

        radioDebit.setOnClickListener(credit ->{ //making card details' text boxes visible when user selects credit card option
            txtQuantityMessage.setText("");
            txtCardNumber.setVisibility(View.VISIBLE);
            txtCardNumber.setText(""); //making sure the card number be empty when user clicks on credit card radio button
            txtCVV.setVisibility(View.VISIBLE);
            txtCVV.setText(""); //making sure the cvv be empty when user clicks on credit card radio button
            txtExpiry.setVisibility(View.VISIBLE);
            txtExpiry.setText(""); //making sure the expiry be empty when user clicks on credit card radio button
        });
        radioCredit.setOnClickListener(debit -> { //making card details' text boxes visible when user selects debit card option
            txtQuantityMessage.setText("");
            txtCardNumber.setVisibility(View.VISIBLE);
            txtCardNumber.setText(""); //making sure the card number be empty when user clicks on debit card radio button
            txtCVV.setVisibility(View.VISIBLE);
            txtCVV.setText(""); //making sure the cvv be empty when user clicks on debit card radio button
            txtExpiry.setVisibility(View.VISIBLE);
            txtExpiry.setText(""); //making sure the expiry be empty when user clicks on debit card radio button
        });
        radioCash.setOnClickListener(cash ->{ //Hiding all card details' text boxes when user selects cash option
            txtQuantityMessage.setText("");
            txtCardNumber.setVisibility(View.INVISIBLE);
            txtCardNumber.setText("");
            txtCVV.setVisibility(View.INVISIBLE);
            txtCVV.setText("");
            txtExpiry.setVisibility(View.INVISIBLE);
            txtExpiry.setText("");
        });

        //checking all the fields, validating when the user clicks on the proceed button and displaying appropriate error messages
        btnProceed.setOnClickListener(proceed ->{
            if(txtFirstName.getText().toString().isEmpty()){
                txtFirstName.setError("Please enter First name to proceed");
            }
            if(txtLastName.getText().toString().isEmpty()){
                txtLastName.setError("Please enter Last name to proceed");
            }
            else if(txtPhone.getText().toString().isEmpty()){
                txtPhone.setError("Please enter Phone number to proceed");
            }
            else if(txtAddress.getText().toString().isEmpty()){
                txtAddress.setError("Please enter full address to proceed");
            }
            else if(txtQuantity.getText().toString().isEmpty()){
                txtQuantity.setError("Please enter total quantity to proceed");
            }
            else if(txtCardNumber.getText().toString().isEmpty()){
                if(radioCash.isChecked() && txtCardNumber.getText().toString().isEmpty()){
                    //proceeding to confirmation message page when user selects Cash option
                    Intent confirmationIntent = new Intent(proceed.getContext(), Confirmation.class);

                    confirmationIntent.putExtra("productName", productNameFromDB);

                    startActivity(confirmationIntent);
                    finish(); //stopping user from going back to checkout form after confirmation by closing/finishing this activity
                }
                else {
                    txtCardNumber.setError("Please enter your card number to proceed");
                }
            }
            else if(txtCVV.getText().toString().isEmpty()){
                if(radioCash.isChecked() && txtCVV.getText().toString().isEmpty()){
                    //proceeding to confirmation message page when user selects cash option
                    Intent confirmationIntent = new Intent(proceed.getContext(), Confirmation.class);

                    confirmationIntent.putExtra("productName", productNameFromDB);

                    startActivity(confirmationIntent);
                    finish(); //stopping user from going back to checkout form after confirmation by closing/finishing this activity
                }
                else {
                    txtCVV.setError("Please enter the cvv to proceed");
                }
            }
            else if(txtExpiry.getText().toString().isEmpty()){
                if(radioCash.isChecked() && txtExpiry.getText().toString().isEmpty()){
                    //proceeding to confirmation message page when user selects cash option
                    Intent confirmationIntent = new Intent(proceed.getContext(), Confirmation.class);

                    confirmationIntent.putExtra("productName", productNameFromDB);

                    startActivity(confirmationIntent);
                    finish(); //stopping user from going back to checkout form after confirmation by closing/finishing this activity
                }
                else {
                    txtExpiry.setError("Please enter the expiry date to proceed");
                }
            }
            else if(radioCash.isChecked() && txtCardNumber.getText().toString().isEmpty()){
                //proceeding to confirmation message page when user selects cash
                Intent confirmationIntent = new Intent(proceed.getContext(), Confirmation.class);

                confirmationIntent.putExtra("productName", productNameFromDB);

                startActivity(confirmationIntent);
                finish(); //stopping user from going back to checkout form after confirmation by closing/finishing this activity
            }
            else {
                //proceeding to confirmation message page when all the input fields are satisfied
                Intent confirmationIntent = new Intent(proceed.getContext(), Confirmation.class);

                confirmationIntent.putExtra("productName", productNameFromDB);

                startActivity(confirmationIntent);
                finish(); //stopping user from going back to checkout form after confirmation by closing/finishing this activity
            }
        });

        btnCancel.setOnClickListener(cancel ->{ //when the user clicks on the cancel button, closing the form to go back to previous page
            finish();
        });

        txtQuantityMessage.setText(""); //setting quantity message to empty string to make sure the message is empty when the form is loaded

        txtQuantity.addTextChangedListener(new TextWatcher() { //This method helps in listening the user input for quantity
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {} //we have to implement this method to use TextWatcher functionalities

            @Override
            public void onTextChanged(CharSequence userInput, int start, int before, int count) { //With this method we are updating price, quantity message to the user
                DecimalFormat df = new DecimalFormat("0.00"); //creating DecimalFormat object for further use
                txtQuantityMessage.setText(""); //setting quantity message to empty string to make sure the message is empty when the form is loaded

                //Validating the order quantity(if user enters any input) and calculating total price, displaying appropriate messages to the user
                if(userInput.length() != 0){
                    int quantity = Integer.parseInt(userInput.toString());

                    if(quantity >= 1 && quantity <= 10){
                        Double double_price = quantity * productPriceFromDB; //calculating total price
                        totalAmount.setText("$" + df.format(double_price)); //setting total amount in "totalAmount" textview after converting it into 2 decimal format
                    }
                    else if(quantity == 0){
                        txtQuantity.setText("1"); //If user enters 0 for quantity, converting it to 1
                        txtQuantityMessage.setText("Try at least one Coffee from us!"); //displaying the quantity change message to the user
                        totalAmount.setText("$" + productPriceFromDB); //setting price in textview
                    }
                    else {
                        txtQuantity.setText("10"); //if user enters >10 for quantity, converting it to 10
                        txtQuantityMessage.setText("Sorry, we can give only 10 Coffees per order!"); //displaying the quantity change message to the user
                        totalAmount.setText("$" + df.format(productPriceFromDB * 10)); //hence setting product price to that of 10 items
                    }
                }
                else if(userInput.length() == 0){
                    totalAmount.setText("");
                }
                else {
                    totalAmount.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {} //we have to implement this method to use TextWatcher functionalities

        });
    }
}
