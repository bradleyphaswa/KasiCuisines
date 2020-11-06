package com.bradley.kasicuisines.deliveryFoodPanel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bradley.kasicuisines.R;

import java.util.List;

public class DeliveryPendingOrderViewAdapter extends RecyclerView.Adapter<DeliveryPendingOrderViewAdapter.ViewHolder> {

    private Context mContext;
    private List<DeliveryShipOrders> deliveryShipOrdersList;

    public DeliveryPendingOrderViewAdapter(Context mContext, List<DeliveryShipOrders> deliveryShipOrdersList) {
        this.mContext = mContext;
        this.deliveryShipOrdersList = deliveryShipOrdersList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.delivery_pending_order, parent, false);
        return new DeliveryPendingOrderViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final DeliveryShipOrders deliveryShipOrders = deliveryShipOrdersList.get(position);
        holder.mealName.setText(deliveryShipOrders.getMealName());
        holder.price.setText("Price: R " + deliveryShipOrders.getMealPrice());
        holder.quantity.setText("× " + deliveryShipOrders.getMealQuantity());
        holder.totalprice.setText("Total: R " + deliveryShipOrders.getTotalPrice());
    }

    @Override
    public int getItemCount() {
        return deliveryShipOrdersList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView mealName, price, totalprice, quantity;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mealName = itemView.findViewById(R.id.Dish1);
            price = itemView.findViewById(R.id.Price1);
            totalprice = itemView.findViewById(R.id.Total1);
            quantity = itemView.findViewById(R.id.Quantity1);
        }
    }
}
