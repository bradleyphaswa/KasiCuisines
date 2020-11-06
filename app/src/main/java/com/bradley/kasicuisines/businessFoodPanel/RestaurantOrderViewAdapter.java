package com.bradley.kasicuisines.businessFoodPanel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bradley.kasicuisines.R;

import java.util.List;

public class RestaurantOrderViewAdapter extends RecyclerView.Adapter<RestaurantOrderViewAdapter.ViewHolder> {

    private Context mContext;
    private List<RestaurantFinalOrders> restaurantFinalOrdersList;

    public RestaurantOrderViewAdapter(Context mContext, List<RestaurantFinalOrders> restaurantFinalOrdersList) {
        this.mContext = mContext;
        this.restaurantFinalOrdersList = restaurantFinalOrdersList;
    }


    @NonNull
    @Override
    public RestaurantOrderViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.restaurant_prepared_order_view, parent, false);
        return new RestaurantOrderViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final RestaurantFinalOrders chefFinalOrders = restaurantFinalOrdersList.get(position);
        holder.mealName.setText(chefFinalOrders.getMealName());
        holder.price.setText("Price: R " + chefFinalOrders.getMealPrice());
        holder.quantity.setText("× " + chefFinalOrders.getMealQuantity());
        holder.totalPrice.setText("Total: R " + chefFinalOrders.getTotalPrice());
    }


    @Override
    public int getItemCount() {
        return restaurantFinalOrdersList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView mealName, price, totalPrice, quantity;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mealName = itemView.findViewById(R.id.Cdishname);
            price = itemView.findViewById(R.id.Cdishprice);
            totalPrice = itemView.findViewById(R.id.Ctotalprice);
            quantity = itemView.findViewById(R.id.Cdishqty);
        }
    }
}
