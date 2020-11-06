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

public class PayableOrderAdapter extends RecyclerView.Adapter<PayableOrderAdapter.ViewHolder>{

    private Context context;
    private List<CustomerPaymentOrders> customerPaymentOrdersList;

    public PayableOrderAdapter(Context context, List<CustomerPaymentOrders> customerPendingOrdersList) {
        this.customerPaymentOrdersList = customerPendingOrdersList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.customer_payable_order, parent, false);
        return new PayableOrderAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final CustomerPaymentOrders customerPaymentOrders = customerPaymentOrdersList.get(position);
        holder.mealName.setText(customerPaymentOrders.getMealName());
        holder.price.setText("Price: R " + customerPaymentOrders.getMealPrice());
        holder.quantity.setText("× " + customerPaymentOrders.getMealQuantity());
        holder.totalPrice.setText("Total: R " + customerPaymentOrders.getTotalPrice());
    }

    @Override
    public int getItemCount() {
        return customerPaymentOrdersList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView mealName, price, quantity, totalPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mealName = itemView.findViewById(R.id.dish);
            price = itemView.findViewById(R.id.pri);
            quantity = itemView.findViewById(R.id.qt);
            totalPrice = itemView.findViewById(R.id.Tot);
        }
    }
}
