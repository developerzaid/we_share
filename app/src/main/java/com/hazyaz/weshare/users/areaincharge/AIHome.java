package com.hazyaz.weshare.users.areaincharge;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hazyaz.weshare.R;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

public class AIHome extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference, innerDB;
    FirebaseAuth mAuth;
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






    }

    void getAllDonations(){
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Toast.makeText(getApplicationContext(),""+snapshot.getValue(),Toast.LENGTH_LONG).show();
                Object ic = snapshot.getValue();

                Log.d("asdasdas",""+snapshot.getValue());

                for (DataSnapshot dsp: snapshot.getChildren()){
                    String key = dsp.getKey();
                    assert key != null;

                    innerDB.child(key).child("donations").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            for(DataSnapshot isp: snapshot.getChildren()){
                                Log.d("asdasdas 2",""+snapshot.child(Objects.requireNonNull(isp.getKey())).child("Donation Name").getValue());

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
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, AILogin.class, null)
                    .commit();
        }
    }
}