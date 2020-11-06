package com.bradley.kasicuisines.businessFoodPanel;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bradley.kasicuisines.R;
import com.bradley.kasicuisines.ReusableCode.ReusableCodeForAll;
import com.bradley.kasicuisines.SendNotification.APIService;
import com.bradley.kasicuisines.SendNotification.Client;
import com.bradley.kasicuisines.SendNotification.Data;
import com.bradley.kasicuisines.SendNotification.MyResponse;
import com.bradley.kasicuisines.SendNotification.NotificationSender;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RestaurantPendingOrdersAdapter extends RecyclerView.Adapter<RestaurantPendingOrdersAdapter.ViewHolder> {

    private Context mContext;
    private List<RestaurantPendingOrders1> restaurantPendingOrders1List;
    private APIService apiService;
    String userid, dishid;

    public RestaurantPendingOrdersAdapter(Context mContext, List<RestaurantPendingOrders1> restaurantPendingOrders1List) {
        this.mContext = mContext;
        this.restaurantPendingOrders1List = restaurantPendingOrders1List;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.restaurant_order, parent, false);
        apiService = Client.getClient("https://fcm.googleapis.com/").create(APIService.class);
        return new RestaurantPendingOrdersAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final RestaurantPendingOrders1 restaurantPendingOrders1 = restaurantPendingOrders1List.get(position);
        holder.address.setText(restaurantPendingOrders1.getAddress());
        holder.grandtotalprice.setText("GrandTotal: R " + restaurantPendingOrders1.getGrandTotalPrice());
        final String random = restaurantPendingOrders1.getRandomUID();
        holder.viewOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, Restaurant_Order_Meals.class);
                intent.putExtra("randomUID", random);
                mContext.startActivity(intent);
            }
        });

        holder.accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("RestaurantPendingOrders")
                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(random).child("Meals");
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            final RestaurantPendingOrders restaurantPendingOrders = snapshot.getValue(RestaurantPendingOrders.class);
                            HashMap<String, String> hashMap = new HashMap<>();
                            String restaurantid = restaurantPendingOrders.getRestaurantId();
                            String dishid = restaurantPendingOrders.getMealId();
                            hashMap.put("restaurantId", restaurantPendingOrders.getRestaurantId());
                            hashMap.put("mealId", restaurantPendingOrders.getMealId());
                            hashMap.put("mealName", restaurantPendingOrders.getMealName());
                            hashMap.put("mealPrice", restaurantPendingOrders.getPrice());
                            hashMap.put("mealQuantity", restaurantPendingOrders.getMealQuantity());
                            hashMap.put("randomUID", random);
                            hashMap.put("totalPrice", restaurantPendingOrders.getTotalPrice());
                            hashMap.put("userId", restaurantPendingOrders.getUserId());
                            FirebaseDatabase.getInstance().getReference("RestaurantPaymentOrders")
                                    .child(restaurantid).child(random).child("Meals").child(dishid).setValue(hashMap);
                        }
                        DatabaseReference data = FirebaseDatabase.getInstance().getReference("RestaurantPendingOrders")
                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(random).child("OtherInformation");
                        data.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                RestaurantPendingOrders1 restaurantPendingOrders1 = dataSnapshot.getValue(RestaurantPendingOrders1.class);
                                HashMap<String, String> hashMap1 = new HashMap<>();
                                hashMap1.put("address", restaurantPendingOrders1.getAddress());
                                hashMap1.put("GrandTotalPrice", restaurantPendingOrders1.getGrandTotalPrice());
                                hashMap1.put("mobileNumber", restaurantPendingOrders1.getMobileNumber());
                                hashMap1.put("name", restaurantPendingOrders1.getName());
                                hashMap1.put("note",restaurantPendingOrders1.getNote());
                                hashMap1.put("randomUID", random);
                                FirebaseDatabase.getInstance().getReference("RestaurantPaymentOrders")
                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(random)
                                        .child("OtherInformation").setValue(hashMap1).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        DatabaseReference Reference = FirebaseDatabase.getInstance().getReference("RestaurantPendingOrders")
                                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(random).child("Meals");
                                        Reference.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                                    final RestaurantPendingOrders restaurantPendingOrders = snapshot.getValue(RestaurantPendingOrders.class);
                                                    HashMap<String, String> hashMap2 = new HashMap<>();
                                                    userid = restaurantPendingOrders.getUserId();
                                                    dishid = restaurantPendingOrders.getMealId();
                                                    hashMap2.put("restaurantId", restaurantPendingOrders.getRestaurantId());
                                                    hashMap2.put("mealId", restaurantPendingOrders.getMealId());
                                                    hashMap2.put("mealName", restaurantPendingOrders.getMealName());
                                                    hashMap2.put("mealPrice", restaurantPendingOrders.getPrice());
                                                    hashMap2.put("mealQuantity", restaurantPendingOrders.getMealQuantity());
                                                    hashMap2.put("randomUID", random);
                                                    hashMap2.put("totalPrice", restaurantPendingOrders.getTotalPrice());
                                                    hashMap2.put("userId", restaurantPendingOrders.getUserId());
                                                    FirebaseDatabase.getInstance().getReference("CustomerPaymentOrders")
                                                            .child(userid).child(random).child("Meals").child(dishid).setValue(hashMap2);
                                                }
                                                DatabaseReference dataa = FirebaseDatabase.getInstance().getReference("RestaurantPendingOrders")
                                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(random).child("OtherInformation");
                                                dataa.addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                        RestaurantPendingOrders1 restaurantPendingOrders1 = dataSnapshot.getValue(RestaurantPendingOrders1.class);
                                                        HashMap<String, String> hashMap3 = new HashMap<>();
                                                        hashMap3.put("address", restaurantPendingOrders1.getAddress());
                                                        hashMap3.put("GrandTotalPrice", restaurantPendingOrders1.getGrandTotalPrice());
                                                        hashMap3.put("mobileNumber", restaurantPendingOrders1.getMobileNumber());
                                                        hashMap3.put("name", restaurantPendingOrders1.getName());
                                                        hashMap3.put("note",restaurantPendingOrders1.getNote());
                                                        hashMap3.put("randomUID", random);
                                                        FirebaseDatabase.getInstance().getReference("CustomerPaymentOrders")
                                                                .child(userid).child(random).child("OtherInformation").setValue(hashMap3).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {

                                                                FirebaseDatabase.getInstance().getReference("CustomerPendingOrders")
                                                                        .child(userid).child(random).child("Meals").removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                    @Override
                                                                    public void onComplete(@NonNull Task<Void> task) {

                                                                        FirebaseDatabase.getInstance().getReference("CustomerPendingOrders")
                                                                                .child(userid).child(random).child("OtherInformation").removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                            @Override
                                                                            public void onComplete(@NonNull Task<Void> task) {

                                                                                FirebaseDatabase.getInstance().getReference("RestaurantPendingOrders")
                                                                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(random).child("Meals")
                                                                                        .removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                    @Override
                                                                                    public void onComplete(@NonNull Task<Void> task) {

                                                                                        FirebaseDatabase.getInstance().getReference("RestaurantPendingOrders")
                                                                                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(random)
                                                                                                .child("OtherInformation").removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                                            @Override
                                                                                            public void onSuccess(Void aVoid) {
                                                                                                FirebaseDatabase.getInstance().getReference().child("Tokens").child(userid).child("token")
                                                                                                        .addListenerForSingleValueEvent(new ValueEventListener() {
                                                                                                    @Override
                                                                                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                                                                        String usertoken = dataSnapshot.getValue(String.class);
                                                                                                        sendNotifications(usertoken, "Order Accepted", "Your Order has been Accepted by the Restaurant, Now make Payment for Order", "Payment");
                                                                                                        ReusableCodeForAll.ShowAlert(mContext,"","Wait for the Customer to make Payment");

                                                                                                    }

                                                                                                    @Override
                                                                                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                                                                                    }
                                                                                                });
                                                                                            }
                                                                                        });
                                                                                    }
                                                                                });

                                                                            }
                                                                        });
                                                                    }
                                                                });
                                                            }
                                                        });
                                                    }

                                                    @Override
                                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                                    }
                                                });

                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }
                                        });
                                    }
                                });

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


            }
        });

        holder.reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseReference Reference = FirebaseDatabase.getInstance().getReference("RestaurantPendingOrders")
                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(random).child("Meals");
                Reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            final RestaurantPendingOrders restaurantPendingOrders = snapshot.getValue(RestaurantPendingOrders.class);
                            userid = restaurantPendingOrders.getUserId();
                            dishid = restaurantPendingOrders.getMealId();
                        }
                        FirebaseDatabase.getInstance().getReference().child("Tokens")
                                .child(userid).child("token").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                String usertoken = dataSnapshot.getValue(String.class);
                                sendNotifications(usertoken, "Order Rejected", "Your Order has been Rejected by the Restaurant due to some Circumstances. Please contact your Restaurant.", "Home");
                                FirebaseDatabase.getInstance().getReference("CustomerPendingOrders")
                                        .child(userid).child(random).child("Meals").removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        FirebaseDatabase.getInstance().getReference("CustomerPendingOrders").child(userid).child(random)
                                                .child("OtherInformation").removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {

                                                FirebaseDatabase.getInstance().getReference("RestaurantPendingOrders")
                                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(random)
                                                        .child("Meals").removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {

                                                        FirebaseDatabase.getInstance().getReference("RestaurantPendingOrders")
                                                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(random).child("OtherInformation")
                                                                .removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                                            @Override
                                                            public void onSuccess(Void aVoid) {
                                                                FirebaseDatabase.getInstance().getReference("AlreadyOrdered")
                                                                        .child(userid).child("isOrdered").setValue("false");
                                                            }
                                                        });

                                                    }
                                                });

                                            }
                                        });

                                    }
                                });

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


            }
        });
    }

    private void sendNotifications(String usertoken, String title, String message, String order) {

        Data data = new Data(title, message, order);
        NotificationSender sender = new NotificationSender(data, usertoken);
        apiService.sendNotification(sender).enqueue(new Callback<MyResponse>() {
            @Override
            public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
                if (response.code() == 200) {
                    if (response.body().success != 1) {
                        Toast.makeText(mContext, "Failed", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<MyResponse> call, Throwable t) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return restaurantPendingOrders1List.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView address, grandtotalprice;
        Button viewOrder, accept, reject;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            address = itemView.findViewById(R.id.AD);
            grandtotalprice = itemView.findViewById(R.id.TP);
            viewOrder = itemView.findViewById(R.id.vieww);
            accept = itemView.findViewById(R.id.accept);
            reject = itemView.findViewById(R.id.reject);

        }
    }
}