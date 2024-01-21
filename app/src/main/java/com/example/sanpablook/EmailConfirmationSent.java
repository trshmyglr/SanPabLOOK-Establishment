package com.example.sanpablook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.sanpablook_establishment.R;

public class EmailConfirmationSent extends AppCompatActivity {

    Button btnVerifyEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_confirmation_sent);

        //BUTTON VERIFY EMAIL REDIRECT TO CONFIRM REGISTRATION
        btnVerifyEmail = findViewById(R.id.verifyEmailBtn);
        btnVerifyEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EmailConfirmationSent.this, RegistrationProcessActivity.class);
                startActivity(intent);
            }
        });

    }
}