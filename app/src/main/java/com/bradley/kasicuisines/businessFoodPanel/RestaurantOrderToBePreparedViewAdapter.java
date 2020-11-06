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

public class RestaurantOrderToBePreparedViewAdapter extends
        RecyclerView.Adapter<RestaurantOrderToBePreparedViewAdapter.ViewHolder> {

    private Context mContext;
    private List<RestaurantWaitingOrders> restaurantWaitingOrdersList;

    public RestaurantOrderToBePreparedViewAdapter(Context mContext, List<RestaurantWaitingOrders> restaurantWaitingOrdersList) {
        this.mContext = mContext;
        this.restaurantWaitingOrdersList = restaurantWaitingOrdersList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.restaurant_order_to_be_prepared_view, parent, false);
        return new RestaurantOrderToBePreparedViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        final RestaurantWaitingOrders restaurantWaitingOrders = restaurantWaitingOrdersList.get(position);
        holder.mealName.setText(restaurantWaitingOrders.getMealName());
        holder.price.setText("Price: R " + restaurantWaitingOrders.getMealPrice());
        holder.quantity.setText("× " + restaurantWaitingOrders.getMealQuantity());
        holder.totalPrice.setText("Total: R " + restaurantWaitingOrders.getTotalPrice());

    }

    @Override
    public int getItemCount() {
        return restaurantWaitingOrdersList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView mealName, price, totalPrice, quantity;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mealName = itemView.findViewById(R.id.Dname);
            price = itemView.findViewById(R.id.Dprice);
            totalPrice = itemView.findViewById(R.id.Tprice);
            quantity = itemView.findViewById(R.id.Dqty);
        }
    }
}
