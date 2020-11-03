package com.bradley.kasicuisines.businessFoodPanel;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bradley.kasicuisines.R;
import com.bradley.kasicuisines.models.UpdateDishModel;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;

import java.util.List;

public class BusinessHomeAdapter extends RecyclerView.Adapter<BusinessHomeAdapter.ViewHolder> {

    private Context mcontext;
    private List<UpdateDishModel>updateDishModellist;
    DatabaseReference databaseReference;

    public BusinessHomeAdapter(Context context,List<UpdateDishModel>updateDishModellist)
    {
        this.updateDishModellist=updateDishModellist;
        this.mcontext=context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mcontext).inflate(R.layout.business_menu_delete_update, parent, false);
        return new BusinessHomeAdapter.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull BusinessHomeAdapter.ViewHolder holder, int position) {

        final UpdateDishModel updateDishModel=updateDishModellist.get(position);

        holder.mealName.setText(updateDishModel.getMealName());
        updateDishModel.getRandomUID();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mcontext, UpdateDelete_Meal.class);
                intent.putExtra("updateDeleteMeal", updateDishModel.getRandomUID());
                mcontext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return updateDishModellist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView mealName;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mealName = itemView.findViewById(R.id.card_meal_name);

        }
    }
 }
