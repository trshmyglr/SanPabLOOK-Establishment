package com.example.sanpablook_establishment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class ConfirmRegistrationActivity extends AppCompatActivity {

    ImageButton backBtnConfirmReg;

    Button btnRegAcc;

    String [] item = {"Hotel", "Bed & Breakfast", "Resort", "Restaurant", "Cafe", "Al fresco", "Products"};
    AutoCompleteTextView autoCompleteTextView;
    ArrayAdapter<String> adapterItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_registration);

        //BACK BUTTON
        backBtnConfirmReg = findViewById(R.id.backBtnConfirmReg);
        backBtnConfirmReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();

            }
        });


        //BUTTON CONFIRM REGISTRATION
        btnRegAcc = findViewById(R.id.btnRegisterAcc);
        btnRegAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ConfirmRegistrationActivity.this, EmailConfirmationSent.class);

                startActivity(intent);

            }
        });

        autoCompleteTextView = findViewById(R.id.dropdownBtnBusType);
        adapterItems = new ArrayAdapter<String>(this, R.layout.list_item, item);
        autoCompleteTextView.setAdapter(adapterItems);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
                Toast.makeText(ConfirmRegistrationActivity.this, "Business Type: " + item, Toast.LENGTH_SHORT).show();
            }
        });
    }
}