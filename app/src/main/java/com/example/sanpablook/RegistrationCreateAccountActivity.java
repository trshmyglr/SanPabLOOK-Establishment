package com.example.sanpablook;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.sanpablook_establishment.R;
import com.example.sanpablook_establishment.databinding.ActivityBottomNavBarBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class RegistrationCreateAccountActivity extends AppCompatActivity {
    ImageButton btnReturn;
    Button btnConfirmAccount;

    String [] item = {"Hotel", "Bed & Breakfast", "Resort", "Restaurant", "Cafe", "Al fresco", "Products"};
    AutoCompleteTextView autoCompleteTextView;
    ArrayAdapter<String> adapterItems;
    FirebaseAuth mAuth;
    FirebaseFirestore fStore;
    EditText editTextBusinessName, editTextBusinessPermit, editTextBusinessEmail, editTextTypeOfBusiness, editTextBusinessAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_create_account);

        mAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        //BACK BUTTON
        btnReturn = findViewById(R.id.btnReturn);
        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getOnBackPressedDispatcher();
            }
        });

        //BUTTON CREATE ACCOUNT
        btnConfirmAccount = findViewById(R.id.btnConfirmCreateAccount);
        btnConfirmAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Retrieve the email and password
                String email = getIntent().getStringExtra("email");
                String password = getIntent().getStringExtra("password");

                // Sign in the user
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                // Retrieve the user's input from the form fields
                                String businessName = ((EditText) findViewById(R.id.editTextBusinessName)).getText().toString();
                                String businessPermit = ((EditText) findViewById(R.id.editTextBusinessPermit)).getText().toString();
                                String businessEmail = ((EditText) findViewById(R.id.editTextBusinessEmail)).getText().toString();
                                String businessType = ((AutoCompleteTextView) findViewById(R.id.dropdownBizType)).getText().toString();
                                String businessAddress = ((EditText) findViewById(R.id.editTextBusinessAddress)).getText().toString();

                                // Create a map to hold the new user information
                                Map<String, Object> user = new HashMap<>();
                                user.put("businessName", businessName);
                                user.put("businessPermit", businessPermit);
                                user.put("businessEmail", businessEmail);
                                user.put("businessType", businessType);
                                user.put("businessAddress", businessAddress);

                                // Update the user's document in Firestore
                                FirebaseUser currentUser = mAuth.getCurrentUser();
                                if (currentUser != null) {
                                    fStore.collection("users").document(currentUser.getUid())
                                            .set(user, SetOptions.merge())
                                            .addOnSuccessListener(aVoid -> {
                                                Toast.makeText(RegistrationCreateAccountActivity.this, "Account Created", Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(RegistrationCreateAccountActivity.this, BottomNavBarActivity.class);
                                                startActivity(intent);
                                            })
                                            .addOnFailureListener(e -> {
                                                Toast.makeText(RegistrationCreateAccountActivity.this, "Please fill out the missing fields", Toast.LENGTH_SHORT).show();
                                            });
                                }
                            } else {
                                Exception exception = task.getException();
                                Toast.makeText(RegistrationCreateAccountActivity.this, "Sign in failed: " + exception.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        autoCompleteTextView = findViewById(R.id.dropdownBizType);
        adapterItems = new ArrayAdapter<String>(this, R.layout.list_item, item);
        autoCompleteTextView.setAdapter(adapterItems);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
                Toast.makeText(RegistrationCreateAccountActivity.this, "Business Type: " + item, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public boolean registerUserIfEmailVerified() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            if (user.isEmailVerified()) {
                // Delete the existing user
                user.delete().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Call your register function here
                        registerEstabUser(editTextBusinessName, editTextBusinessPermit, editTextBusinessEmail, editTextTypeOfBusiness, editTextBusinessAddress);
                    } else {
                        Toast.makeText(RegistrationCreateAccountActivity.this, "Failed to delete existing user.", Toast.LENGTH_SHORT).show();
                    }
                });
                return true;
            } else {
                Toast.makeText(RegistrationCreateAccountActivity.this, "Please verify your email before registering.", Toast.LENGTH_SHORT).show();
            }
        }
        return false;
    }

    private void registerEstabUser(EditText editTextBusinessName, EditText editTextBusinessPermit, EditText editTextBusinessEmail, EditText editTextTypeOfBusiness, EditText editTextBusinessAddress) {
        //EditText fields
        String businessName = editTextBusinessName.getText().toString().trim();
        String businessPermit = editTextBusinessPermit.getText().toString().trim();
        String businessEmail = editTextBusinessEmail.getText().toString().trim();
        String businessType = editTextTypeOfBusiness.getText().toString().trim();
        String businessAddress = editTextBusinessAddress.getText().toString().trim();

        // Check if the EditText fields are not empty
        if (TextUtils.isEmpty(businessName) || TextUtils.isEmpty(businessPermit) || TextUtils.isEmpty(businessEmail) || TextUtils.isEmpty(businessType) || TextUtils.isEmpty(businessAddress)) {
            Toast.makeText(RegistrationCreateAccountActivity.this, "Please fill out all fields.", Toast.LENGTH_SHORT).show();
            return;
        }

        //Generate random password
        String password = generateRandomPassword();

        // Use Firebase Authentication to create a new user with the email and generated password
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.createUserWithEmailAndPassword(businessEmail, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Get the user's ID
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        String userId = user.getUid();

                        FirebaseFirestore db = FirebaseFirestore.getInstance();
                        Map<String, Object> updates = new HashMap<>();
                        updates.put("businessName", businessName);
                        updates.put("businessPermit", businessPermit);
                        updates.put("businessEmail", businessEmail);
                        updates.put("businessType", businessType);
                        updates.put("businessAddress", businessAddress);
                        updates.put("password", password);

                        db.collection("Establishments").document(userId)
                                .update(updates)
                                .addOnSuccessListener(aVoid -> {
                                    Toast.makeText(RegistrationCreateAccountActivity.this, "Account Created", Toast.LENGTH_SHORT).show();
                                    Log.d(TAG, "DocumentSnapshot added with ID: " + userId);
                                })
                                .addOnFailureListener(e -> Log.w(TAG, "Error adding document", e));
                    } else {
                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                        Toast.makeText(RegistrationCreateAccountActivity.this, "Registration failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public String generateRandomPassword() {
        String possibleCharacters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder password = new StringBuilder();

        for (int i = 0; i < 6; i++) {
            int randomIndex = random.nextInt(possibleCharacters.length());
            password.append(possibleCharacters.charAt(randomIndex));
        }
        return password.toString();
    }
}