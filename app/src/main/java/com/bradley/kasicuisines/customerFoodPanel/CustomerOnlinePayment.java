package com.bradley.kasicuisines.customerFoodPanel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import com.bradley.kasicuisines.R;
import com.google.android.material.textfield.TextInputLayout;

public class CustomerOnlinePayment extends AppCompatActivity {


    TextInputLayout cardName, cardNumber, expiryDate, cvv;
    Button addCard;
    String name, number, date, CVV;
    String randomUID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_online_payment);

        addCard = (Button) findViewById(R.id.addcard);
        cardName = (TextInputLayout) findViewById(R.id.nameoncard);
        cardNumber = (TextInputLayout) findViewById(R.id.cardnumber);
        expiryDate = (TextInputLayout) findViewById(R.id.expirydate);
        cvv = (TextInputLayout) findViewById(R.id.CVV);
        randomUID = getIntent().getStringExtra("randomUID");

        addCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                name = cardName.getEditText().getText().toString().trim();
                number = cardNumber.getEditText().getText().toString().trim();
                date = expiryDate.getEditText().getText().toString().trim();
                CVV = cvv.getEditText().getText().toString().trim();
                if (valid()) {
                    Intent intent = new Intent(CustomerOnlinePayment.this, CustomerPaymentOTP.class);
                    intent.putExtra("randomUID",randomUID);
                    startActivity(intent);
                }

            }
        });
    }

    private boolean valid() {


        cardName.setErrorEnabled(false);
        cardName.setError("");
        cardNumber.setErrorEnabled(false);
        cardNumber.setError("");
        expiryDate.setErrorEnabled(false);
        expiryDate.setError("");
        cvv.setErrorEnabled(false);
        cvv.setError("");


        boolean isValidname = false, isValidlnumber = false, isValidexpiry = false, isValidcvv = false, isvalid = false;
        if (TextUtils.isEmpty(name)) {
            cardName.setErrorEnabled(true);
            cardName.setError("Cardname is required");
        } else {
            isValidname = true;
        }
        if (TextUtils.isEmpty(number)) {
            cardNumber.setErrorEnabled(true);
            cardNumber.setError("Cardnumber is required");
        } else {
            if (number.length() < 16) {
                cardNumber.setErrorEnabled(true);
                cardNumber.setError("Invalid number");
            } else {
                isValidlnumber = true;
            }
        }
        if (TextUtils.isEmpty(date)) {
            expiryDate.setErrorEnabled(true);
            expiryDate.setError("Expiry date is required");
        } else {
            isValidexpiry = true;

        }
        if (TextUtils.isEmpty(CVV)) {
            cvv.setErrorEnabled(true);
            cvv.setError("CVV is required");
        } else {
            if (CVV.length() < 3) {
                cvv.setErrorEnabled(true);
                cvv.setError("Invalid CVV number");
            } else {
                isValidcvv = true;
            }
        }
        isvalid = (isValidname && isValidlnumber && isValidexpiry && isValidcvv) ? true : false;
        return isvalid;

    }
}