package com.bradley.kasicuisines;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.bradley.kasicuisines.businessFoodPanel.BusinessHomeFragment;
import com.bradley.kasicuisines.businessFoodPanel.BusinessOrdersFragment;
import com.bradley.kasicuisines.businessFoodPanel.BusinessPendingOrdersFragment;
import com.bradley.kasicuisines.businessFoodPanel.BusinessProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BusinessPanel_BottomNavigation extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_panel__bottom_navigation);
        BottomNavigationView navigationView = findViewById(R.id.business_bottom_nav);
        navigationView.setOnNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;
        switch (item.getItemId())
        {
            case R.id.business_home:
                fragment = new BusinessHomeFragment();
                break;
            case R.id.pending_orders:
                fragment = new BusinessPendingOrdersFragment();
                break;

            case R.id.orders:
                fragment = new BusinessOrdersFragment();
                break;

            case R.id.business_profile:
                fragment = new BusinessProfileFragment();
                break;
        }

        return loadBusinessFragment(fragment);
    }

    private boolean loadBusinessFragment(Fragment fragment) {
        if(fragment != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.b_fragment_container, fragment).commit();
            return true;
        }

        return false;
    }
}