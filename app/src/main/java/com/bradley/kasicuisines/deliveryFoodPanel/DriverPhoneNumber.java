package com.bradley.kasicuisines.deliveryFoodPanel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.bradley.kasicuisines.R;
import com.bradley.kasicuisines.customerFoodPanel.CustomerPhoneNumber;
import com.bradley.kasicuisines.customerFoodPanel.CustomerPhoneSendOTP;
import com.hbb20.CountryCodePicker;

public class DriverPhoneNumber extends AppCompatActivity {

    EditText num;
    CountryCodePicker cpp;
    Button SendOTP;
    String number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_phone_number);

        num=(EditText)findViewById(R.id.d_phonenumber);
        cpp=(CountryCodePicker)findViewById(R.id.d_Countrycode);
        SendOTP=(Button)findViewById(R.id.d_sendotp);

        SendOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                number=num.getText().toString().trim();
                String phonenumber= cpp.getSelectedCountryCodeWithPlus() + number;
                Intent intent=new Intent(DriverPhoneNumber.this, DriverPhoneSendOTP.class);
                intent.putExtra("phoneNumber",phonenumber);
                startActivity(intent);
                finish();
            }
        });
    }
}