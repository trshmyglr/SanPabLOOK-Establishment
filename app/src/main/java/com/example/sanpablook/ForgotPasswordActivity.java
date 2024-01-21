package com.example.sanpablook;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.sanpablook_establishment.R;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends AppCompatActivity {

    Button sendEmailBtn;
    FirebaseAuth mAuth;
    EditText emailField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        mAuth = FirebaseAuth.getInstance();
        EditText emailField = findViewById(R.id.txtForgotEmailField);
        Button sendEmailBtn = findViewById(R.id.sendEmailBtn);


    }
}