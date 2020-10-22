package com.bradley.kasicuisines;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.hbb20.CountryCodePicker;

public class BusinessLoginPhone extends AppCompatActivity {

    //Widgets
    EditText phone;
    CountryCodePicker countryCode;
    Button sendOtp, signInEmail;
    TextView signUp;


    FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_login_phone);

        phone = findViewById(R.id.b_phone);
        sendOtp = findViewById(R.id.b_send_otp);
        countryCode = findViewById(R.id.b_country_code);
        signInEmail = findViewById(R.id.b_email_link_button);
        signUp = findViewById(R.id.b_sign_up_link);

        mFirebaseAuth = FirebaseAuth.getInstance();

        sendOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phoneNumber = countryCode.getSelectedCountryCodeWithPlus()+
                        phone.getText().toString();
                Intent intent = new Intent(getApplicationContext(), BusinessSendOtp.class);
                intent.putExtra("phoneNumber", phoneNumber);
                startActivity(intent);
                finish();
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), BusinessRegistration.class));
                finish();
            }
        });

        signInEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), BusinessLoginEmail.class));
                finish();
            }
        });

    }
}