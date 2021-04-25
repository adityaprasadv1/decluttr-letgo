package com.example.myshoppingstore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;

    private EditText txtCreateUsername, txtCreateEmail, txtCreatePassword;
    private Button btnCreateLogin, btnCreateRegister;
    private ProgressBar createProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        btnCreateLogin = (Button) findViewById(R.id.btnCreateLogin);
        btnCreateLogin.setOnClickListener(this);

        btnCreateRegister = (Button) findViewById(R.id.btnCreateRegister);
        btnCreateRegister.setOnClickListener(this);

        txtCreateUsername = (EditText) findViewById(R.id.txtCreateUsername);
        txtCreateEmail = (EditText) findViewById(R.id.txtCreateEmail);
        txtCreatePassword = (EditText) findViewById(R.id.txtCreatePassword);

        createProgressBar = (ProgressBar) findViewById(R.id.createProgressBar);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btnCreateLogin:
                startActivity(new Intent(this, Login.class));
                break;
            case R.id.btnCreateRegister:
                registerUser();
                break;
        }
    }

    private void registerUser() {
        String userUsername = txtCreateUsername.getText().toString().trim();
        String userEmail = txtCreateEmail.getText().toString().trim();
        String userPassword = txtCreatePassword.getText().toString().trim();

        if (userUsername.isEmpty()) {
            txtCreateUsername.setError("Username is required!");
            txtCreateUsername.requestFocus();
            return;
        }

        if (userEmail.isEmpty()) {
            txtCreateEmail.setError("Email is required!");
            txtCreateEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()) {
            txtCreateEmail.setError("Email is not valid!");
            txtCreateEmail.requestFocus();
            return;
        }

        if (userPassword.isEmpty()) {
            txtCreatePassword.setError("Password is required!");
            txtCreatePassword.requestFocus();
            return;
        }

        if (userPassword.length() < 6) {
            txtCreatePassword.setError("Minimum Password length is 6!");
            txtCreatePassword.requestFocus();
            return;
        }

        createProgressBar.setVisibility(View.VISIBLE);

        Log.i("TESTING", "UserEmail: " + userEmail + "UserPassword: " + userPassword);
        mAuth.createUserWithEmailAndPassword(userEmail, userPassword)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        User user = new User(userUsername, userEmail);

                        FirebaseDatabase
                                .getInstance()
                                .getReference("Users")
                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                .setValue(user).addOnCompleteListener(task1 -> {
                                    if (task1.isSuccessful()) {
                                        Toast.makeText(RegisterActivity.this, "User registered successfully!", Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(this, Home.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        Toast.makeText(RegisterActivity.this, "User failed to register!", Toast.LENGTH_LONG).show();
                                    }
                                    createProgressBar.setVisibility(View.GONE);
                                });
                    } else {
                        Toast.makeText(RegisterActivity.this, "Failed to register!", Toast.LENGTH_SHORT).show();
                        createProgressBar.setVisibility(View.GONE);
                    }
                });
    }
}