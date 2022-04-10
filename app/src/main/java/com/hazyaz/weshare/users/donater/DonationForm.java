package com.hazyaz.weshare.users.donater;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AppComponentFactory;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.hardware.camera2.TotalCaptureResult;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
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
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.hazyaz.weshare.R;
import com.hazyaz.weshare.users.areaincharge.AIHome;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;

import static android.app.Activity.RESULT_OK;

public class DonationForm extends AppCompatActivity {
    private static final int CAMERA_REQUEST = 1888;

    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    EditText donater_name, donater_city, donater_phone, donation_name, donation_desciption;
    Spinner donation_area;
    Button submitDonation,UploadImage;
    String area[] = {"Mumbai-Central", "Mahim", "Nallasopara", "Andheri", "Byculla", "Jogeshwari"};
    ProgressDialog progressDialog;
    private static final int CAMERA_REQUEST_CODE=1;
    Uri downloadUri;
    private StorageReference mStorage;
    private Uri mImageUri = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.donater_form);




            donater_name = findViewById(R.id.donater_name);
            donater_city = findViewById(R.id.donater_city);
            donater_phone = findViewById(R.id.donater_phone);
            donation_name = findViewById(R.id.donation_name);
            donation_desciption = findViewById(R.id.donation_description);
            donation_area = findViewById(R.id.donater_area_spinner);
            UploadImage = findViewById(R.id.ItemImagexxx);
            submitDonation = findViewById(R.id.submit_donation_form);

            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                    (this, android.R.layout.simple_spinner_item,
                            area); //selected item will look like a spinner set from XML
            spinnerArrayAdapter.setDropDownViewResource(android.R.layout
                    .simple_spinner_dropdown_item);
            donation_area.setAdapter(spinnerArrayAdapter);
            progressDialog = new ProgressDialog(this, 3);


            submitDonation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    progressDialog.setMessage("Donation Request Sending");
                    progressDialog.show();


                    String donp_name, donp_city, donp_phone, don_name, don_desc, area;

                    donp_name = donater_name.getText().toString();
                    donp_city = donater_city.getText().toString();
                    donp_phone = donater_phone.getText().toString();
                    don_name = donation_name.getText().toString();
                    don_desc = donation_desciption.getText().toString();
                    area = donation_area.getSelectedItem().toString();


                    if (donp_name.equals("") || donp_city.equals("") || donp_phone.equals("") ||
                            don_name.equals("") || don_desc.equals("") || area.equals("")) {
                        Toast.makeText(DonationForm.this, "fill all the data ", Toast.LENGTH_SHORT).show();
                    }
                    filledDonationForm(donp_name, donp_city, donp_phone, don_name, don_desc, area);
                }
            });


            UploadImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
                    {
                        requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
                    }
                    else
                    {
                        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(cameraIntent, CAMERA_REQUEST);
                        Log.d("urdsf", "33333333333333333333");
                    }


                }
            });
        }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("urdsf", ""+data.getData());
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {

            Bitmap photo = (Bitmap) data.getExtras().get("data");
            Log.d("urdsf", "" + photo);
            mImageUri = getImageUri(getApplicationContext(),photo);
            Log.d("urdsf", ""+mImageUri);
        }
    }


    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE)
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
            else
            {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }



    void filledDonationForm(String name, String city, String phone, String donation_name, String donation_desc, String donation_area) {

        ProgressDialog progressDialog
                = new ProgressDialog(this);
        progressDialog.setTitle("Uploading Data Please Wait...");
        progressDialog.show();


        mStorage = FirebaseStorage.getInstance().getReference();
        StorageReference filepath = mStorage.child("Images").child(mImageUri.getLastPathSegment());
        filepath.putFile(mImageUri).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }
                return filepath.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    downloadUri = task.getResult();
                    putAllContent(name, city, phone, donation_name, donation_desc, donation_area);
                    System.out.println("Upload success: " + downloadUri);
                } else {

                }
            }
        });


    }


    void putAllContent(String name, String city, String phone, String donation_name, String donation_desc, String donation_area){

        final FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        String uid = user.getUid();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("Donater").child(uid);
        HashMap<Object, String> hashMap = new HashMap<>();
        String key = reference.push().getKey();

        hashMap.put("Donater Name", name);
        hashMap.put("Donater City", city);
        hashMap.put("Donater Phone", phone);
        hashMap.put("Donation Name", donation_name);
        hashMap.put("Donation Desc", donation_desc);
        hashMap.put("Donation Area", donation_area);
        hashMap.put("TimeStamp", "" + System.currentTimeMillis());
        hashMap.put("Current Location", "Donater");
        hashMap.put("Image",""+downloadUri);


        reference.child("donations").child(key).setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful()) {
                    progressDialog.dismiss();

                    Toast.makeText(DonationForm.this, "Data Sent Succeddult ", Toast.LENGTH_LONG).show();
                    finish();

                } else {
                    Toast.makeText(DonationForm.this, "Cannot send data to server ", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    }







