package com.bradley.kasicuisines;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth mFirebaseAuth;
    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference mDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                mFirebaseAuth = FirebaseAuth.getInstance();
                if(mFirebaseAuth.getCurrentUser()!=null) {
                    if(mFirebaseAuth.getCurrentUser().isEmailVerified()) {
                        mFirebaseAuth = FirebaseAuth.getInstance();

                        mDatabaseReference = FirebaseDatabase.getInstance().getReference("User")
                                .child(FirebaseAuth.getInstance().getUid()+"/Role");
                        mDatabaseReference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                String role = snapshot.getValue(String.class);
                                if(role.equals("Restaurant")) {
                                    startActivity(new Intent(getApplicationContext(), BusinessPanel_BottomNavigation.class));
                                    finish();
                                }

                                if(role.equals("Customer")) {
                                    startActivity(new Intent(getApplicationContext(), CustomerPanel_BottomNavigation.class));
                                    finish();
                                }

                                if(role.equals("Driver")) {
                                    startActivity(new Intent(getApplicationContext(), DriverPanel_BottomNavigation.class));
                                    finish();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        });
                    } else {
                        Toast.makeText(getApplicationContext(), "Email is not verified", Toast.LENGTH_LONG).show();
                        mFirebaseAuth.signOut();
                    }
                } else {

                    startActivity(new Intent(MainActivity.this, MainMenu.class));
                    finish();

                }

            }
        }, 3000);
    }
}