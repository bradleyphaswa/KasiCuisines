package com.bradley.kasicuisines;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.bradley.kasicuisines.deliveryFoodPanel.DeliveryPendingOrdersFragment;
import com.bradley.kasicuisines.deliveryFoodPanel.DeliveryProfileFragment;
import com.bradley.kasicuisines.deliveryFoodPanel.DeliveryShipOrdersFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class DriverPanel_BottomNavigation extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_panel__bottom_navigation);
        BottomNavigationView navigationView = findViewById(R.id.delivery_bottom_navigation);
        navigationView.setOnNavigationItemSelectedListener(this);
        String name = getIntent().getStringExtra("PAGE");
        if(name != null) {
            if(name.equalsIgnoreCase("DeliveryOrderPage")) {
                loadDeliveryFragment(new DeliveryPendingOrdersFragment());
            } else {
                loadDeliveryFragment(new DeliveryPendingOrdersFragment());
            }
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;

        switch (item.getItemId()) {
            case R.id.d_pending_orders:
                fragment = new DeliveryPendingOrdersFragment();
                break;

            case R.id.d_ship_orders:
                fragment = new DeliveryShipOrdersFragment();
                break;

            case R.id.d_profile:
                fragment = new DeliveryProfileFragment();
                break;
        }
        return loadDeliveryFragment(fragment);
    }

    private boolean loadDeliveryFragment(Fragment fragment) {
        if(fragment != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.d_fragment_container, fragment).commit();
            return true;
        }

        return false;
    }
}