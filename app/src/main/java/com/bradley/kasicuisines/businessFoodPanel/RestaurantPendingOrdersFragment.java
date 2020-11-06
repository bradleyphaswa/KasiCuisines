package com.bradley.kasicuisines.businessFoodPanel;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bradley.kasicuisines.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RestaurantPendingOrdersFragment extends Fragment {

    private RecyclerView recyclerView;
    private List<RestaurantPendingOrders1> restaurantPendingOrders1List;
    private RestaurantPendingOrdersAdapter adapter;
    private DatabaseReference databaseReference;
    private SwipeRefreshLayout swipeRefreshLayout;
    private View v;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle("Pending Orders");
        v = inflater.inflate(R.layout.fragment_restaurant_pending_orders, null);

        recyclerView = v.findViewById(R.id.Recycle_orders);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        restaurantPendingOrders1List = new ArrayList<>();
        swipeRefreshLayout = v.findViewById(R.id.Swipe_layoutt);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimaryDark, R.color.colorAccent);
        adapter = new RestaurantPendingOrdersAdapter(getContext(),restaurantPendingOrders1List);
        recyclerView.setAdapter(adapter);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                restaurantPendingOrders1List.clear();
                recyclerView = v.findViewById(R.id.Recycle_orders);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                restaurantPendingOrders1List = new ArrayList<>();
                restaurantorders();
            }
        });
        restaurantorders();
        return v;
    }

    private void restaurantorders() {
        databaseReference = FirebaseDatabase.getInstance().getReference("RestaurantPendingOrders")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    restaurantPendingOrders1List.clear();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        DatabaseReference data = FirebaseDatabase.getInstance().getReference("RestaurantPendingOrders")
                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(snapshot.getKey()).child("OtherInformation");
                        data.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                RestaurantPendingOrders1 restaurantPendingOrders1 = dataSnapshot.getValue(RestaurantPendingOrders1.class);
                                restaurantPendingOrders1List.add(restaurantPendingOrders1);
                                adapter = new RestaurantPendingOrdersAdapter(getContext(),restaurantPendingOrders1List);
                                recyclerView.setAdapter(adapter);
                                swipeRefreshLayout.setRefreshing(false);
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                    }
                }else{
                    swipeRefreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}