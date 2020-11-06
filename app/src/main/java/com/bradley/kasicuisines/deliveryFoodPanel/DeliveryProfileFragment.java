package com.bradley.kasicuisines.deliveryFoodPanel;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bradley.kasicuisines.Customer;
import com.bradley.kasicuisines.MainMenu;
import com.bradley.kasicuisines.R;
import com.bradley.kasicuisines.customerFoodPanel.CustomerPassword;
import com.bradley.kasicuisines.customerFoodPanel.CustomerPhoneNumber;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class DeliveryProfileFragment extends Fragment {

    EditText edt_first_name, edt_last_name, edt_street, edt_city, edt_suburb;
    Spinner spinnerProvince;
    TextView txt_mobile_number, txt_emailID;
    Button Update;
    LinearLayout LogOut, password_layout;
    DatabaseReference databaseReference, data;
    FirebaseDatabase firebaseDatabase;
    String provnce, cityy, suburban, email, passwordd, confirmpass;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_delivery_profile, null);
        getActivity().setTitle("Profile");

        edt_first_name = (EditText) v.findViewById(R.id.edt_first_name);
        edt_last_name = (EditText) v.findViewById(R.id.edt_last_name);
        txt_emailID = (TextView) v.findViewById(R.id.txt_emailID);
        edt_street = (EditText) v.findViewById(R.id.edt_street);
        spinnerProvince = (Spinner) v.findViewById(R.id.d_spinner_province);
        edt_city = (EditText) v.findViewById(R.id.edt_city);
        edt_suburb = (EditText) v.findViewById(R.id.edt_suburb);
        txt_mobile_number = (TextView) v.findViewById(R.id.txt_mobile_number);
        Update = (Button) v.findViewById(R.id.d_update);
        password_layout = (LinearLayout) v.findViewById(R.id.d_password_layout);
        LogOut = (LinearLayout) v.findViewById(R.id.logout_layout);


        String userid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference("Drivers").child(userid);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                 Drivers driver = dataSnapshot.getValue(Drivers.class);

                edt_first_name.setText(driver.getFirstName());
                edt_last_name.setText(driver.getLastName());
                edt_street.setText(driver.getStreetNo());
                txt_mobile_number.setText(driver.getMobileNo());
                txt_emailID.setText(driver.getEmail());
                spinnerProvince.setSelection(getIndexByString(spinnerProvince, driver.getProvince()));
                spinnerProvince.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        Object value = parent.getItemAtPosition(position);
                        provnce = value.toString().trim();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                edt_city.setText(driver.getCity());
                edt_suburb.setText(driver.getSuburb());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        updateinformation();
        return v;
    }

    private void updateinformation() {


        Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String useridd = FirebaseAuth.getInstance().getCurrentUser().getUid();
                data = FirebaseDatabase.getInstance().getReference("Drivers").child(useridd);
                data.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Customer customer = dataSnapshot.getValue(Customer.class);

                        cityy = customer.getCity();
                        suburban = customer.getSuburb();
                        confirmpass = customer.getPassword();
                        email = customer.getEmail();
                        passwordd = customer.getPassword();
                        long mobilenoo = Long.parseLong(customer.getMobileNo());

                        String Fname = edt_first_name.getText().toString().trim();
                        String Lname = edt_last_name.getText().toString().trim();
                        String Address = edt_street.getText().toString().trim();

                        HashMap<String, String> hashMappp = new HashMap<>();
                        hashMappp.put("city", cityy);
                        hashMappp.put("email", email);
                        hashMappp.put("firstName", Fname);
                        hashMappp.put("lastName", Lname);
                        hashMappp.put("mobileNo", String.valueOf(mobilenoo));
                        hashMappp.put("streetNo", Address);
                        hashMappp.put("province", provnce);
                        hashMappp.put("suburb", suburban);
                        firebaseDatabase.getInstance().getReference("Driver")
                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(hashMappp);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


            }
        });

        password_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), DriverPassword.class);
                startActivity(intent);
            }
        });

        txt_mobile_number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getActivity(), DriverPhoneNumber.class);
                startActivity(i);
            }
        });

        LogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("Are you sure you want to Logout ?");
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        FirebaseAuth.getInstance().signOut();
                        Intent intent = new Intent(getActivity(), MainMenu.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);


                    }
                });
                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();


            }
        });

    }

    private int getIndexByString(Spinner st, String spist) {
        int index = 0;
        for (int i = 0; i < st.getCount(); i++) {
            if (st.getItemAtPosition(i).toString().equalsIgnoreCase(spist)) {
                index = i;
                break;
            }
        }
        return index;
    }
}
