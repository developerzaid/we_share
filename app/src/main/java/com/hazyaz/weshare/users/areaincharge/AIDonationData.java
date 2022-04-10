package com.hazyaz.weshare.users.areaincharge;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.CursorIndexOutOfBoundsException;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.hazyaz.weshare.R;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;

public class AIDonationData extends AppCompatActivity {

    private static final int CAMERA_REQUEST = 1888;
    String key,userkey;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    TextView name,city,phone,itemname,itemdesc, areax,currentlocationx;
    ImageView imageViewd;
    Uri mImageUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ai_donation_data);

        Intent intent = getIntent();
        String uName = intent.getStringExtra("name");
        String uCity = intent.getStringExtra("city");
        String uPhone = intent.getStringExtra("phoneno");
        String ItemName = intent.getStringExtra("itemname");
        String ItemDesc = intent.getStringExtra("itemdesc");
        String area = intent.getStringExtra("area");
        String Current_Location = intent.getStringExtra("currentlocation");
        String Image = intent.getStringExtra("Image");
        key = intent.getStringExtra("key");
        userkey = intent.getStringExtra("Userkey");

        phone = findViewById(R.id.xUserPhone);
        itemname = findViewById(R.id.xItemName);
        itemdesc = findViewById(R.id.xItemDesc);
        areax = findViewById(R.id.xUserarea);
        currentlocationx = findViewById(R.id.xCurrentLocation);
        imageViewd = findViewById(R.id.singleDonationImg);
        Button btn = findViewById(R.id.xacceptdonation);

        Picasso.get().load(Image).into(imageViewd);

        name.setText("User Name : "+uName);
        city.setText("User City : "+uCity);
        phone.setText("User Phone : "+uPhone);
        itemname.setText("Donation Name : "+ItemName);
        itemdesc.setText("Donation Description : "+ItemDesc);
        areax.setText("Donation Location : "+area);
        currentlocationx.setText("Current Location : "+Current_Location);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                updateDatabase();

//                Toast.makeText(AIDonationData.this,"Donation Accepted",Toast.LENGTH_LONG).show();
            }
        });

    }




    void updateDatabase(){
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        database.getReference("Donater").child(userkey).child("donations").child(key).child("Current Location").setValue("Area Incharge");


    }



}