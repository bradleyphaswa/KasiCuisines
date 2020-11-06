package com.bradley.kasicuisines;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SelectLoginMethod extends AppCompatActivity {

    Button customer, business, driver;
    Intent mIntent;
    String type;
    ConstraintLayout bgImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_login_method);

        mIntent = getIntent();
        type = mIntent.getStringExtra("Home").toString().trim();

        customer = findViewById(R.id.customer);
        business = findViewById(R.id.business);
        driver = findViewById(R.id.driver);

        customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(type.equals("Email")) {
                    startActivity(new Intent(SelectLoginMethod.this, CustomerLoginEmail.class));

                }
                if(type.equals("Phone")) {
                    startActivity(new Intent(SelectLoginMethod.this, CustomerLoginPhone.class));

                }

                if(type.equals("SignUp")) {
                    startActivity(new Intent(SelectLoginMethod.this, CustomerRegistration.class));

                }
            }
        });

        business.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(type.equals("Email")) {
                    startActivity(new Intent(SelectLoginMethod.this, RestaurantLoginEmail.class));

                }
                if(type.equals("Phone")) {
                    startActivity(new Intent(SelectLoginMethod.this, RestaurantLoginPhone.class));

                }

                if(type.equals("SignUp")) {
                    startActivity(new Intent(SelectLoginMethod.this, RestaurantRegistration.class));

                }
            }
        });

        driver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(type.equals("Email")) {
                    startActivity(new Intent(SelectLoginMethod.this, DeliveryLoginEmail.class));

                }
                if(type.equals("Phone")) {
                    startActivity(new Intent(SelectLoginMethod.this, DeliveryLoginPhone.class));

                }

                if(type.equals("SignUp")) {
                    startActivity(new Intent(SelectLoginMethod.this, DeliveryRegistration.class));

                }
            }
        });
    }
}