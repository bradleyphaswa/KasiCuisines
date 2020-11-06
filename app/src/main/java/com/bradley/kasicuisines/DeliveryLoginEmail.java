package com.bradley.kasicuisines;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class DeliveryLoginEmail extends AppCompatActivity {

    //constants
    private static final int ERROR_DIALOG_REQUEST = 9001;

    //Firebase
    private FirebaseAuth.AuthStateListener mAuthListener;

    // widgets
    private EditText mEmail, mPassword;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_login_email);

        mEmail = (EditText) findViewById(R.id.d_edt_email);
        mPassword = (EditText) findViewById(R.id.d_edt_password);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);

        setupFirebaseAuth();
        if(servicesOK()){
            init();
        }
        hideSoftKeyboard();
    }

    private void init(){
        Button signIn = findViewById(R.id.d_sign_in_email_button);
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //check if the fields are filled out
                if(!isEmpty(mEmail.getText().toString())
                        && !isEmpty(mPassword.getText().toString())){

                    showDialog();

                    FirebaseAuth.getInstance().signInWithEmailAndPassword(mEmail.getText().toString(),
                            mPassword.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    hideDialog();

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), "Authentication Failed", Toast.LENGTH_SHORT).show();
                            hideDialog();
                        }
                    });
                }else{
                    Toast.makeText(getApplicationContext(), "You didn't fill in all the fields.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button signInPhone = findViewById(R.id.d_phone_link_button);
        signInPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), DeliveryLoginPhone.class));
                finish();
            }
        });

        TextView register = findViewById(R.id.d_sign_up_link);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), DeliveryRegistration.class);
                startActivity(intent);
            }
        });

        TextView resetPassword = findViewById(R.id.d_forgot_password);
        resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PasswordResetDialog dialog = new PasswordResetDialog();
                dialog.show(getSupportFragmentManager(), "dialog_password_reset");
            }
        });

        TextView resendEmailVerification = findViewById(R.id.d_resend_verification_email);
        resendEmailVerification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ResendVerificationDialog dialog = new ResendVerificationDialog();
                dialog.show(getSupportFragmentManager(), "dialog_resend_email_verification");
            }
        });
    }

    public boolean servicesOK(){

        int isAvailable = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(DeliveryLoginEmail.this);

        if(isAvailable == ConnectionResult.SUCCESS){
            //everything is ok and the user can make mapping requests
            return true;
        }
        else if(GoogleApiAvailability.getInstance().isUserResolvableError(isAvailable)){


            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(DeliveryLoginEmail.this, isAvailable, ERROR_DIALOG_REQUEST);
            dialog.show();
        }
        else{
            Toast.makeText(this, "Can't connect to mapping services", Toast.LENGTH_SHORT).show();
        }

        return false;
    }

    /**
     * Return true if the @param is null
     * @param string
     * @return
     */
    private boolean isEmpty(String string){
        return string.equals("");
    }


    private void showDialog(){
        mProgressBar.setVisibility(View.VISIBLE);

    }

    private void hideDialog(){
        if(mProgressBar.getVisibility() == View.VISIBLE){
            mProgressBar.setVisibility(View.INVISIBLE);
        }
    }

    private void hideSoftKeyboard(){
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    /*
        ----------------------------- Firebase setup ---------------------------------
     */
    private void setupFirebaseAuth(){
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {

                    //check if email is verified
                    if(user.isEmailVerified()){

                        Toast.makeText(getApplicationContext(), "Authenticated with: " + user.getEmail(), Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(getApplicationContext(), DeliveryPanel_BottomNavigation.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();

                    }else{
                        Toast.makeText(DeliveryLoginEmail.this, "Email is not Verified\nCheck your Inbox", Toast.LENGTH_LONG).show();
                        FirebaseAuth.getInstance().signOut();
                    }

                }
                // ...
            }
        };
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseAuth.getInstance().addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            FirebaseAuth.getInstance().removeAuthStateListener(mAuthListener);
        }
    }
}