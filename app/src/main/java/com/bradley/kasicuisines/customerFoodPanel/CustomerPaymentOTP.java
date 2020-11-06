package com.bradley.kasicuisines.customerFoodPanel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bradley.kasicuisines.CustomerPanel_BottomNavigation;
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

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomerPaymentOTP extends AppCompatActivity {

    EditText otp;
    Button place;
    String ot, randomUID, restaurantId;
    private APIService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_payment_o_t_p);

        otp = (EditText) findViewById(R.id.OTP);
        place = (Button) findViewById(R.id.place);
        apiService = Client.getClient("https://fcm.googleapis.com/").create(APIService.class);

        randomUID = getIntent().getStringExtra("randomUID");

        place.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ot = otp.getText().toString().trim();

                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("CustomerPaymentOrders")
                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(randomUID).child("Meals");
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                            final CustomerPaymentOrders customerPaymentOrders = dataSnapshot1.getValue(CustomerPaymentOrders.class);
                            HashMap<String, String> hashMap = new HashMap<>();
                            String mealId = customerPaymentOrders.getMealId();
                            hashMap.put("restaurantId", customerPaymentOrders.getRestaurantId());
                            hashMap.put("mealId", customerPaymentOrders.getMealId());
                            hashMap.put("mealName", customerPaymentOrders.getMealName());
                            hashMap.put("mealPrice", customerPaymentOrders.getMealPrice());
                            hashMap.put("mealQuantity", customerPaymentOrders.getMealQuantity());
                            hashMap.put("randomUID", randomUID);
                            hashMap.put("totalPrice", customerPaymentOrders.getTotalPrice());
                            hashMap.put("userId", customerPaymentOrders.getUserId());
                            FirebaseDatabase.getInstance().getReference("CustomerFinalOrders")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(randomUID).child("Meals").child(mealId).setValue(hashMap);
                        }
                        DatabaseReference data = FirebaseDatabase.getInstance().getReference("CustomerPaymentOrders")
                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(randomUID).child("OtherInformation");
                        data.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                final CustomerPaymentOrders1 customerPaymentOrders1 = dataSnapshot.getValue(CustomerPaymentOrders1.class);
                                HashMap<String, String> hashMap1 = new HashMap<>();
                                hashMap1.put("address", customerPaymentOrders1.getAddress());
                                hashMap1.put("GrandTotalPrice", customerPaymentOrders1.getGrandTotalPrice());
                                hashMap1.put("mobileNumber", customerPaymentOrders1.getMobileNumber());
                                hashMap1.put("name", customerPaymentOrders1.getName());
                                hashMap1.put("note", customerPaymentOrders1.getNote());
                                hashMap1.put("randomUID", randomUID);
                                hashMap1.put("Status", "Your order is waiting to be prepared by Restaurant...");
                                FirebaseDatabase.getInstance().getReference("CustomerFinalOrders")
                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(randomUID)
                                        .child("OtherInformation").setValue(hashMap1).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        DatabaseReference Reference = FirebaseDatabase.getInstance().getReference("CustomerPaymentOrders")
                                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(randomUID).child("Meals");
                                        Reference.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                                    final CustomerPaymentOrders customerPaymentOrderss = snapshot.getValue(CustomerPaymentOrders.class);
                                                    HashMap<String, String> hashMap2 = new HashMap<>();
                                                    String mealId = customerPaymentOrderss.getMealId();
                                                    restaurantId = customerPaymentOrderss.getRestaurantId();
                                                    hashMap2.put("restaurantId", customerPaymentOrderss.getRestaurantId());
                                                    hashMap2.put("mealId", customerPaymentOrderss.getMealId());
                                                    hashMap2.put("mealName", customerPaymentOrderss.getMealName());
                                                    hashMap2.put("mealPrice", customerPaymentOrderss.getMealPrice());
                                                    hashMap2.put("mealQuantity", customerPaymentOrderss.getMealQuantity());
                                                    hashMap2.put("randomUID", randomUID);
                                                    hashMap2.put("totalPrice", customerPaymentOrderss.getTotalPrice());
                                                    hashMap2.put("userId", customerPaymentOrderss.getUserId());
                                                    FirebaseDatabase.getInstance().getReference("RestaurantWaitingOrders")
                                                            .child(restaurantId).child(randomUID).child("Meals").child(mealId).setValue(hashMap2);
                                                }
                                                DatabaseReference dataa = FirebaseDatabase.getInstance().getReference("CustomerPaymentOrders")
                                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(randomUID).child("OtherInformation");
                                                dataa.addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                        CustomerPaymentOrders1 customerPaymentOrders11 = dataSnapshot.getValue(CustomerPaymentOrders1.class);
                                                        HashMap<String, String> hashMap3 = new HashMap<>();
                                                        hashMap3.put("address", customerPaymentOrders11.getAddress());
                                                        hashMap3.put("GrandTotalPrice", customerPaymentOrders11.getGrandTotalPrice());
                                                        hashMap3.put("mobileNumber", customerPaymentOrders11.getMobileNumber());
                                                        hashMap3.put("name", customerPaymentOrders11.getName());
                                                        hashMap3.put("note", customerPaymentOrders11.getNote());
                                                        hashMap3.put("randomUID", randomUID);
                                                        hashMap3.put("Status", "Your order is waiting to be prepared by Restaurant...");
                                                        FirebaseDatabase.getInstance().getReference("RestaurantWaitingOrders").child(restaurantId)
                                                                .child(randomUID).child("OtherInformation").setValue(hashMap3).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                FirebaseDatabase.getInstance().getReference("RestaurantPaymentOrders").child(restaurantId)
                                                                        .child(randomUID).child("Meals").removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                    @Override
                                                                    public void onComplete(@NonNull Task<Void> task) {
                                                                        FirebaseDatabase.getInstance().getReference("RestaurantPaymentOrders")
                                                                                .child(restaurantId).child(randomUID).child("OtherInformation").removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                            @Override
                                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                                FirebaseDatabase.getInstance().getReference("CustomerPaymentOrders")
                                                                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(randomUID)
                                                                                        .child("Meals").removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                    @Override
                                                                                    public void onComplete(@NonNull Task<Void> task) {
                                                                                        FirebaseDatabase.getInstance().getReference("CustomerPaymentOrders")
                                                                                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(randomUID)
                                                                                                .child("OtherInformation").removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                                            @Override
                                                                                            public void onSuccess(Void aVoid) {
                                                                                                FirebaseDatabase.getInstance().getReference().child("Tokens")
                                                                                                        .child(restaurantId).child("token").addListenerForSingleValueEvent(new ValueEventListener() {
                                                                                                    @Override
                                                                                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                                                                        String usertoken = dataSnapshot.getValue(String.class);
                                                                                                        sendNotifications(usertoken, "Order Confirmed", "Payment mode is confirmed by user, Now you can start the order", "Confirm");
                                                                                                    }

                                                                                                    @Override
                                                                                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                                                                                    }
                                                                                                });

                                                                                            }
                                                                                        }).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                                            @Override
                                                                                            public void onSuccess(Void aVoid) {
                                                                                                AlertDialog.Builder builder = new AlertDialog.Builder(CustomerPaymentOTP.this);
                                                                                                builder.setMessage("Payment mode confirmed, Now you can track your order.");
                                                                                                builder.setCancelable(false);
                                                                                                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                                                                    @Override
                                                                                                    public void onClick(DialogInterface dialog, int which) {

                                                                                                        dialog.dismiss();
                                                                                                        Intent b = new Intent(CustomerPaymentOTP.this, CustomerPanel_BottomNavigation.class);
                                                                                                        b.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                                                                        startActivity(b);
                                                                                                        finish();

                                                                                                    }
                                                                                                });
                                                                                                AlertDialog alert = builder.create();
                                                                                                alert.show();
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
    }

    private void sendNotifications(String usertoken, String title, String message, String confirm) {

        Data data = new Data(title, message, confirm);
        NotificationSender sender = new NotificationSender(data, usertoken);
        apiService.sendNotification(sender).enqueue(new Callback<MyResponse>() {
            @Override
            public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
                if (response.code() == 200) {
                    if (response.body().success != 1) {
                        Toast.makeText(CustomerPaymentOTP.this, "Failed", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<MyResponse> call, Throwable t) {

            }
        });
    }


}
