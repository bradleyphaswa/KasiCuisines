package com.bradley.kasicuisines.customerFoodPanel;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class CustomerProfileFragment extends Fragment {

    EditText firstname, lastname, streetNo, City, Suburban;
    Spinner spinnerProvince;
    TextView mobileno, Email, password;
    Button Update;
    LinearLayout LogOut;
    DatabaseReference databaseReference, data;
    FirebaseDatabase firebaseDatabase;
    String provnce, cityy, suburban, email, passwordd, confirmpass;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_customer_profile, null);
        getActivity().setTitle("Profile");

        firstname = (EditText) v.findViewById(R.id.fnamee);
        lastname = (EditText) v.findViewById(R.id.lnamee);
        streetNo = (EditText) v.findViewById(R.id.street);
        Email = (TextView) v.findViewById(R.id.emailID);
        spinnerProvince = (Spinner) v.findViewById(R.id.provincee);
        City = (EditText) v.findViewById(R.id.cityy);
        Suburban = (EditText) v.findViewById(R.id.sub);
        mobileno = (TextView) v.findViewById(R.id.mobilenumber);
        Update = (Button) v.findViewById(R.id.update);
        password = (TextView) v.findViewById(R.id.Passwordd);
        LogOut = (LinearLayout) v.findViewById(R.id.logout_layout);

        String userid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference("Customer").child(userid);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final Customer customer = dataSnapshot.getValue(Customer.class);

                firstname.setText(customer.getFirstName());
                lastname.setText(customer.getLastName());
                streetNo.setText(customer.getStreetNo());
                mobileno.setText(customer.getMobileNo());
                Email.setText(customer.getEmail());
                spinnerProvince.setSelection(getIndexByString(spinnerProvince, customer.getProvince()));
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

                City.setText(customer.getCity());
                Suburban.setText(customer.getSuburb());
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
                data = FirebaseDatabase.getInstance().getReference("Customer").child(useridd);
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

                        String Fname = firstname.getText().toString().trim();
                        String Lname = lastname.getText().toString().trim();
                        String Address = streetNo.getText().toString().trim();

                        HashMap<String, String> hashMappp = new HashMap<>();
                        hashMappp.put("city", cityy);
                        hashMappp.put("email", email);
                        hashMappp.put("firstName", Fname);
                        hashMappp.put("lastName", Lname);
                        hashMappp.put("mobileNo", String.valueOf(mobilenoo));
                        hashMappp.put("streetNo", Address);
                        hashMappp.put("province", provnce);
                        hashMappp.put("suburb", suburban);
                        firebaseDatabase.getInstance().getReference("Customer")
                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(hashMappp);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


            }
        });

        password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), CustomerPassword.class);
                startActivity(intent);
            }
        });

        mobileno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getActivity(), CustomerPhoneNumber.class);
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
