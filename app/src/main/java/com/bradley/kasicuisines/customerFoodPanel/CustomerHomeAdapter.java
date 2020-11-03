package com.bradley.kasicuisines.customerFoodPanel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bradley.kasicuisines.R;
import com.bradley.kasicuisines.models.UpdateDishModel;
import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;

import java.util.List;

public class CustomerHomeAdapter extends RecyclerView.Adapter<CustomerHomeAdapter.ViewHolder> {

    private Context mcontext;
    private List<UpdateDishModel>updateDishModellist;
    DatabaseReference databaseReference;

    public CustomerHomeAdapter(Context context,List<UpdateDishModel>updateDishModellist)
    {
        this.updateDishModellist=updateDishModellist;
        this.mcontext=context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mcontext).inflate(R.layout.customer_menu_meal_item, parent, false);
        return new CustomerHomeAdapter.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        final UpdateDishModel updateDishModel = updateDishModellist.get(position);
        Glide.with(mcontext).load(updateDishModel.getImageURL()).into(holder.imageView);

        holder.Dishname.setText(updateDishModel.getMealName());
        updateDishModel.getRandomUID();
        updateDishModel.getRestaurantId();
        holder.price.setText("Price: R " + updateDishModel.getPrice());
        holder.mCurrentPosition = position;
    }

    @Override
    public int getItemCount() {
        return updateDishModellist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView Dishname,price;
        public int mCurrentPosition;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView=itemView.findViewById(R.id.menu_image);
            Dishname=itemView.findViewById(R.id.mealName);
            price=itemView.findViewById(R.id.mealPrice);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Snackbar.make(v, updateDishModellist.get(mCurrentPosition).getImageURL(),
                            Snackbar.LENGTH_LONG).show();
                }
            });
        }
    }
}
