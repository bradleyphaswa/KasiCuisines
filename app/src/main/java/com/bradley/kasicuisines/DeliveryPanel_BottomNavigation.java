package com.bradley.kasicuisines;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.bradley.kasicuisines.SendNotification.Token;
import com.bradley.kasicuisines.deliveryFoodPanel.DeliveryPendingOrderFragment;
import com.bradley.kasicuisines.deliveryFoodPanel.DeliveryProfileFragment;
import com.bradley.kasicuisines.deliveryFoodPanel.DeliveryShipOrderFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

public class DeliveryPanel_BottomNavigation extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_panel__bottom_navigation);
        BottomNavigationView navigationView = findViewById(R.id.delivery_bottom_navigation);
        navigationView.setOnNavigationItemSelectedListener(this);

        UpdateToken();


        String name = getIntent().getStringExtra("PAGE");
        if(name != null) {

            if(name.equalsIgnoreCase("DeliveryOrderPage")) {

                loadDeliveryFragment(new DeliveryPendingOrderFragment());

            } else {

                loadDeliveryFragment(new DeliveryPendingOrderFragment());
            }
        }
    }

    private void UpdateToken() {

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String refreshToken = FirebaseInstanceId.getInstance().getToken();
        Token token = new Token(refreshToken);
        FirebaseDatabase.getInstance().getReference("Tokens")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(token);
    }

    private boolean loadDeliveryFragment(Fragment fragment) {
        if(fragment != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.d_fragment_container, fragment).commit();
            return true;
        }

        return false;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;

        switch (item.getItemId()) {
            case R.id.d_pending_orders:
                fragment = new DeliveryPendingOrderFragment();
                break;

            case R.id.d_ship_orders:
                fragment = new DeliveryShipOrderFragment();
                break;

            case R.id.d_profile:
                fragment = new DeliveryProfileFragment();
                break;
        }
        return loadDeliveryFragment(fragment);
    }
}