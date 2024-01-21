package com.example.sanpablook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.sanpablook_establishment.R;

public class RegistrationProcessActivity extends AppCompatActivity {

    TextView btnBackLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_process);
        btnBackLogin = findViewById(R.id.btnBackLogIn);
        btnBackLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegistrationProcessActivity.this, RegistrationCompleteActivity.class);
                startActivity(intent);
            }
        });



    }
}