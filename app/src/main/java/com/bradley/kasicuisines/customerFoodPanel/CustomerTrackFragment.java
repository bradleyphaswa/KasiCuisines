package com.bradley.kasicuisines.customerFoodPanel;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bradley.kasicuisines.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CustomerTrackFragment extends Fragment {

    RecyclerView recyclerView;
    private List<CustomerFinalOrders> customerFinalOrdersList;
    private CustomerTrackAdapter adapter;
    DatabaseReference databaseReference;
    TextView grandTotal, address, status;
    LinearLayout total;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_customer_track, null);
        getActivity().setTitle("Track");
        recyclerView = v.findViewById(R.id.recyclefinalorders);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        grandTotal = v.findViewById(R.id.Rs);
        address = v.findViewById(R.id.addresstrack);
        status =v.findViewById(R.id.status);
        total = v.findViewById(R.id.btnn);
        customerFinalOrdersList = new ArrayList<>();
        CustomerTrackOrder();

        return v;
    }

    private void CustomerTrackOrder() {

        databaseReference = FirebaseDatabase.getInstance().getReference("CustomerFinalOrders")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                customerFinalOrdersList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    DatabaseReference data = FirebaseDatabase.getInstance().getReference("CustomerFinalOrders")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(snapshot.getKey()).child("Meals");
                    data.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot snapshot1 : dataSnapshot.getChildren()) {
                                CustomerFinalOrders customerFinalOrders = snapshot1.getValue(CustomerFinalOrders.class);
                                customerFinalOrdersList.add(customerFinalOrders);
                            }

                            if (customerFinalOrdersList.size() == 0) {
                                address.setVisibility(View.INVISIBLE);
                                total.setVisibility(View.INVISIBLE);
                            } else {
                                address.setVisibility(View.VISIBLE);
                                total.setVisibility(View.VISIBLE);
                            }
                            adapter = new CustomerTrackAdapter(getContext(), customerFinalOrdersList);
                            recyclerView.setAdapter(adapter);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("CustomerFinalOrders")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(snapshot.getKey())
                            .child("OtherInformation");
                    reference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            CustomerFinalOrders1 customerFinalOrders1 = dataSnapshot.getValue(CustomerFinalOrders1.class);
                            grandTotal.setText("R " + customerFinalOrders1.getGrandTotalPrice());
                            address.setText(customerFinalOrders1.getAddress());
                            status.setText(customerFinalOrders1.getStatus());

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
