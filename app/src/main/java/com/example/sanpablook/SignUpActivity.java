package com.example.sanpablook;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sanpablook_establishment.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.Random;

import javax.mail.Session;
import javax.mail.PasswordAuthentication;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SignUpActivity extends AppCompatActivity {

    Button verifyAcc;

    ImageButton backBtn;

    TextView redirectSignIn;

    FirebaseAuth mAuth;
    FirebaseFirestore fStore;

    EditText enterFirstName, enterLastName, enterBusinessEmail, enterPhoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //Objects
        mAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        enterBusinessEmail = findViewById(R.id.enterBusinessEmail);

        //BACK BUTTON
        backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressedDispatcher();

            }
        });

        //BUTTON Register/ Verify  Account
        verifyAcc = findViewById(R.id.verifyAccntBtn);
        verifyAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = String.valueOf(enterBusinessEmail.getText());


                if (TextUtils.isEmpty(email)){
                    Toast.makeText(SignUpActivity.this, "Please enter email", Toast.LENGTH_SHORT).show();
                    enterBusinessEmail.setError("Please enter email");
                    enterBusinessEmail.requestFocus();
                } else {
                    registerEstabUser(enterFirstName, enterLastName, enterBusinessEmail, enterPhoneNumber);
                }
                    Toast.makeText(SignUpActivity.this, "Account Created", Toast.LENGTH_SHORT).show();
                    Intent VerifyAccintent = new Intent(SignUpActivity.this, EmailConfirmationSent.class);
                    startActivity(VerifyAccintent);
            }
        });

        // redirect back to Sign In
        redirectSignIn = findViewById(R.id.alreadyRegisteredBtn);
        redirectSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Shift to another activity here
                Intent signUpIntent = new Intent(SignUpActivity.this, SignInActivity.class);
                startActivity(signUpIntent);
            }
        });
    }

    private void registerEstabUser(EditText enterFirstName, EditText enterLastName, EditText enterBusinessEmail, EditText enterPhoneNumber) {
        String email = enterBusinessEmail.getText().toString().trim();

        String password = generateRandomPassword();

        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        String userId = Objects.requireNonNull(auth.getCurrentUser()).getUid();

                        FirebaseFirestore db = FirebaseFirestore.getInstance();
                        Map<String, Object> user = new HashMap<>();
                        user.put("businessEmail", email);
                        user.put("password", password);

                        db.collection("Establishments").document(userId)
                                .set(user)
                                .addOnSuccessListener(aVoid -> {
                                    Log.d(TAG, "DocumentSnapshot successfully written!");
                                })
                                .addOnFailureListener(e -> {
                                    Log.w(TAG, "Error writing document", e);
                                });

                        FirebaseUser firebaseUser = auth.getCurrentUser();
                        firebaseUser.sendEmailVerification().addOnCompleteListener(task1 -> {
                            if (task1.isSuccessful()) {
                                Toast.makeText(SignUpActivity.this, "Check your email for verification", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
    }

    private void onBackPressedDispatcher() {
        Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
        startActivity(intent);
        finish();
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