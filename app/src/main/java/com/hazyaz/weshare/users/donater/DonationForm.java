package com.hazyaz.weshare.users.donater;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.camera2.TotalCaptureResult;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.hazyaz.weshare.R;
import com.hazyaz.weshare.users.areaincharge.AIHome;

import java.util.HashMap;

public class DonationForm extends Fragment {

    EditText donater_name, donater_city, donater_phone, donation_name, donation_desciption;
    Spinner donation_area;
    Button submitDonation;
    String area[] = {"Mumbai-Central", "Mahim", "Nallasopara", "Andheri", "Byculla", "Jogeshwari"};
    ProgressDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootViewDonater = inflater.inflate(R.layout.donater_form,
                container, false);


        donater_name = rootViewDonater.findViewById(R.id.donater_name);
        donater_city = rootViewDonater.findViewById(R.id.donater_city);
        donater_phone = rootViewDonater.findViewById(R.id.donater_phone);
        donation_name = rootViewDonater.findViewById(R.id.donation_name);
        donation_desciption = rootViewDonater.findViewById(R.id.donation_description);
        donation_area = rootViewDonater.findViewById(R.id.donater_area_spinner);
        submitDonation = rootViewDonater.findViewById(R.id.submit_donation_form);

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                (getContext(), android.R.layout.simple_spinner_item,
                        area); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);
        donation_area.setAdapter(spinnerArrayAdapter);
        progressDialog=new ProgressDialog(getContext(),3);



        submitDonation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressDialog.setMessage("Donation Request Sending");
                progressDialog.show();


                String donp_name, donp_city, donp_phone, don_name,don_desc, area;

                donp_name = donater_name.getText().toString();
                donp_city = donater_city.getText().toString();
                donp_phone = donater_phone.getText().toString();
                don_name = donation_name.getText().toString();
                don_desc = donation_desciption.getText().toString();
                area = donation_area.getSelectedItem().toString();



                if(donp_name.equals("")||donp_city.equals("")||donp_phone.equals("")||
                        don_name.equals("")||don_desc.equals("") ||area.equals("")){
                    Toast.makeText(getContext(), "fill all the data ", Toast.LENGTH_SHORT).show();
                }
                filledDonationForm(donp_name,donp_city,donp_phone,don_name,don_desc,area);
            }
        });
        return rootViewDonater;

    }

    void filledDonationForm(String name, String city, String phone, String donation_name, String donation_desc, String donation_area){



        final FirebaseAuth mAuth=FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        String uid=user.getUid();

        FirebaseDatabase database=FirebaseDatabase.getInstance();
        DatabaseReference reference=database.getReference("Donater").child(uid);
        HashMap<Object,String> hashMap=new HashMap<>();
        String key = reference.push().getKey();

        hashMap.put("Donater Name",name);
        hashMap.put("Donater City",city);
        hashMap.put("Donater Phone",phone);
        hashMap.put("Donation Name",donation_name);
        hashMap.put("Donation Desc",donation_desc);
        hashMap.put("Donation Area",donation_area);
        hashMap.put("TimeStamp", ""+System.currentTimeMillis());


        reference.child("donations").child(key).setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful()){
                    progressDialog.dismiss();

                    new AlertDialog.Builder(getContext(),4)
                            .setTitle("Donation Request")
                            .setMessage("Your Donation Request is sent to the Area Incharge, they will contact you shortly")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    startActivity(new Intent(getContext(),DonaterHome.class));
                                }
                            })
                            .create()
                            .show();
                }
                else{
                    Toast.makeText(getContext(),"Cannot send data to server ",Toast.LENGTH_LONG).show();
                }
            }
        });



    }




}
