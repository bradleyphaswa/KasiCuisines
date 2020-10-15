package com.bradley.kasicuisines;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

public class MainMenu extends AppCompatActivity {

    Button signInEmail, signInPhone, signUp;
    ImageView bgImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        backgroundAnimation();

        signInEmail = findViewById(R.id.sign_in_email);
        signInPhone = findViewById(R.id.sign_in_phone);
        signUp = findViewById(R.id.button_signup);

        signInEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainMenu.this, SelectLoginMethod.class);
                intent.putExtra("Home", "Email");
                startActivity(intent);

            }
        });

        signInPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainMenu.this, SelectLoginMethod.class);
                intent.putExtra("Home", "Phone");
                startActivity(intent);

            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainMenu.this, SelectLoginMethod.class);
                intent.putExtra("Home", "SignUp");
                startActivity(intent);

            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.gc();
    }

    //Background animation
    private void backgroundAnimation() {
        final Animation zoomIn = AnimationUtils.loadAnimation(this, R.anim.zoom_in);
        final Animation zoomOut = AnimationUtils.loadAnimation(this, R.anim.zoom_out);

        bgImage = findViewById(R.id.background);
        bgImage.setAnimation(zoomIn);
        bgImage.setAnimation(zoomOut);

        zoomOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                bgImage.setAnimation(zoomIn);
            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        zoomIn.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                bgImage.setAnimation(zoomOut);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }
}