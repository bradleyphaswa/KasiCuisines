package com.bradley.kasicuisines.businessFoodPanel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

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

public class RestaurantOrderToBePrepared extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<RestaurantWaitingOrders1> restaurantWaitingOrders1List;
    private RestaurantOrderTobePreparedAdapter adapter;
    private DatabaseReference databaseReference;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_order_to_be_prepared);

        recyclerView = findViewById(R.id.Recycle_orderstobeprepared);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(RestaurantOrderToBePrepared.this));
        restaurantWaitingOrders1List = new ArrayList<>();
        swipeRefreshLayout = findViewById(R.id.Swipe1);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimaryDark, R.color.colorAccent);
        adapter = new RestaurantOrderTobePreparedAdapter(RestaurantOrderToBePrepared.this, restaurantWaitingOrders1List);
        recyclerView.setAdapter(adapter);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                restaurantWaitingOrders1List.clear();
                recyclerView = findViewById(R.id.Recycle_orderstobeprepared);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(RestaurantOrderToBePrepared.this));
                restaurantWaitingOrders1List = new ArrayList<>();
                restaurantorderstobePrepare();
            }
        });
        restaurantorderstobePrepare();

    }

    private void restaurantorderstobePrepare() {

        databaseReference = FirebaseDatabase.getInstance().getReference("RestaurantWaitingOrders")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    restaurantWaitingOrders1List.clear();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        DatabaseReference data = FirebaseDatabase.getInstance().getReference("RestaurantWaitingOrders")
                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(snapshot.getKey())
                                .child("OtherInformation");
                        data.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                RestaurantWaitingOrders1 restaurantWaitingOrders1 = dataSnapshot.getValue(RestaurantWaitingOrders1.class);
                                restaurantWaitingOrders1List.add(restaurantWaitingOrders1);
                                adapter = new RestaurantOrderTobePreparedAdapter(RestaurantOrderToBePrepared.this, restaurantWaitingOrders1List);
                                recyclerView.setAdapter(adapter);
                                swipeRefreshLayout.setRefreshing(false);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                    }
                } else {
                    swipeRefreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
