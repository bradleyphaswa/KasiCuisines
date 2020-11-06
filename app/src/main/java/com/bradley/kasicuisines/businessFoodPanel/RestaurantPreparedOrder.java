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

public class RestaurantPreparedOrder extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<RestaurantFinalOrders1> restaurantFinalOrders1List;
    private RestaurantPreparedOrderAdapter adapter;
    private DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_prepared_order);

        recyclerView = findViewById(R.id.Recycle_preparedOrders);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(RestaurantPreparedOrder.this));
        restaurantFinalOrders1List = new ArrayList<>();
        RestaurantPrepareOrders();
    }

    private void RestaurantPrepareOrders() {

        databaseReference = FirebaseDatabase.getInstance().getReference("RestaurantFinalOrders")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                restaurantFinalOrders1List.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    DatabaseReference data = FirebaseDatabase.getInstance().getReference("RestaurantFinalOrders")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(snapshot.getKey())
                            .child("OtherInformation");
                    data.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            RestaurantFinalOrders1 restaurantFinalOrders1 = dataSnapshot.getValue(RestaurantFinalOrders1.class);
                            restaurantFinalOrders1List.add(restaurantFinalOrders1);
                            adapter = new RestaurantPreparedOrderAdapter(RestaurantPreparedOrder.this, restaurantFinalOrders1List);
                            recyclerView.setAdapter(adapter);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}
