package com.bradley.kasicuisines;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hbb20.CountryCodePicker;

import java.util.HashMap;

public class DeliveryRegistration extends AppCompatActivity {

    //Strings
    String street, province, city, suburb, firstName, lastName, email,
            password, confirmPassword, phone, role="Driver",
            emailPattern = "[a-zA-Z0-9]+@[a-z]+\\.+[a-z]+";

    //Widgets
    TextInputLayout d_street_no, d_province, d_city, d_suburb, d_first_name, d_last_name, d_email,
            d_password, d_confirm_password, d_phone;
    CountryCodePicker d_country_code;
    Button d_signup_button, d_signin_email, d_signin_phone;

    //Firebase
    FirebaseAuth mFirebaseAuth;
    DatabaseReference mDatabaseReference;
    FirebaseDatabase mFirebaseDatabase;

    ProgressDialog mDialog;
    public final static String PHONE_NUMBER =  "phoneNumber";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_registration);

        d_first_name = findViewById(R.id.d_first_name);
        d_last_name = findViewById(R.id.d_last_name);
        d_email = findViewById(R.id.d_email);
        d_password = findViewById(R.id.d_password);
        d_confirm_password = findViewById(R.id.d_confirm_password);
        d_phone = findViewById(R.id.d_phone);

        d_country_code = findViewById(R.id.d_country_code);

        d_street_no = findViewById(R.id.d_street_no);
        d_province = findViewById(R.id.d_province);
        d_city = findViewById(R.id.d_city);
        d_suburb = findViewById(R.id.d_suburb);

        d_signup_button = findViewById(R.id.d_signup_button);
        d_signin_email = findViewById(R.id.d_email_link_button);
        d_signin_phone = findViewById(R.id.d_phone_link_button);

        mDialog = new ProgressDialog(DeliveryRegistration.this);

        mDatabaseReference = mFirebaseDatabase.getInstance().getReference("Drivers");
        mFirebaseAuth = FirebaseAuth.getInstance();

        d_signup_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firstName = d_first_name.getEditText().getText().toString();
                lastName = d_last_name.getEditText().getText().toString();
                email = d_email.getEditText().getText().toString().trim();
                password = d_password.getEditText().getText().toString();
                confirmPassword = d_confirm_password.getEditText().getText().toString();
                phone = d_phone.getEditText().getText().toString();
                street = d_street_no.getEditText().getText().toString();
                province = d_province.getEditText().getText().toString();
                city = d_city.getEditText().getText().toString();
                suburb = d_suburb.getEditText().getText().toString();

                if(!isEmpty(street) && !isEmpty(firstName) && !isEmpty(province) && !isEmpty(city) && !isEmpty(suburb) &&
                        !isEmpty(lastName) && !isEmpty(email) && !isEmpty(password) &&
                        !isEmpty(confirmPassword)  && !isEmpty(phone) ){

                    if(email.matches(emailPattern)) {
                        mFirebaseAuth.fetchSignInMethodsForEmail(email).addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                            @Override
                            public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {

                                Boolean isEmailEmpty = task.getResult().getSignInMethods().isEmpty();

                                if(isEmailEmpty) {

                                    //Check if passwords match
                                    if(doStringsMatch(password, confirmPassword)) {

                                        registerNewEmail(email, password);

                                    } else {
                                        Toast.makeText(DeliveryRegistration.this, "Passwords do not match",
                                                Toast.LENGTH_SHORT).show();
                                    }

                                }
                                else {
                                    Toast.makeText(DeliveryRegistration.this, "Email already registered",
                                            Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    } else {
                        Toast.makeText(DeliveryRegistration.this, "Please enter a valid email address",
                                Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(DeliveryRegistration.this, "Please fill in all the fields",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        d_signin_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), DeliveryLoginEmail.class));
                finish();
            }
        });

        d_signin_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), DeliveryLoginPhone.class));
                finish();
            }
        });
    }

    /**
     * Register new email
     */
    private void registerNewEmail(final String email, final String password) {

        mDialog.setCancelable(false);
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.setMessage("Registration in progress, please wait.....");
        mDialog.show();

        mFirebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {

                    String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    mDatabaseReference = FirebaseDatabase.getInstance().getReference("User").child(userId);
                    final HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put("Role", role);
                    mDatabaseReference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            HashMap<String, String> hashMap1 = new HashMap<>();
                            hashMap1.put("firstName", firstName);
                            hashMap1.put("lastName", lastName);
                            hashMap1.put("email", email);
                            hashMap1.put("mobileNo", phone);
                            hashMap1.put("streetNo", street);
                            hashMap1.put("province", province);
                            hashMap1.put("city", city);
                            hashMap1.put("suburb", suburb);

                            mFirebaseDatabase.getInstance().getReference("Drivers")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(hashMap1)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            mDialog.dismiss();

                                            sendVerificationEmail();
                                        }
                                    });
                        }
                    });
                }

                if(!task.isSuccessful()) {
                    mDialog.dismiss();
                    Toast.makeText(DeliveryRegistration.this, "Unable to Register",
                            Toast.LENGTH_SHORT).show();

                }

            }
        });
    }

    /**
     * Redirect to login screen
     */
    private void redirectLoginScreen() {
        startActivity(new Intent(DeliveryRegistration.this, MainMenu.class));
    }

    /**
     * sends an email verification link to the user
     */
    private void sendVerificationEmail() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            user.sendEmailVerification()
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(DeliveryRegistration.this);
                                builder.setMessage("Email registered successfully!\n\n" +
                                        "An email with a verification link has been sent to the email provided.");
                                builder.setCancelable(false);
                                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        mDialog.dismiss();

                                        String phoneNumber = d_country_code.getSelectedCountryCodeWithPlus() + phone;
                                        Intent b = new Intent(DeliveryRegistration.this, DeliveryVerifyPhone.class);
                                        b.putExtra(PHONE_NUMBER, phoneNumber);
                                        startActivity(b);
                                    }
                                });

                                AlertDialog Alert = builder.create();
                                Alert.show();
                            }
                            else{
                                mDialog.dismiss();
                                Toast.makeText(DeliveryRegistration.this, "Couldn't Send Verification Email", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }

    }

    private boolean isEmpty(String string){
        return string.equals("");
    }

    private boolean doStringsMatch(String s1, String s2){
        return s1.equals(s2);
    }
}