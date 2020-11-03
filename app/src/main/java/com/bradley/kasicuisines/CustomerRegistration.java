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

public class CustomerRegistration extends AppCompatActivity {

    //Strings
    String street, province, city, suburb, firstName, lastName, email,
            password, confirmPassword, phone, role="Customer",
            emailPattern = "[a-zA-Z0-9]+@[a-z]+\\.+[a-z]+";

    //Widgets
    TextInputLayout c_street, c_province, c_city, c_suburb, c_first_name, c_last_name, c_email,
            c_password, c_confirm_password, c_phone;
    CountryCodePicker c_country_code;
    Button c_signup_button, c_signin_email, c_signin_phone;

    //Firebase
    FirebaseAuth mFirebaseAuth;
    DatabaseReference mDatabaseReference;
    FirebaseDatabase mFirebaseDatabase;

    ProgressDialog mDialog;
    public final static String PHONE_NUMBER =  "phoneNo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_registration);

        c_street = findViewById(R.id.c_street_no);
        c_province = findViewById(R.id.c_province);
        c_city = findViewById(R.id.c_city);
        c_suburb = findViewById(R.id.c_suburb);
        c_first_name = findViewById(R.id.c_first_name);
        c_last_name = findViewById(R.id.c_last_name);
        c_email = findViewById(R.id.c_email);
        c_password = findViewById(R.id.c_password);
        c_confirm_password = findViewById(R.id.c_confirm_password);
        c_phone = findViewById(R.id.c_phone);

        c_country_code = findViewById(R.id.c_country_code);

        c_signup_button = findViewById(R.id.c_signup_button);
        c_signin_email = findViewById(R.id.c_email_link_button);
        c_signin_phone = findViewById(R.id.c_phone_link_button);

        mDialog = new ProgressDialog(CustomerRegistration.this);

        mDatabaseReference = mFirebaseDatabase.getInstance().getReference("Customer");
        mFirebaseAuth = FirebaseAuth.getInstance();

        c_signup_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                street = c_street.getEditText().getText().toString();
                province = c_province.getEditText().getText().toString();
                city = c_city.getEditText().getText().toString();
                suburb = c_suburb.getEditText().getText().toString();
                street = c_street.getEditText().getText().toString();
                firstName = c_first_name.getEditText().getText().toString();
                lastName = c_last_name.getEditText().getText().toString();
                email = c_email.getEditText().getText().toString().trim();
                password = c_password.getEditText().getText().toString();
                confirmPassword = c_confirm_password.getEditText().getText().toString();
                phone = c_phone.getEditText().getText().toString();

                if(!isEmpty(street) && !isEmpty(province) && !isEmpty(city) && !isEmpty(suburb)
                        && !isEmpty(firstName)&&
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
                                        Toast.makeText(CustomerRegistration.this, "Passwords do not match",
                                                Toast.LENGTH_SHORT).show();
                                    }

                                }
                                else {
                                    Toast.makeText(CustomerRegistration.this, "Email already registered",
                                            Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    } else {
                        Toast.makeText(CustomerRegistration.this, "Please enter a valid email address",
                                Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(CustomerRegistration.this, "Please fill in all the fields",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        c_signin_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), CustomerLoginEmail.class));
                finish();
            }
        });

        c_signin_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), CustomerLoginPhone.class));
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
                            hashMap1.put("streetNo", street);
                            hashMap1.put("province", province);
                            hashMap1.put("city", city);
                            hashMap1.put("suburb", suburb);
                            hashMap1.put("firstName", firstName);
                            hashMap1.put("lastName", lastName);
                            hashMap1.put("email", email);
                            hashMap1.put("mobileNo", phone);

                            mFirebaseDatabase.getInstance().getReference("Customer")
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
                    Toast.makeText(CustomerRegistration.this, "Unable to Register",
                            Toast.LENGTH_SHORT).show();

                }

            }
        });
    }

    /**
     * Redirect to login screen
     */
    private void redirectLoginScreen() {
        startActivity(new Intent(CustomerRegistration.this, MainMenu.class));
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
                                AlertDialog.Builder builder = new AlertDialog.Builder(CustomerRegistration.this);
                                builder.setMessage("Email registered successfully!\n\n" +
                                        "An email with a verification link has been sent to the email provided.");
                                builder.setCancelable(false);
                                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        mDialog.dismiss();

                                        String phoneNumber = c_country_code.getSelectedCountryCodeWithPlus() + phone;
                                        Intent b = new Intent(CustomerRegistration.this, CustomerVerifyPhone.class);
                                        b.putExtra(PHONE_NUMBER, phoneNumber);
                                        startActivity(b);
                                    }
                                });

                                AlertDialog Alert = builder.create();
                                Alert.show();
                            }
                            else{
                                mDialog.dismiss();
                                Toast.makeText(CustomerRegistration.this, "Couldn't Send Verification Email", Toast.LENGTH_SHORT).show();
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