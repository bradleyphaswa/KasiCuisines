package com.bradley.kasicuisines.businessFoodPanel;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bradley.kasicuisines.R;

import java.util.List;

public class RestaurantPreparedOrderAdapter extends RecyclerView.Adapter<RestaurantPreparedOrderAdapter.ViewHolder> {

    private Context context;
    private List<RestaurantFinalOrders1> restaurantFinalOrders1List;

    public RestaurantPreparedOrderAdapter(Context context, List<RestaurantFinalOrders1> restaurantFinalOrders1List) {
        this.context = context;
        this.restaurantFinalOrders1List = restaurantFinalOrders1List;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.restaurant_prepared_order, parent, false);
        return new RestaurantPreparedOrderAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final RestaurantFinalOrders1 chefFinalOrders1 = restaurantFinalOrders1List.get(position);
        holder.Address.setText(chefFinalOrders1.getAddress());
        holder.grandtotalprice.setText("Grand Total: R " + chefFinalOrders1.getGrandTotalPrice());
        final String random = chefFinalOrders1.getRandomUID();
        holder.Vieworder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, RestaurantPreparedOrderView.class);
                intent.putExtra("randomUID", random);
                context.startActivity(intent);
                ((RestaurantPreparedOrder) context).finish();
            }
        });
    }

    @Override
    public int getItemCount() {
        return restaurantFinalOrders1List.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView Address, grandtotalprice;
        Button Vieworder;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            Address = itemView.findViewById(R.id.customer_address);
            grandtotalprice = itemView.findViewById(R.id.customer_totalprice);
            Vieworder = itemView.findViewById(R.id.View);
        }
    }
}
