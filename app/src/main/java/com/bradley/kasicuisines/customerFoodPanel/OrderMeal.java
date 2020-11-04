package com.bradley.kasicuisines.customerFoodPanel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bradley.kasicuisines.CustomerPanel_BottomNavigation;
import com.bradley.kasicuisines.R;
import com.bradley.kasicuisines.businessFoodPanel.Restaurant;
import com.bradley.kasicuisines.models.UpdateDishModel;
import com.bumptech.glide.Glide;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class OrderMeal extends AppCompatActivity {

    String randomId,restaurantId;

    ImageView imageView;
    ElegantNumberButton addItem;
    TextView foodName, restaurantName, restaurantLocation, foodQuantity, foodPrice, foodDescription;
    DatabaseReference databaseReference, dataaa, restaurantData, reference, data, dataref;
    String province, city, suburb, mealMame;
    double mealPrice;
    String customerId;
    FirebaseDatabase firebaseDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_meal);

        foodName = (TextView) findViewById(R.id.food_name);
        restaurantName = (TextView) findViewById(R.id.restaurant_name);
        restaurantLocation = (TextView) findViewById(R.id.restaurant_location);
        foodQuantity = (TextView) findViewById(R.id.food_quantity);
        foodPrice = (TextView) findViewById(R.id.food_price);
         foodDescription = (TextView) findViewById(R.id.food_description);
        imageView = (ImageView) findViewById(R.id.image);
        addItem = (ElegantNumberButton) findViewById(R.id.number_button);

        final String userid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        dataaa = FirebaseDatabase.getInstance().getReference("Customer").child(userid);
        dataaa.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final Customer customer = dataSnapshot.getValue(Customer.class);
                province = customer.getProvince();
                city = customer.getCity();
                suburb = customer.getSuburb();

                randomId = getIntent().getStringExtra("FoodMenu");
                restaurantId = getIntent().getStringExtra("RestaurantId");

                databaseReference = FirebaseDatabase.getInstance().getReference("FoodDetails")
                        .child(province).child(city).child(suburb).child(restaurantId).child(randomId);
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        UpdateDishModel updateDishModel = dataSnapshot.getValue(UpdateDishModel.class);
                        foodName.setText(updateDishModel.getMealName());
                        String qty = "<b>" + "Quantity: " + "</b>" + updateDishModel.getQuantity();
                        foodQuantity.setText(Html.fromHtml(qty));
                        String descrp = "<b>" + "Description: " + "</b>" + updateDishModel.getDescription();
                        foodDescription.setText(Html.fromHtml(descrp));
                        String prc = "<b>" + "Price: R " + "</b>" + updateDishModel.getPrice();
                        foodPrice.setText(Html.fromHtml(prc));
                        Glide.with(OrderMeal.this).load(updateDishModel.getImageURL()).into(imageView);

                        restaurantData = FirebaseDatabase.getInstance().getReference("Restaurant").child(restaurantId);
                        restaurantData.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                Restaurant restaurant = dataSnapshot.getValue(Restaurant.class);

                                String name = "<b>" + "Restaurant Name: " + "</b>" + restaurant.getStoreName();
                                restaurantName.setText(Html.fromHtml(name));
                                String loc = "<b>" + "Location: " + "</b>" + restaurant.getSuburb();
                                restaurantLocation.setText(Html.fromHtml(loc));
                                customerId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                databaseReference = FirebaseDatabase.getInstance().getReference("Cart")
                                        .child("CartItems").child(customerId).child(randomId);
                                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                        Cart cart = dataSnapshot.getValue(Cart.class);
                                        if(dataSnapshot.exists()) {
                                            addItem.setNumber(cart.getMealQuantity());
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        addItem.setOnClickListener(new ElegantNumberButton.OnClickListener() {
            @Override
            public void onClick(View view) {

                dataref = FirebaseDatabase.getInstance().getReference("Cart")
                        .child("CartItems").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                dataref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Cart cart1 = null;
                        if(dataSnapshot.exists()) {
                            int totalcount=0;

                            for(DataSnapshot snapshot: dataSnapshot.getChildren()) {
                                totalcount ++;
                            }
                            int i=0;
                            for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                                i++;
                                if(i==totalcount) {
                                    cart1= snapshot.getValue(Cart.class);
                                }
                            }

                            if (restaurantId.equals(cart1.getRestaurantId())) {
                                data = FirebaseDatabase.getInstance().getReference("FoodDetails")
                                        .child(province).child(city).child(suburb).child(restaurantId).child(randomId);
                                data.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        UpdateDishModel update = dataSnapshot.getValue(UpdateDishModel.class);
                                        mealMame = update.getMealName();
                                        mealPrice = Double.parseDouble(update.getPrice());

                                        int num = Integer.parseInt(addItem.getNumber());
                                        double totalPrice = num * mealPrice;
                                        if (num != 0) {
                                            HashMap<String, String> hashMap = new HashMap<>();
                                            hashMap.put("mealName", mealMame);
                                            hashMap.put("mealId", randomId);
                                            hashMap.put("mealQuantity", String.valueOf(num));
                                            hashMap.put("price", String.valueOf(mealPrice));
                                            hashMap.put("totalPrice", String.valueOf(totalPrice));
                                            hashMap.put("restaurantId", restaurantId);
                                            customerId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                            reference = FirebaseDatabase.getInstance().getReference("Cart")
                                                    .child("CartItems").child(customerId).child(randomId);
                                            reference.setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {

                                                    Toast.makeText(OrderMeal.this, "Added to cart", Toast.LENGTH_SHORT).show();
                                                }
                                            });

                                        } else {

                                            firebaseDatabase.getInstance().getReference("Cart")
                                                    .child(customerId).child(randomId).removeValue();
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                            }

                            else
                            {
                                AlertDialog.Builder builder = new AlertDialog.Builder(OrderMeal.this);
                                builder.setMessage("You can't add food items of multiple restaurants at a time. Try to add items of same the same restaurant");
                                builder.setCancelable(false);
                                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        dialog.dismiss();
                                        Intent intent = new Intent(OrderMeal.this, CustomerPanel_BottomNavigation.class);
                                        startActivity(intent);
                                        finish();

                                    }
                                });
                                AlertDialog alert = builder.create();
                                alert.show();
                            }
                        } else {
                            data = FirebaseDatabase.getInstance().getReference("FoodDetails")
                                    .child(province).child(city).child(suburb).child(restaurantId).child(randomId);
                            data.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    UpdateDishModel update = dataSnapshot.getValue(UpdateDishModel.class);
                                    mealMame = update.getMealName();
                                    mealPrice = Double.parseDouble(update.getPrice());
                                    int num = Integer.parseInt(addItem.getNumber());
                                    double totalPrice = num * mealPrice;
                                    if (num != 0) {
                                        HashMap<String, String> hashMap = new HashMap<>();
                                        hashMap.put("mealName",  mealMame);
                                        hashMap.put("mealId", randomId);
                                        hashMap.put("mealQuantity", String.valueOf(num));
                                        hashMap.put("price", String.valueOf(mealPrice));
                                        hashMap.put("totalPrice", String.valueOf(totalPrice));
                                        hashMap.put("restaurantId", restaurantId);
                                        customerId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                        reference = FirebaseDatabase.getInstance().getReference("Cart")
                                                .child("CartItems").child(customerId).child(randomId);
                                        reference.setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {

                                                Toast.makeText(OrderMeal.this, "Added to cart", Toast.LENGTH_SHORT).show();
                                            }
                                        });

                                    } else {

                                        firebaseDatabase.getInstance().getReference("Cart").child(customerId).child(randomId).removeValue();
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
    }

}



