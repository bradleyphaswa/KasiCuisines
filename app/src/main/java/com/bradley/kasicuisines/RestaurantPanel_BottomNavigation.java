package com.bradley.kasicuisines;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.bradley.kasicuisines.SendNotification.Token;
import com.bradley.kasicuisines.businessFoodPanel.RestaurantHomeFragment;
import com.bradley.kasicuisines.businessFoodPanel.RestaurantOrdersFragment;
import com.bradley.kasicuisines.businessFoodPanel.RestaurantPendingOrdersFragment;
import com.bradley.kasicuisines.businessFoodPanel.RestaurantProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

public class RestaurantPanel_BottomNavigation extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_panel__bottom_navigation);
        BottomNavigationView navigationView = findViewById(R.id.business_bottom_nav);
        navigationView.setOnNavigationItemSelectedListener(this);
        UpdateToken();
        String name = getIntent().getStringExtra("PAGE");
        if(name !=null) {
            if(name.equalsIgnoreCase("OrderPage")) {
                loadBusinessFragment(new RestaurantPendingOrdersFragment());
            } else if(name.equalsIgnoreCase("ConfirmPage")) {
                loadBusinessFragment(new RestaurantOrdersFragment());
            } else if(name.equalsIgnoreCase("AcceptOrderPage")) {
                loadBusinessFragment(new RestaurantOrdersFragment());
            } else if(name.equalsIgnoreCase("DeliveredPage")) {
                loadBusinessFragment(new RestaurantOrdersFragment());
            }
        } else {
            loadBusinessFragment(new RestaurantHomeFragment());
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;
        switch (item.getItemId())
        {
            case R.id.business_home:
                fragment = new RestaurantHomeFragment();
                break;
            case R.id.pending_orders:
                fragment = new RestaurantPendingOrdersFragment();
                break;

            case R.id.orders:
                fragment = new RestaurantOrdersFragment();
                break;

            case R.id.business_profile:
                fragment = new RestaurantProfileFragment();
                break;
        }

        return loadBusinessFragment(fragment);
    }

    private void UpdateToken() {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String refreshToken = FirebaseInstanceId.getInstance().getToken();
        Token token = new Token(refreshToken);
        FirebaseDatabase.getInstance().getReference("Tokens").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(token);
    }

    private boolean loadBusinessFragment(Fragment fragment) {
        if(fragment != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.b_fragment_container, fragment).commit();
            return true;
        }

        return false;
    }
}