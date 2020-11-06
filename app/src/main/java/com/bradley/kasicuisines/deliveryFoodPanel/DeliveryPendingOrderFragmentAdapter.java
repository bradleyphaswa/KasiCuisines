package com.bradley.kasicuisines.deliveryFoodPanel;

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

public class DeliveryPendingOrderFragmentAdapter extends RecyclerView.Adapter<DeliveryPendingOrderFragmentAdapter.ViewHolder> {

    private Context context;
    private List<DeliveryShipOrders1> deliveryShipOrders1List;
    private APIService apiService;
    String restaurantId;

    public DeliveryPendingOrderFragmentAdapter(Context context, List<DeliveryShipOrders1> deliveryShipOrders1List) {
        this.context = context;
        this.deliveryShipOrders1List = deliveryShipOrders1List;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.delivery_pending_orders, parent, false);
        apiService = Client.getClient("https://fcm.googleapis.com/").create(APIService.class);
        return new DeliveryPendingOrderFragmentAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final DeliveryShipOrders1 deliveryShipOrders1 = deliveryShipOrders1List.get(position);
        holder.Address.setText(deliveryShipOrders1.getAddress());
        holder.mobilenumber.setText("+27" + deliveryShipOrders1.getMobileNumber());
        holder.grandtotalprice.setText("Grand Total: R " + deliveryShipOrders1.getGrandTotalPrice());
        final String randomuid = deliveryShipOrders1.getRandomUID();
        holder.Vieworder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, DeliveryPendingOrderView.class);
                intent.putExtra("random", randomuid);
                context.startActivity(intent);
            }
        });

        holder.Accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("DeliveryShipOrders").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(randomuid).child("Meals");
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            DeliveryShipOrders deliveryShipOrderss = snapshot.getValue(DeliveryShipOrders.class);
                            HashMap<String, String> hashMap = new HashMap<>();
                            String mealid = deliveryShipOrderss.getMealId();
                            restaurantId = deliveryShipOrderss.getRestaurantId();
                            hashMap.put("restaurantId", deliveryShipOrderss.getRestaurantId());
                            hashMap.put("mealId", deliveryShipOrderss.getMealId());
                            hashMap.put("mealName", deliveryShipOrderss.getMealName());
                            hashMap.put("mealPrice", deliveryShipOrderss.getMealPrice());
                            hashMap.put("mealQuantity", deliveryShipOrderss.getMealQuantity());
                            hashMap.put("randomUID", deliveryShipOrderss.getRandomUID());
                            hashMap.put("totalPrice", deliveryShipOrderss.getTotalPrice());
                            hashMap.put("userId", deliveryShipOrderss.getUserId());
                            FirebaseDatabase.getInstance().getReference("DeliveryShipFinalOrders")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(randomuid).child("Meals")
                                    .child(mealid).setValue(hashMap);

                        }

                        DatabaseReference data = FirebaseDatabase.getInstance().getReference("DeliveryShipOrders")
                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(randomuid).child("OtherInformation");
                        data.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                DeliveryShipOrders1 deliveryShipOrders11 = dataSnapshot.getValue(DeliveryShipOrders1.class);
                                HashMap<String, String> hashMap1 = new HashMap<>();
                                hashMap1.put("address", deliveryShipOrders11.getAddress());
                                hashMap1.put("restaurantId", deliveryShipOrders11.getRestaurantId());
                                hashMap1.put("restaurantName", deliveryShipOrders11.getRestaurantName());
                                hashMap1.put("GrandTotalPrice", deliveryShipOrders11.getGrandTotalPrice());
                                hashMap1.put("mobileNumber", deliveryShipOrders11.getMobileNumber());
                                hashMap1.put("name", deliveryShipOrders11.getName());
                                hashMap1.put("randomUID", randomuid);
                                hashMap1.put("userId", deliveryShipOrders11.getUserId());
                                FirebaseDatabase.getInstance().getReference("DeliveryShipFinalOrders")
                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(randomuid)
                                        .child("OtherInformation").setValue(hashMap1).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        FirebaseDatabase.getInstance().getReference("DeliveryShipOrders")
                                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(randomuid)
                                                .child("Meals").removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                FirebaseDatabase.getInstance().getReference("DeliveryShipOrders")
                                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                        .child(randomuid).child("OtherInformation").removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        FirebaseDatabase.getInstance().getReference().child("Tokens")
                                                                .child(restaurantId).child("token").addListenerForSingleValueEvent(new ValueEventListener() {
                                                            @Override
                                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                                String usertoken = dataSnapshot.getValue(String.class);
                                                                sendNotifications(usertoken, "Order Accepted", "Your Order has been Accepted by the Delivery person", "AcceptOrder");
                                                                ReusableCodeForAll.ShowAlert(context, "", "Now you can check orders which are to be shipped");

                                                            }

                                                            @Override
                                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                                            }
                                                        });
                                                    }
                                                }).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        FirebaseDatabase.getInstance().getReference("ChefFinalOrders")
                                                                .child(restaurantId).child(randomuid).child("Meals").removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                FirebaseDatabase.getInstance().getReference("ChefFinalOrders")
                                                                        .child(restaurantId).child(randomuid).child("OtherInformation").removeValue();
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

        holder.Reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("DeliveryShipOrders")
                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(randomuid).child("Meals");
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                            DeliveryShipOrders deliveryShipOrders = dataSnapshot1.getValue(DeliveryShipOrders.class);
                            restaurantId = deliveryShipOrders.getRestaurantId();
                        }

                        FirebaseDatabase.getInstance().getReference().child("Tokens").child(restaurantId)
                                .child("token").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                String usertoken = dataSnapshot.getValue(String.class);
                                sendNotifications(usertoken, "Order Rejected", "Your Order has been Rejected by the Delivery person", "RejectOrder");
                                FirebaseDatabase.getInstance().getReference("DeliveryShipOrders")
                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .child(randomuid).child("Meals").removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        FirebaseDatabase.getInstance().getReference("DeliveryShipOrders")
                                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                .child(randomuid).child("OtherInformation").removeValue();
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
                        Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
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
        return deliveryShipOrders1List.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView Address, grandtotalprice, mobilenumber;
        Button Vieworder, Accept, Reject;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Address = itemView.findViewById(R.id.ad1);
            mobilenumber = itemView.findViewById(R.id.MB1);
            grandtotalprice = itemView.findViewById(R.id.TP1);
            Vieworder = itemView.findViewById(R.id.view1);
            Accept = itemView.findViewById(R.id.accept1);
            Reject = itemView.findViewById(R.id.reject1);
        }
    }
}
