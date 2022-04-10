package com.hazyaz.weshare.users.donater;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.BugreportManager;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hazyaz.weshare.R;
import com.hazyaz.weshare.users.areaincharge.AIHome;

import java.util.HashMap;

public class DonaterRegister extends AppCompatActivity {

    String area[] = {"Mumbai-Central", "Mahim", "Nallasopara", "Andheri", "Byculla", "Jogeshwari"};
    EditText don_name, don_password, don_email,don_city, don_phone;
    Spinner donAreaSpinner;
    Button register_don_button;
    ProgressDialog progressDialog;

    @Nullable
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.donater_register);

        don_name = findViewById(R.id.don_reg_name);
        don_password = findViewById(R.id.don_reg_password);
        don_email = findViewById(R.id.don_reg_email);
        don_phone = findViewById(R.id.don_reg_PhoneNo);
        don_city = findViewById(R.id.don_city);
        register_don_button = findViewById(R.id.don_reg_submit);

        donAreaSpinner = findViewById(R.id.area_spinner_don_register);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item,
                        area); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);
        donAreaSpinner.setAdapter(spinnerArrayAdapter);
        progressDialog = new ProgressDialog(this, 4);

        register_don_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String name, pass, email, phone, city, area;

                name = don_name.getText().toString();
                pass = don_password.getText().toString();
                email = don_email.getText().toString();
                phone = don_phone.getText().toString();
                city = don_city.getText().toString();
                area = donAreaSpinner.getSelectedItem().toString();


                if (email.equals("") || name.equals("") || phone.equals("") ||
                        city.equals("") || pass.equals("") || area.equals("")) {
                    Toast.makeText(getApplicationContext(), "fill all", Toast.LENGTH_SHORT).show();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    don_email.setError("Invalided Email");
                    don_email.setFocusable(true);

                } else if (pass.length() < 6) {
                    don_password.setError("Password length at least 6 characters");
                    don_password.setFocusable(true);
                }

                registerDonater(name, pass, email, phone, city, area);
            }
        });

    }


     void registerDonater(String name, String pass, String email, String phone, String city, String area){
         ProgressDialog progressDialog
                 = new ProgressDialog(this);
         progressDialog.setTitle("Registering User");
         progressDialog.setMessage("Please wait, registration in progress");
         progressDialog.show();

         final FirebaseAuth mAuth=FirebaseAuth.getInstance();
         mAuth.createUserWithEmailAndPassword(email,pass)
                 .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                     @Override
                     public void onComplete(@NonNull Task<AuthResult> task) {

                         if (task.isSuccessful()) {


                             progressDialog.dismiss();
                             FirebaseUser user = mAuth.getCurrentUser();

                             String uid=user.getUid();

                             HashMap<Object,String> hashMap=new HashMap<>();

                             hashMap.put("Name",name);
                             hashMap.put("Email",email);
                             hashMap.put("Phone",phone);
                             hashMap.put("city",city);
                             hashMap.put("area",area);
                             hashMap.put("Password",pass);
                             hashMap.put("uid",uid);

                             FirebaseDatabase database=FirebaseDatabase.getInstance();

                             DatabaseReference reference=database.getReference("Donater");
                             reference.child(uid).setValue(hashMap);
                             //sucess
                             startActivity(new Intent(DonaterRegister.this, DonaterHome.class));
                         }
                         else {
                             progressDialog.dismiss();
                             Toast.makeText(getApplicationContext(), "Authentication Failed", Toast.LENGTH_SHORT).show();
                         }


                     }
                 }).addOnFailureListener(new OnFailureListener() {
             @Override
             public void onFailure(@NonNull Exception e) {
                 progressDialog.dismiss();
                 Toast.makeText(getApplicationContext(),""+e.getMessage(),Toast.LENGTH_SHORT).show();
             }
         });


     }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }



}
