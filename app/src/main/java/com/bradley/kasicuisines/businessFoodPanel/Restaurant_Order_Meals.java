package com.bradley.kasicuisines.businessFoodPanel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.bradley.kasicuisines.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Restaurant_Order_Meals extends AppCompatActivity {

    RecyclerView recyclerViewMeal;
    private List<RestaurantPendingOrders> restaurantPendingOrdersList;
    private RestaurantOrderMealAdapter adapter;
    DatabaseReference reference;
    String randomUID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant__order__meals);

        recyclerViewMeal = findViewById(R.id.Recycle_orders_dish);
        recyclerViewMeal.setHasFixedSize(true);
        recyclerViewMeal.setLayoutManager(new LinearLayoutManager(this));
        restaurantPendingOrdersList = new ArrayList<>();

        RestaurantOrderMeals();
    }

    private void RestaurantOrderMeals() {

        randomUID = getIntent().getStringExtra("randomUID");

        reference = FirebaseDatabase.getInstance().getReference("RestaurantPendingOrders")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(randomUID).child("Meals");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                restaurantPendingOrdersList.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    RestaurantPendingOrders restaurantPendingOrders = snapshot.getValue(RestaurantPendingOrders.class);
                    restaurantPendingOrdersList.add(restaurantPendingOrders);
                }
                adapter = new RestaurantOrderMealAdapter(Restaurant_Order_Meals.this, restaurantPendingOrdersList);
                recyclerViewMeal.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
