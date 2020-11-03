package com.bradley.kasicuisines.businessFoodPanel;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bradley.kasicuisines.BusinessPanel_BottomNavigation;
import com.bradley.kasicuisines.R;
import com.bradley.kasicuisines.models.UpdateDishModel;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.UUID;

public class UpdateDelete_Meal extends AppCompatActivity {

    TextInputLayout desc, qty, prc;
    TextView mealName;
    ImageButton imageButton;
    Uri imageUri;
    String description, quantity, price, meal, restaurantId, dbUri, ID, randomUID, province, city, suburb;
    private Uri mCropImageUri;
    Button updateMeal, deleteMeal;
    StorageReference ref, storageReference;
    FirebaseStorage storage;
    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference mDatabaseReference, data;
    FirebaseAuth mFirebaseAuth;
    private ProgressDialog mProgressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_udate_delete__meal);

        desc = (TextInputLayout)findViewById(R.id.description);
        qty = (TextInputLayout) findViewById(R.id.quantity);
        prc = (TextInputLayout)findViewById(R.id.price);
        mealName = (TextView)findViewById(R.id.Meal_Name);
        imageButton = (ImageButton) findViewById(R.id.imageUpload);
        updateMeal = (Button)findViewById(R.id.update_meal);
        deleteMeal = (Button)findViewById(R.id.delete_meal);
        ID = getIntent().getStringExtra("updateDeleteMeal");


        final String userid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        data = mFirebaseDatabase.getInstance().getReference("Restaurant").child(userid);
        data.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Restaurant restaurant = snapshot.getValue(Restaurant.class);
                province = restaurant.getProvince();
                city = restaurant.getCity();
                suburb= restaurant.getSuburb();

                updateMeal.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        description = desc.getEditText().getText().toString().trim();
                        quantity = qty.getEditText().getText().toString().trim();
                        price = prc.getEditText().getText().toString().trim();
                        meal = mealName.getText().toString();

                        if(isValid()){
                            if(imageUri != null){
                                uploadImage();
                            }else{
                                updatedesc(dbUri);
                            }
                        }

                    }
                });
                deleteMeal.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(UpdateDelete_Meal.this);
                        builder.setMessage("Are you sure you want to Delete this Meal");
                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mFirebaseDatabase.getInstance().getReference("FoodDetails").child(province).child(city).child(suburb)
                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(ID).removeValue();
                                AlertDialog.Builder food = new AlertDialog.Builder(UpdateDelete_Meal.this);
                                food.setMessage("Meal deleted successfully!");
                                food.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        startActivity(new Intent(UpdateDelete_Meal.this, BusinessPanel_BottomNavigation.class));
                                    }
                                });
                                AlertDialog alert = food.create();
                                alert.show();
                            }
                        });
                        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                        AlertDialog alert = builder.create();
                        alert.show();
                    }
                });

                String useridd = FirebaseAuth.getInstance().getCurrentUser().getUid();
                mProgressDialog = new ProgressDialog(UpdateDelete_Meal.this);
                mDatabaseReference = FirebaseDatabase.getInstance().getReference("FoodDetails").child(province).child(city).child(suburb)
                        .child(useridd).child(ID);
                mDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        UpdateDishModel updateDishModel = snapshot.getValue(UpdateDishModel.class);
                        desc.getEditText().setText(updateDishModel.getDescription());
                        qty.getEditText().setText(updateDishModel.getQuantity());
                        mealName.setText(updateDishModel.getMealName());
                        prc.getEditText().setText(updateDishModel.getPrice());
                        Glide.with(UpdateDelete_Meal.this).load(updateDishModel.getImageURL()).into(imageButton);
                        dbUri = updateDishModel.getImageURL();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                mFirebaseAuth = FirebaseAuth.getInstance();
                mDatabaseReference = mFirebaseDatabase.getInstance().getReference("FoodDetails");
                storage = FirebaseStorage.getInstance();
                storageReference = storage.getReference();
                imageButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onSelectImageclick(v);
                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void updatedesc(String buri) {

        restaurantId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FoodDetails info = new FoodDetails(meal, description , quantity, price, buri, ID, restaurantId);
        mFirebaseDatabase.getInstance().getReference("FoodDetails").child(province).child(city).child(suburb)
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(ID)
                .setValue(info).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                mProgressDialog.dismiss();
                Toast.makeText(UpdateDelete_Meal.this,"Meal Updates Successfully!",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void uploadImage() {

        if(imageUri != null){

            mProgressDialog.setTitle("Uploading....");
            mProgressDialog.show();
            randomUID = UUID.randomUUID().toString();
            ref = storageReference.child(randomUID);
            ref.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            updatedesc(String.valueOf(uri));
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    mProgressDialog.dismiss();
                    Toast.makeText(UpdateDelete_Meal.this,"Failed:"+e.getMessage(),Toast.LENGTH_LONG).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = (100.0 * taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                    mProgressDialog.setMessage("Upload "+(int) progress+"%");
                    mProgressDialog.setCanceledOnTouchOutside(false);
                }
            });
        }

    }

    private boolean isValid() {

        desc.setErrorEnabled(false);
        desc.setError("");
        qty.setErrorEnabled(false);
        qty.setError("");
        prc.setErrorEnabled(false);
        prc.setError("");

        boolean isValidDescription = false,isValidPrice=false,isValidQuantity=false,isValid=false;
        if(TextUtils.isEmpty(description)){
            desc.setErrorEnabled(true);
            desc.setError("Description is Required");
        }else{
            desc.setError(null);
            isValidDescription=true;
        }
        if(TextUtils.isEmpty(quantity)){
            qty.setErrorEnabled(true);
            qty.setError("Enter number of Plates or Items");
        }else{
            isValidQuantity=true;
        }
        if(TextUtils.isEmpty(price)){
            prc.setErrorEnabled(true);
            prc.setError("Please Mention Price");
        }else{
            isValidPrice=true;
        }
        isValid = (isValidDescription && isValidQuantity && isValidPrice)?true:false;
        return isValid;
    }

    private void startCropImageActivity(Uri imageuri){
        CropImage.activity(imageuri)
                .setGuidelines(CropImageView.Guidelines.ON)
                .setMultiTouchEnabled(true)
                .start(this);
    }
    private void onSelectImageclick(View v){
        CropImage.startPickImageActivity(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(mCropImageUri !=null && grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED){
            startCropImageActivity(mCropImageUri);
        }else{
            Toast.makeText(this,"Cancelling! Permission Not Granted",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    @SuppressLint("NewApi")
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(requestCode==CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode== Activity.RESULT_OK){
            imageUri = CropImage.getPickImageResultUri(this,data);
            if(CropImage.isReadExternalStoragePermissionsRequired(this,imageUri)){
                mCropImageUri = imageUri;
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},0);
            }else{
                startCropImageActivity(imageUri);
            }
        }
        if(requestCode==CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if(resultCode==RESULT_OK){
                ((ImageButton) findViewById(R.id.image_upload)).setImageURI(result.getUri());
                Toast.makeText(this,"Cropped Successfully!"+result.getSampleSize(),Toast.LENGTH_SHORT).show();
            }else if(resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE){
                Toast.makeText(this,"Failed To Crop"+result.getError(),Toast.LENGTH_SHORT).show();

            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }


}