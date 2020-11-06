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
import android.widget.TextView;
import android.widget.Toast;

import com.bradley.kasicuisines.R;
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

public class RestaurantOrderToBePrepareView extends AppCompatActivity {

    RecyclerView recyclerViewMeal;
    private List<RestaurantWaitingOrders> restaurantWaitingOrdersList;
    private RestaurantOrderToBePreparedViewAdapter adapter;
    DatabaseReference reference;
    String RandomUID, userid;
    TextView grandtotal, note, address, name, number;
    LinearLayout l1;
    Button Preparing;
    private ProgressDialog progressDialog;
    private APIService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_order_to_be_prepare_view);

        recyclerViewMeal = findViewById(R.id.Recycle_viewOrder);
        grandtotal = findViewById(R.id.rands);
        note = findViewById(R.id.NOTE);
        address = findViewById(R.id.ad);
        name = findViewById(R.id.nm);
        number = findViewById(R.id.num);
        l1 = findViewById(R.id.button1);
        Preparing = findViewById(R.id.preparing);
        apiService = Client.getClient("https://fcm.googleapis.com/").create(APIService.class);
        progressDialog = new ProgressDialog(RestaurantOrderToBePrepareView.this);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        recyclerViewMeal.setHasFixedSize(true);
        recyclerViewMeal.setLayoutManager(new LinearLayoutManager(RestaurantOrderToBePrepareView.this));
        restaurantWaitingOrdersList = new ArrayList<>();
        RestaurantorderdishesView();
    }

    private void RestaurantorderdishesView() {
        RandomUID = getIntent().getStringExtra("randomUID");

        reference = FirebaseDatabase.getInstance().getReference("RestaurantWaitingOrders")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(RandomUID).child("Meals");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                restaurantWaitingOrdersList.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    RestaurantWaitingOrders restaurantWaitingOrders = snapshot.getValue(RestaurantWaitingOrders.class);
                    restaurantWaitingOrdersList.add(restaurantWaitingOrders);
                }
                if (restaurantWaitingOrdersList.size() == 0) {
                    l1.setVisibility(View.INVISIBLE);

                } else {
                    l1.setVisibility(View.VISIBLE);
                    Preparing.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            progressDialog.setMessage("Please wait...");
                            progressDialog.show();

                            DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference("RestaurantWaitingOrders")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(RandomUID).child("Meals");
                            databaseReference1.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                        final RestaurantWaitingOrders restaurantWaitingOrders = dataSnapshot1.getValue(RestaurantWaitingOrders.class);
                                        HashMap<String, String> hashMap = new HashMap<>();
                                        String dishid = restaurantWaitingOrders.getMealId();
                                        userid = restaurantWaitingOrders.getUserId();
                                        hashMap.put("restaurantId", restaurantWaitingOrders.getRestaurantId());
                                        hashMap.put("mealId", restaurantWaitingOrders.getMealId());
                                        hashMap.put("mealName", restaurantWaitingOrders.getMealName());
                                        hashMap.put("mealPrice", restaurantWaitingOrders.getMealPrice());
                                        hashMap.put("mealQuantity", restaurantWaitingOrders.getMealQuantity());
                                        hashMap.put("randomUID", RandomUID);
                                        hashMap.put("totalPrice", restaurantWaitingOrders.getTotalPrice());
                                        hashMap.put("userId", restaurantWaitingOrders.getUserId());
                                        FirebaseDatabase.getInstance().getReference("RestaurantFinalOrders")
                                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(RandomUID).child("Meals").child(dishid).setValue(hashMap);
                                    }
                                    DatabaseReference data = FirebaseDatabase.getInstance().getReference("RestaurantWaitingOrders")
                                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(RandomUID).child("OtherInformation");
                                    data.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            final RestaurantWaitingOrders1 restaurantWaitingOrders1 = dataSnapshot.getValue(RestaurantWaitingOrders1.class);
                                            HashMap<String, String> hashMap1 = new HashMap<>();
                                            hashMap1.put("address", restaurantWaitingOrders1.getAddress());
                                            hashMap1.put("GrandTotalPrice", restaurantWaitingOrders1.getGrandTotalPrice());
                                            hashMap1.put("mobileNumber", restaurantWaitingOrders1.getMobileNumber());
                                            hashMap1.put("name", restaurantWaitingOrders1.getName());
                                            hashMap1.put("randomUID", RandomUID);
                                            hashMap1.put("Status", "Restaurant is preparing your Order...");
                                            FirebaseDatabase.getInstance().getReference("RestaurantFinalOrders")
                                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(RandomUID)
                                                    .child("OtherInformation").setValue(hashMap1).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    FirebaseDatabase.getInstance().getReference("CustomerFinalOrders")
                                                            .child(userid).child(RandomUID).child("OtherInformation").child("Status")
                                                            .setValue("Restaurant is preparing your order...").addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            FirebaseDatabase.getInstance().getReference("RestaurantWaitingOrders")
                                                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(RandomUID).child("Meals")
                                                                    .removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                    FirebaseDatabase.getInstance().getReference("RestaurantWaitingOrders")
                                                                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                                            .child(RandomUID).child("OtherInformation").removeValue()
                                                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                        @Override
                                                                        public void onSuccess(Void aVoid) {

                                                                            FirebaseDatabase.getInstance().getReference().child("Tokens")
                                                                                    .child(userid).child("token").addListenerForSingleValueEvent(new ValueEventListener() {
                                                                                @Override
                                                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                                                    String usertoken = dataSnapshot.getValue(String.class);
                                                                                    sendNotifications(usertoken, "Estimated Time", "Restaurant is Preparing your Order", "Preparing");
                                                                                    progressDialog.dismiss();
                                                                                    AlertDialog.Builder builder = new AlertDialog.Builder(RestaurantOrderToBePrepareView.this);
                                                                                    builder.setMessage("See Orders which are Prepared");
                                                                                    builder.setCancelable(false);
                                                                                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                                                        @Override
                                                                                        public void onClick(DialogInterface dialog, int which) {

                                                                                            dialog.dismiss();
                                                                                            Intent b = new Intent(RestaurantOrderToBePrepareView.this, RestaurantOrderToBePrepared.class);
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
                adapter = new RestaurantOrderToBePreparedViewAdapter(RestaurantOrderToBePrepareView.this, restaurantWaitingOrdersList);
                recyclerViewMeal.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("RestaurantWaitingOrders").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(RandomUID).child("OtherInformation");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                RestaurantWaitingOrders1 restaurantWaitingOrders1 = dataSnapshot.getValue(RestaurantWaitingOrders1.class);
                grandtotal.setText("R " + restaurantWaitingOrders1.getGrandTotalPrice());
                note.setText(restaurantWaitingOrders1.getNote());
                address.setText(restaurantWaitingOrders1.getAddress());
                name.setText(restaurantWaitingOrders1.getName());
                number.setText("+27" + restaurantWaitingOrders1.getMobileNumber());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void sendNotifications(String usertoken, String title, String message, String preparing) {
        Data data = new Data(title, message, preparing);
        NotificationSender sender = new NotificationSender(data, usertoken);
        apiService.sendNotification(sender).enqueue(new Callback<MyResponse>() {
            @Override
            public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
                if (response.code() == 200) {
                    if (response.body().success != 1) {
                        Toast.makeText(RestaurantOrderToBePrepareView.this, "Failed", Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<MyResponse> call, Throwable t) {

            }
        });
    }
}
