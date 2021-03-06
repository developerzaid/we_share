package com.hazyaz.weshare.users.donater;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hazyaz.weshare.R;

import java.util.ArrayList;
import java.util.Objects;


public class DonaterHome extends AppCompatActivity {
    ProgressDialog progressDialog;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference,databaseRef;
    FirebaseAuth mAuth;
    ArrayList<ArrayList<String>> donations = new ArrayList<ArrayList<String>>();
    MyListAdapter adapter;
    RecyclerView recyclerView;
    TextView uName, uPhone, uCity, uArea;
    int i = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.donater_home);
        mAuth = FirebaseAuth.getInstance();



        uName = findViewById(R.id.UName);
        uCity = findViewById(R.id.UCity);
        uPhone = findViewById(R.id.UPhone);
        uArea = findViewById(R.id.UArea);


        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);



        adapter = new MyListAdapter(donations,"DonaterHome",getApplicationContext());

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(adapter);
//

        progressDialog=new ProgressDialog(this,4);



        Button donate = findViewById(R.id.donate_button);
        donate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               startActivity(new Intent(DonaterHome.this, DonationForm.class));
            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();

        if(mAuth.getCurrentUser()==null){

        startActivity(new Intent(DonaterHome.this,DonaterLogin.class));

        }
        else{
            String uid = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
            firebaseDatabase = FirebaseDatabase.getInstance();
            databaseReference = firebaseDatabase.getReference("Donater").child(uid).child("donations");
            databaseRef = firebaseDatabase.getReference("Donater").child(uid);
          getUserInfo();
            getDonationInfo();

        }


    }

    void getUserInfo(){

        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                uName.setText("Name: "+snapshot.child("Name").getValue().toString());
                uArea.setText("Area: "+snapshot.child("area").getValue().toString());
                uPhone.setText("Phone no: "+snapshot.child("Phone").getValue().toString());
                uCity.setText("City: "+snapshot.child("city").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }



    void getDonationInfo(){

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                for (DataSnapshot dsp: snapshot.getChildren()){

                    ArrayList<String> data = new ArrayList<>();
                    String name = dsp.child("Donater Name").getValue().toString();
                    String city = dsp.child("Donater City").getValue().toString();
                    String phone = dsp.child("Donater Phone").getValue().toString();
                    String item_name = dsp.child("Donation Name").getValue().toString();
                    String item_desc = dsp.child("Donation Desc").getValue().toString();
                    String area = dsp.child("Donation Area").getValue().toString();
                    String currentLocation = dsp.child("Current Location").getValue().toString();
                    String Imagecc = dsp.child("Image").getValue().toString();

                     data.add(name);
                     data.add(city);
                     data.add(phone);
                     data.add(item_name);
                     data.add(item_desc);
                     data.add(area);
                     data.add(currentLocation);
                     data.add(Imagecc);

                     donations.add(data);
                     adapter.notifyDataSetChanged();


//                    Toast.makeText(DonaterHome.this,""+data,Toast.LENGTH_LONG).show();

                }

                String key  = snapshot.getKey();

                // String name = snapshot.child(key).child("Donater Name").getValue().toString();
                // after getting the value we are setting
                // our value to our text view in below line.

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // calling on cancelled method when we receive
                // any error or we are not able to get the data.
                Toast.makeText(DonaterHome.this, "Fail to get data.", Toast.LENGTH_SHORT).show();
            }
        });

    }



}