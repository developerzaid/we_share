package com.hazyaz.weshare.users.areaincharge;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hazyaz.weshare.R;
import com.hazyaz.weshare.users.donater.MyListAdapter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

public class AIHome extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference, innerDB;
    FirebaseAuth mAuth;
    ArrayList<ArrayList<String>> donations = new ArrayList<ArrayList<String>>();
    MyListAdapter adapter;
    RecyclerView recyclerView;
    String userKey;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ai_home);

        mAuth = FirebaseAuth.getInstance();



//        String uid = mAuth.getCurrentUser().getUid();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Donater");
        innerDB = firebaseDatabase.getReference("Donater");

        getAllDonations();


        recyclerView = findViewById(R.id.recyclerViewi);



        adapter = new MyListAdapter(donations,"AIHome",getApplicationContext());

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(adapter);



    }

    void getAllDonations(){
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                Toast.makeText(getApplicationContext(),""+snapshot.getValue(),Toast.LENGTH_LONG).show();
                Object ic = snapshot.getValue();

//                Log.d("asdasdas",""+snapshot.getValue());

                for (DataSnapshot dsp: snapshot.getChildren()){
                    String key = dsp.getKey();
                    assert key != null;
                    userKey = key;

                    innerDB.child(key).child("donations").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            for(DataSnapshot isp: snapshot.getChildren()){
                                ArrayList<String> data = new ArrayList<>();
                                String keyy = snapshot.child(Objects.requireNonNull(isp.getKey())).toString();
                                String donationName = snapshot.child(Objects.requireNonNull(isp.getKey())).child("Donation Name").getValue().toString();
                                String donationDesc = snapshot.child(Objects.requireNonNull(isp.getKey())).child("Donation Desc").getValue().toString();
                                String donationArea = snapshot.child(Objects.requireNonNull(isp.getKey())).child("Donation Area").getValue().toString();
                                String donaterName = snapshot.child(Objects.requireNonNull(isp.getKey())).child("Donater Name").getValue().toString();
                                String donaterPhone = snapshot.child(Objects.requireNonNull(isp.getKey())).child("Donater Phone").getValue().toString();
                                String CurrentLocation = snapshot.child(Objects.requireNonNull(isp.getKey())).child("Current Location").getValue().toString();
                                String DonaterCity = snapshot.child(Objects.requireNonNull(isp.getKey())).child("Donater City").getValue().toString();
                                String ImageXX = snapshot.child(Objects.requireNonNull(isp.getKey())).child("Image").getValue().toString();

                                    data.add(donaterName);
                                    data.add(DonaterCity);
                                    data.add(donaterPhone);
                                    data.add(donationName);
                                    data.add(donationDesc);
                                    data.add(donationArea);
                                    data.add(CurrentLocation);
                                     data.add(ImageXX);
                                     data.add(keyy);
                                    data.add(userKey);
                                     donations.add(data);

                                   adapter.notifyDataSetChanged();

//                                Log.d("asdasdas 2",""+snapshot.child(Objects.requireNonNull(isp.getKey())).child("Donation Name").getValue());

                            }


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });



                 Log.d("thisisdoantion",""+dsp.getKey());





                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        String AILogged =prefs.getString("AreaIncharge","");

        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser==null){
            startActivity(new Intent(AIHome.this, AILogin.class));
        }
    }
}