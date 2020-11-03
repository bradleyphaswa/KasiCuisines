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

public class BusinessRegistration extends AppCompatActivity {

    //Strings
    String storeName, street, province, city, suburb, firstName, lastName, email,
            password, confirmPassword, phone, role="Restaurant",
            emailPattern = "[a-zA-Z0-9]+@[a-z]+\\.+[a-z]+";

    //Widgets
    TextInputLayout store_name, street_no, prvnc, cty, sub, b_first_name, b_last_name, b_email,
            b_password, b_confirm_password, b_phone;
    CountryCodePicker b_country_code;
    Button b_signup_button, b_signin_email, b_signin_phone;

    //Firebase
    FirebaseAuth mFirebaseAuth;
    DatabaseReference mDatabaseReference;
    FirebaseDatabase mFirebaseDatabase;

    ProgressDialog mDialog;
    public final static String PHONE_NUMBER =  "phoneNo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_registration);

        store_name = findViewById(R.id.store_name);
        street_no = findViewById(R.id.street_address);
        prvnc = findViewById(R.id.province);
        cty = findViewById(R.id.city);
        sub = findViewById(R.id.suburb);
        b_first_name = findViewById(R.id.b_first_name);
        b_last_name = findViewById(R.id.b_last_name);
        b_email = findViewById(R.id.b_email);
        b_password = findViewById(R.id.b_password);
        b_confirm_password = findViewById(R.id.b_confirm_password);
        b_phone = findViewById(R.id.b_phone);

        b_country_code = findViewById(R.id.b_country_code);

        b_signup_button = findViewById(R.id.b_signup_button);
        b_signin_email = findViewById(R.id.b_email_link_button);
        b_signin_phone = findViewById(R.id.b_phone_link_button);

        mDialog = new ProgressDialog(BusinessRegistration.this);

        mDatabaseReference = mFirebaseDatabase.getInstance().getReference("Restaurant");
        mFirebaseAuth = FirebaseAuth.getInstance();

        b_signup_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                storeName = store_name.getEditText().getText().toString();
                street = street_no.getEditText().getText().toString();
                province = prvnc.getEditText().getText().toString();
                city = cty.getEditText().getText().toString();
                suburb = sub.getEditText().getText().toString();
                firstName = b_first_name.getEditText().getText().toString();
                lastName = b_last_name.getEditText().getText().toString();
                email = b_email.getEditText().getText().toString().trim();
                password = b_password.getEditText().getText().toString();
                confirmPassword = b_confirm_password.getEditText().getText().toString();
                phone = b_phone.getEditText().getText().toString();

                if(!isEmpty(storeName) && !isEmpty(street) && !isEmpty(province)
                        && !isEmpty(suburb) && !isEmpty(firstName) &&
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
                                        Toast.makeText(BusinessRegistration.this, "Passwords do not match",
                                                Toast.LENGTH_SHORT).show();
                                    }

                                }
                                else {
                                    Toast.makeText(BusinessRegistration.this, "Email already registered",
                                            Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    } else {
                        Toast.makeText(BusinessRegistration.this, "Please enter a valid email address",
                                Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(BusinessRegistration.this, "Please fill in all the fields",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        b_signin_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), BusinessLoginEmail.class));
                finish();
            }
        });

        b_signin_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), BusinessLoginPhone.class));
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
                            hashMap1.put("storeName", storeName);
                            hashMap1.put("streetNo", street);
                            hashMap1.put("province", province);
                            hashMap1.put("city", city);
                            hashMap1.put("suburb", suburb);
                            hashMap1.put("firstName", firstName);
                            hashMap1.put("lastName", lastName);
                            hashMap1.put("email", email);
                            hashMap1.put("mobileNo", phone);

                            mFirebaseDatabase.getInstance().getReference("Restaurant")
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
                    Toast.makeText(BusinessRegistration.this, "Unable to Register",
                            Toast.LENGTH_SHORT).show();

                }

            }
        });
    }

    /**
     * Redirect to login screen
     */
    private void redirectLoginScreen() {
        startActivity(new Intent(BusinessRegistration.this, MainMenu.class));
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
                                AlertDialog.Builder builder = new AlertDialog.Builder(BusinessRegistration.this);
                                builder.setMessage("Email registered successfully!\n\n" +
                                        "An email with a verification link has been sent to the email provided.");
                                builder.setCancelable(false);
                                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        mDialog.dismiss();

                                        String phoneNumber = b_country_code.getSelectedCountryCodeWithPlus() + phone;
                                        Intent b = new Intent(BusinessRegistration.this, BusinessVerifyPhone.class);
                                        b.putExtra(PHONE_NUMBER, phoneNumber);
                                        startActivity(b);
                                    }
                                });

                                AlertDialog Alert = builder.create();
                                Alert.show();
                            }
                            else{
                                mDialog.dismiss();
                                Toast.makeText(BusinessRegistration.this, "Couldn't Send Verification Email", Toast.LENGTH_SHORT).show();
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