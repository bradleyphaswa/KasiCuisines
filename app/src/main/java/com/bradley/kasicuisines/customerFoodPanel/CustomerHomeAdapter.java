package com.bradley.kasicuisines.customerFoodPanel;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bradley.kasicuisines.R;
import com.bradley.kasicuisines.models.UpdateDishModel;
import com.bumptech.glide.Glide;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.firebase.database.DatabaseReference;

import java.util.List;

public class CustomerHomeAdapter extends RecyclerView.Adapter<CustomerHomeAdapter.ViewHolder> {

    private Context mContext;
    private List<UpdateDishModel>updateDishModellist;
    DatabaseReference databaseReference;

    public CustomerHomeAdapter(Context context,List<UpdateDishModel>updateDishModellist)
    {
        this.updateDishModellist=updateDishModellist;
        this.mContext =context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.customer_menu_meal_item, parent, false);
        return new CustomerHomeAdapter.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        final UpdateDishModel updateDishModel = updateDishModellist.get(position);
        Glide.with(mContext).load(updateDishModel.getImageURL()).into(holder.imageView);

        holder.Dishname.setText(updateDishModel.getMealName());
        updateDishModel.getRandomUID();
        updateDishModel.getRestaurantId();
        holder.price.setText("Price: R " + updateDishModel.getPrice());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, OrderMeal.class);
                intent.putExtra("foodMenu", updateDishModel.getRandomUID());
                intent.putExtra("restaurantId", updateDishModel.getRestaurantId());
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return updateDishModellist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView Dishname,price;
        ElegantNumberButton addItem;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView=itemView.findViewById(R.id.menu_image);
            Dishname=itemView.findViewById(R.id.mealName);
            price=itemView.findViewById(R.id.mealPrice);
            addItem = itemView.findViewById(R.id.number_button);
        }
    }
}
