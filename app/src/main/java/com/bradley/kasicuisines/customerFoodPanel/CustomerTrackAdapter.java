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

public class CustomerTrackAdapter extends RecyclerView.Adapter<CustomerTrackAdapter.ViewHolder>{

    private Context mContext;
    private List<CustomerFinalOrders> customerFinalOrdersList;

    public CustomerTrackAdapter(Context mContext, List<CustomerFinalOrders> customerFinalOrdersList) {
        this.mContext = mContext;
        this.customerFinalOrdersList = customerFinalOrdersList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.customer_trackorder, parent, false);
        return new CustomerTrackAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final CustomerFinalOrders customerFinalOrders = customerFinalOrdersList.get(position);
        holder.mealName.setText(customerFinalOrders.getMealName());
        holder.quantity.setText(customerFinalOrders.getMealQuantity() + "× ");
        holder.totalPrice.setText("R " + customerFinalOrders.getTotalPrice());
    }

    @Override
    public int getItemCount() {
        return customerFinalOrdersList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mealName, quantity, totalPrice;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mealName = itemView.findViewById(R.id.dishnm);
            quantity = itemView.findViewById(R.id.dishqty);
            totalPrice = itemView.findViewById(R.id.totRS);
        }
    }
}
