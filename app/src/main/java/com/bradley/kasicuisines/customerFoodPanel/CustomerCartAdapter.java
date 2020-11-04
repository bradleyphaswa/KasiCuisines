package com.bradley.kasicuisines.customerFoodPanel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bradley.kasicuisines.R;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.List;

public class CustomerCartAdapter extends RecyclerView.Adapter<CustomerCartAdapter.ViewHolder> {

    private Context mContext;
    private List<Cart> cartModelList;
    static int total = 0;

    public CustomerCartAdapter(Context context, List<Cart> cartModelList) {
        this.cartModelList = cartModelList;
        this.mContext = context;
        total = 0;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.cart_place_order, parent, false);
        return new CustomerCartAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final Cart cart = cartModelList.get(position);
        holder.mealName.setText(cart.getMealName());
        holder.price.setText("Price: R "+cart.getPrice());
        holder.quantity.setText("× " + cart.getMealQuantity());
        holder.total.setText("Total: R " + cart.getTotalPrice());
        total += Double.parseDouble(cart.getTotalPrice());
        holder.elegantNumberButton.setNumber(cart.getMealQuantity());
        final double mealPrice = Double.parseDouble(cart.getPrice());

        holder.elegantNumberButton.setOnValueChangeListener(new ElegantNumberButton.OnValueChangeListener() {
            @Override
            public void onValueChange(ElegantNumberButton view, int oldValue, int newValue) {
                int num = newValue;
                double totalPrice = num * mealPrice;
                if (num != 0) {
                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put("mealId", cart.getMealId());
                    hashMap.put("mealName", cart.getMealName());
                    hashMap.put("mealQuantity", String.valueOf(num));
                    hashMap.put("price", String.valueOf(mealPrice));
                    hashMap.put("totalPrice", String.valueOf(totalPrice));
                    hashMap.put("restaurantId",cart.getRestaurantId());

                    FirebaseDatabase.getInstance().getReference("Cart").child("CartItems")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(cart.getMealId()).setValue(hashMap);
                } else {
                    FirebaseDatabase.getInstance().getReference("Cart").child("CartItems")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(cart.getMealId()).removeValue();
                }
            }
        });
        CustomerCartFragment.grandt.setText("Grand Total: R " + total);
        FirebaseDatabase.getInstance().getReference("Cart").child("GrandTotal")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("GrandTotal").setValue(String.valueOf(total));

    }

    @Override
    public int getItemCount() {
        return cartModelList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView mealName, price, quantity, total;
        ElegantNumberButton elegantNumberButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mealName = itemView.findViewById(R.id.txt_cart_mealName);
            price = itemView.findViewById(R.id.txt_cart_price);
            quantity = itemView.findViewById(R.id.txt_cart_qty);
            total = itemView.findViewById(R.id.txt_cart_total);
            elegantNumberButton = itemView.findViewById(R.id.cart_elegant_button);
        }
    }
}
