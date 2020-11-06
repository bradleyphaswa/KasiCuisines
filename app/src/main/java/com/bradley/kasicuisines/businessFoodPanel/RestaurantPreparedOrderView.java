package com.bradley.kasicuisines.businessFoodPanel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bradley.kasicuisines.R;
import com.bradley.kasicuisines.Restaurant;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RestaurantPreparedOrderView extends AppCompatActivity {

    RecyclerView recyclerViewdish;
    private List<RestaurantFinalOrders> restaurantFinalOrdersList;
    private RestaurantPreparedOrderViewAdapter adapter;
    DatabaseReference reference;
    String RandomUID, userid;
    TextView grandtotal, address, name, number;
    LinearLayout l1;
    Button Prepared;
    private ProgressDialog progressDialog;
    private APIService apiService;
    Spinner Shipper;
    String deliveryId = "xPJIFvXZOIcd4jlgkzcVrN6btht2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_prepared_order_view);

        recyclerViewdish = findViewById(R.id.Recycle_viewOrders);
        grandtotal = findViewById(R.id.Gtotal);
        address = findViewById(R.id.Cadress);
        name = findViewById(R.id.Cname);
        number = findViewById(R.id.Cnumber);
        l1 = findViewById(R.id.linear);
        Shipper = findViewById(R.id.shipper);
        Prepared = findViewById(R.id.prepared);
        apiService = Client.getClient("https://fcm.googleapis.com/").create(APIService.class);
        progressDialog = new ProgressDialog(RestaurantPreparedOrderView.this);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        recyclerViewdish.setHasFixedSize(true);
        recyclerViewdish.setLayoutManager(new LinearLayoutManager(RestaurantPreparedOrderView.this));
        restaurantFinalOrdersList = new ArrayList<>();
        RestaurantorderdishesView();
    }

    private void RestaurantorderdishesView() {

        RandomUID = getIntent().getStringExtra("randomUID");

        reference = FirebaseDatabase.getInstance().getReference("RestaurantFinalOrders")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(RandomUID).child("Meals");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                restaurantFinalOrdersList.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    RestaurantFinalOrders restaurantFinalOrders = snapshot.getValue(RestaurantFinalOrders.class);
                    restaurantFinalOrdersList.add(restaurantFinalOrders);
                }
                if (restaurantFinalOrdersList.size() == 0) {
                    l1.setVisibility(View.INVISIBLE);

                } else {
                    l1.setVisibility(View.VISIBLE);
                    Prepared.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            progressDialog.setMessage("Please wait...");
                            progressDialog.show();

                            DatabaseReference data = FirebaseDatabase.getInstance().getReference("Restaurant")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                            data.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    final Restaurant restaurant = dataSnapshot.getValue(Restaurant.class);
                                    final String RestaurantName = restaurant.getStoreName();
                                    DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference("RestaurantFinalOrders")
                                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(RandomUID).child("Meals");
                                    databaseReference1.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                                final RestaurantFinalOrders restaurantFinalOrders = dataSnapshot1.getValue(RestaurantFinalOrders.class);
                                                HashMap<String, String> hashMap = new HashMap<>();
                                                String mealid = restaurantFinalOrders.getMealId();
                                                userid = restaurantFinalOrders.getUserId();
                                                hashMap.put("restaurantId", restaurantFinalOrders.getRestaurantId());
                                                hashMap.put("mealId", restaurantFinalOrders.getMealId());
                                                hashMap.put("mealName", restaurantFinalOrders.getMealName());
                                                hashMap.put("mealPrice", restaurantFinalOrders.getMealPrice());
                                                hashMap.put("mealQuantity", restaurantFinalOrders.getMealQuantity());
                                                hashMap.put("randomUID", RandomUID);
                                                hashMap.put("totalPrice", restaurantFinalOrders.getTotalPrice());
                                                hashMap.put("userId", restaurantFinalOrders.getUserId());
                                                FirebaseDatabase.getInstance().getReference("DeliveryShipOrders").child(deliveryId)
                                                        .child(RandomUID).child("Meals").child(mealid).setValue(hashMap);
                                            }
                                            DatabaseReference data = FirebaseDatabase.getInstance().getReference("RestaurantFinalOrders")
                                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(RandomUID).child("OtherInformation");
                                            data.addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                    final RestaurantFinalOrders1 restaurantFinalOrders1 = dataSnapshot.getValue(RestaurantFinalOrders1.class);
                                                    HashMap<String, String> hashMap1 = new HashMap<>();
                                                    String restaurantid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                                    hashMap1.put("address", restaurantFinalOrders1.getAddress());
                                                    hashMap1.put("restaurantId", restaurantid);
                                                    hashMap1.put("restaurantName", RestaurantName);
                                                    hashMap1.put("GrandTotalPrice", restaurantFinalOrders1.getGrandTotalPrice());
                                                    hashMap1.put("mobileNumber", restaurantFinalOrders1.getMobileNumber());
                                                    hashMap1.put("name", restaurantFinalOrders1.getName());
                                                    hashMap1.put("randomUID", RandomUID);
                                                    hashMap1.put("Status", "Order is Prepared");
                                                    hashMap1.put("userId", userid);
                                                    FirebaseDatabase.getInstance().getReference("DeliveryShipOrders")
                                                            .child(deliveryId).child(RandomUID).child("OtherInformation").setValue(hashMap1).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            FirebaseDatabase.getInstance().getReference("CustomerFinalOrders")
                                                                    .child(userid).child(RandomUID).child("OtherInformation").child("Status")
                                                                    .setValue("Order is Prepared...").addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                @Override
                                                                public void onSuccess(Void aVoid) {
                                                                    FirebaseDatabase.getInstance().getReference().child("Tokens").child(userid)
                                                                            .child("token").addListenerForSingleValueEvent(new ValueEventListener() {
                                                                        @Override
                                                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                                            String usertoken = dataSnapshot.getValue(String.class);
                                                                            sendNotifications(usertoken, "Estimated Time", "Your Order is Prepared", "Prepared");
                                                                        }

                                                                        @Override
                                                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                                                        }
                                                                    });
                                                                }
                                                            }).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                @Override
                                                                public void onSuccess(Void aVoid) {
                                                                    FirebaseDatabase.getInstance().getReference().child("Tokens").child(deliveryId)
                                                                            .child("token").addListenerForSingleValueEvent(new ValueEventListener() {
                                                                        @Override
                                                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                                            String usertoken = dataSnapshot.getValue(String.class);
                                                                            sendNotifications(usertoken, "New Order", "You have a New Order", "DeliveryOrder");
                                                                            progressDialog.dismiss();
                                                                            AlertDialog.Builder builder = new AlertDialog.Builder(RestaurantPreparedOrderView.this);
                                                                            builder.setMessage("Order has been sent to shipper");
                                                                            builder.setCancelable(false);
                                                                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                                                @Override
                                                                                public void onClick(DialogInterface dialog, int which) {

                                                                                    dialog.dismiss();
                                                                                    Intent b = new Intent(RestaurantPreparedOrderView.this, RestaurantPreparedOrder.class);
                                                                                    startActivity(b);
                                                                                    finish();


                                                                                }
                                                                            });
                                                                            AlertDialog alert = builder.create();
                                                                            alert.show();
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

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });


                        }
                    });

                }
                adapter = new RestaurantPreparedOrderViewAdapter(RestaurantPreparedOrderView.this, restaurantFinalOrdersList);
                recyclerViewdish.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("RestaurantFinalOrders").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(RandomUID).child("OtherInformation");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                RestaurantFinalOrders1 restaurantFinalOrders1 = dataSnapshot.getValue(RestaurantFinalOrders1.class);
                grandtotal.setText("₹ " + restaurantFinalOrders1.getGrandTotalPrice());
                address.setText(restaurantFinalOrders1.getAddress());
                name.setText(restaurantFinalOrders1.getName());
                number.setText("+91" + restaurantFinalOrders1.getMobileNumber());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void sendNotifications(String usertoken, String title, String message, String prepared) {

        Data data = new Data(title, message, prepared);
        NotificationSender sender = new NotificationSender(data, usertoken);
        apiService.sendNotification(sender).enqueue(new Callback<MyResponse>() {
            @Override
            public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
                if (response.code() == 200) {
                    if (response.body().success != 1) {
                        Toast.makeText(RestaurantPreparedOrderView.this, "Failed", Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<MyResponse> call, Throwable t) {

            }
        });
    }
}
