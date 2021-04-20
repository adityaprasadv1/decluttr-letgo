package com.example.myshoppingstore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;

    private TextView register;
    private EditText txtEmail, txtPassword;
    private Button btnLogin;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        register = (TextView) findViewById(R.id.btnRegister);
        register.setOnClickListener(this);

        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);

        txtEmail = (EditText) findViewById(R.id.txtEmail);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btnRegister:
                startActivity(new Intent(this, RegisterActivity.class));
                break;
            case R.id.btnLogin:
                userLogin();
                break;
        }
    }

    private void userLogin() {
        String email = txtEmail.getText().toString().trim();
        String password = txtPassword.getText().toString().trim();

        if (email.isEmpty()) {
            txtEmail.setError("Email is required!");
            txtEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            txtEmail.setError("Email is not valid!");
            txtEmail.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            txtPassword.setError("Password is required!");
            txtPassword.requestFocus();
            return;
        }

        if (password.length() < 6) {
            txtPassword.setError("Minimum Password length is 6!");
            txtPassword.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(Login.this, "Login Success!", Toast.LENGTH_LONG).show();
                    Intent homeIntent = new Intent(Login.this, Home.class);
                    startActivity(homeIntent);
                } else {
                    Toast.makeText(Login.this, "Login failed! Check credentials again!", Toast.LENGTH_LONG).show();
                }
                progressBar.setVisibility(View.GONE);
            }
        });
    }
}