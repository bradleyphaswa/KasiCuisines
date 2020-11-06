package com.bradley.kasicuisines.customerFoodPanel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bradley.kasicuisines.R;

import java.util.List;

public class PendingOrdersAdapter extends RecyclerView.Adapter<PendingOrdersAdapter.ViewHolder> {

    private Context mContext;
    private List<CustomerPendingOrders> customerPendingOrdersList;

    public PendingOrdersAdapter(Context mContext, List<CustomerPendingOrders> customerPendingOrdersList) {
        this.mContext = mContext;
        this.customerPendingOrdersList = customerPendingOrdersList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.pending_order_meals, parent, false);
        return new PendingOrdersAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final CustomerPendingOrders customerPendingOrders = customerPendingOrdersList.get(position);
        holder.mealName.setText(customerPendingOrders.getMealName());
        holder.price.setText("Price: R " + customerPendingOrders.getMealPrice());
        holder.quantity.setText("× " + customerPendingOrders.getMealQuantity());
        holder.totalPrice.setText("Total: R " + customerPendingOrders.getTotalPrice());

    }

    @Override
    public int getItemCount() {
        return customerPendingOrdersList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView mealName, price, quantity, totalPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mealName = itemView.findViewById(R.id.Dishh);
            price = itemView.findViewById(R.id.pricee);
            quantity = itemView.findViewById(R.id.qtyy);
            totalPrice = itemView.findViewById(R.id.total);
        }
    }
}
