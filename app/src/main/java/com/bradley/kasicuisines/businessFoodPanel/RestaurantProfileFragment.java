package com.bradley.kasicuisines.businessFoodPanel;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bradley.kasicuisines.R;

public class RestaurantProfileFragment extends Fragment {

    Button postMeal;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_restaurant_profile, null);
        getActivity().setTitle("Post Meal");

        postMeal = v.findViewById(R.id.post_meal);

        postMeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), Restaurant_postMeal.class));
            }
        });
        return v;

    }
}
