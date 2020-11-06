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

public class RestaurantOrderTobePreparedAdapter extends RecyclerView.Adapter<RestaurantOrderTobePreparedAdapter.ViewHolder> {

    private Context mContext;
    private List<RestaurantWaitingOrders1> restaurantWaitingOrders1List;

    public RestaurantOrderTobePreparedAdapter(Context mContext, List<RestaurantWaitingOrders1> restaurantWaitingOrders1List) {
        this.mContext = mContext;
        this.restaurantWaitingOrders1List = restaurantWaitingOrders1List;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.restaurant_order_to_be_prepared, parent, false);
        return new RestaurantOrderTobePreparedAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        RestaurantWaitingOrders1 chefWaitingOrders1 = restaurantWaitingOrders1List.get(position);
        holder.address.setText(chefWaitingOrders1.getAddress());
        holder.grandTotalPrice.setText("Grand Total: R " + chefWaitingOrders1.getGrandTotalPrice());
        final String random = chefWaitingOrders1.getRandomUID();
        holder.viewHolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, RestaurantOrderToBePrepareView.class);
                intent.putExtra("randomUID", random);
                mContext.startActivity(intent);
                ((RestaurantOrderToBePrepared) mContext).finish();
            }
        });

    }

    @Override
    public int getItemCount() {
        return restaurantWaitingOrders1List.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView address, grandTotalPrice;
        Button viewHolder;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            address = itemView.findViewById(R.id.cust_address);
            grandTotalPrice = itemView.findViewById(R.id.Grandtotalprice);
            viewHolder = itemView.findViewById(R.id.View_order);
        }
    }
}
