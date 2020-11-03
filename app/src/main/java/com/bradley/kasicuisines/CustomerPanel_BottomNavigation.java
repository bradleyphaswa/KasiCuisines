package com.bradley.kasicuisines;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.bradley.kasicuisines.customerFoodPanel.CustomerCartFragment;
import com.bradley.kasicuisines.customerFoodPanel.CustomerHomeFragment;
import com.bradley.kasicuisines.customerFoodPanel.CustomerOrdersFragment;
import com.bradley.kasicuisines.customerFoodPanel.CustomerProfileFragment;
import com.bradley.kasicuisines.customerFoodPanel.CustomerTrackFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class CustomerPanel_BottomNavigation extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_panel__bottom_navigation);
        BottomNavigationView navigationView = findViewById(R.id.c_bottom_nav);
        navigationView.setOnNavigationItemSelectedListener(this);
        String name = getIntent().getStringExtra("PAGE");
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if(name != null) {
            if(name.equalsIgnoreCase("HomePage")) {
                loadCustomerFragment(new CustomerHomeFragment());
            } else if(name.equalsIgnoreCase("PreparingPage")) {
                loadCustomerFragment(new CustomerTrackFragment());
            }
            else if(name.equalsIgnoreCase("DeliveryOrderPage")) {
                loadCustomerFragment(new CustomerTrackFragment());
            } else if(name.equalsIgnoreCase("ThankYouPage")) {
                loadCustomerFragment(new CustomerHomeFragment());
            }
        } else {
            loadCustomerFragment(new CustomerHomeFragment());
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;
        switch (item.getItemId()) {

            case R.id.customer_home:
                fragment = new CustomerHomeFragment();
                break;

            case R.id.customer_cart:
                fragment = new CustomerCartFragment();
                break;

            case R.id.customer_order:
                fragment = new CustomerOrdersFragment();
                break;

            case R.id.customer_track:
                fragment = new CustomerTrackFragment();
                break;

            case R.id.customer_profile:
                fragment = new CustomerProfileFragment();
                break;
        }
        
        return loadCustomerFragment(fragment);
    }

    private boolean loadCustomerFragment(Fragment fragment) {

        if(fragment != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.c_fragment_container, fragment).commit();
            return true;
        }

        return false;
    }
}