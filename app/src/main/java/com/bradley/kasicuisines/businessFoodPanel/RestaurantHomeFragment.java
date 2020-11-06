package com.bradley.kasicuisines.businessFoodPanel;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bradley.kasicuisines.MainMenu;
import com.bradley.kasicuisines.R;
import com.bradley.kasicuisines.Restaurant;
import com.bradley.kasicuisines.models.UpdateDishModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RestaurantHomeFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private List<UpdateDishModel> updateDishModelList;
    private RestaurantHomeAdapter adapter;
    DatabaseReference data;
    private String province, city, suburb;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_restaurant_home, null);

        getActivity().setTitle("Home");
        setHasOptionsMenu(true);

        mRecyclerView =(RecyclerView) v.findViewById(R.id.b_recycler_menu);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        updateDishModelList = new ArrayList<>();

        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        data = FirebaseDatabase.getInstance().getReference("Restaurant").child(userId);
        data.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Restaurant restaurant = snapshot.getValue(Restaurant.class);
                province = restaurant.getProvince();
                city = restaurant.getCity();
                suburb = restaurant.getSuburb();
                restaurantDishes();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }


        });


        return v;

    }

    private void restaurantDishes() {

        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("FoodDetails")
                .child(province).child(city).child(suburb).child(userId);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    UpdateDishModel updateDishModel = snapshot.getValue(UpdateDishModel.class);
                    Log.d("List", updateDishModelList.toString());
                    updateDishModelList.add(updateDishModel);
                }
                adapter = new RestaurantHomeAdapter(getContext(), updateDishModelList);
                mRecyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();

            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }


        });


    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.logout, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        if(id == R.id.logout) {
            logout();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void logout() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(getActivity(), MainMenu.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
