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

public class RestaurantOrderMealAdapter extends RecyclerView.Adapter<RestaurantOrderMealAdapter.ViewHolder> {

    private Context mContext;
    private List<RestaurantPendingOrders> restaurantPendingOrdersList;

    public RestaurantOrderMealAdapter(Context mContext, List<RestaurantPendingOrders> restaurantPendingOrdersList) {
        this.mContext = mContext;
        this.restaurantPendingOrdersList = restaurantPendingOrdersList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.restaurant_order_meals, parent, false);
        return new RestaurantOrderMealAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final RestaurantPendingOrders restaurantPendingOrders = restaurantPendingOrdersList.get(position);
        holder.mealName.setText(restaurantPendingOrders.getMealName());
        holder.price.setText("Price: R " + restaurantPendingOrders.getPrice());
        holder.quantity.setText("× " + restaurantPendingOrders.getMealQuantity());
        holder.totalPrice.setText("Total: R " + restaurantPendingOrders.getTotalPrice());
    }

    @Override
    public int getItemCount() {
        return restaurantPendingOrdersList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView mealName, price, totalPrice, quantity;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mealName = itemView.findViewById(R.id.DN);
            price = itemView.findViewById(R.id.PR);
            totalPrice = itemView.findViewById(R.id.TR);
            quantity = itemView.findViewById(R.id.QY);
        }
    }
}
