package com.hazyaz.weshare.users.areaincharge;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
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

import java.util.HashMap;

public class AIRegister extends Fragment {

    String area[] = {"Mumbai-Central", "Mahim", "Nallasopara", "Andheri", "Byculla", "Jogeshwari"};
    EditText ai_name, ai_password, ai_email,ai_city, ai_phone, ai_;
    Spinner AreaSpinner;
    Button register_ai_button;
    ProgressDialog progressDialog;

    AIRegister(){

    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View rootViewAI = inflater.inflate(R.layout.ai_register,
                container, false);


        ai_name = rootViewAI.findViewById(R.id.ai_reg_name);
        ai_password = rootViewAI.findViewById(R.id.ai_reg_password);
        ai_email = rootViewAI.findViewById(R.id.ai_reg_email);
        ai_phone = rootViewAI.findViewById(R.id.ai_reg_PhoneNo);
        ai_city = rootViewAI.findViewById(R.id.ai_reg_city);
        register_ai_button = rootViewAI.findViewById(R.id.ai_reg_submit);

        AreaSpinner = rootViewAI.findViewById(R.id.area_spinner_ai_register);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                (getContext(), android.R.layout.simple_spinner_item,
                        area); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);
        AreaSpinner.setAdapter(spinnerArrayAdapter);
        progressDialog=new ProgressDialog(getContext(),4);


        register_ai_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name, pass, email, phone, city, area;

                name = ai_name.getText().toString();
                pass = ai_password.getText().toString();
                email = ai_email.getText().toString();
                phone = ai_phone.getText().toString();
                city = ai_city.getText().toString();
                area = AreaSpinner.getSelectedItem().toString();

                if(email.equals("")||name.equals("")||phone.equals("")||
                        city.equals("")||pass.equals("") ||area.equals("")     ){
                    Toast.makeText(getContext(), "fill all the data ", Toast.LENGTH_SHORT).show();
                }
                else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    ai_email.setError("Invalided Email");
                    ai_email.setFocusable(true);

                }
                else if(pass.length()<6){
                    ai_password.setError("Password length at least 6 characters");
                    ai_password.setFocusable(true);
                }
            // Sending data to firebase
                RegisterAreaIncharge(name,pass,email,phone,city,area);
            }
        });
        return rootViewAI;
    }

    void RegisterAreaIncharge(String name, String pass, String email, String phone, String city,String area){
        progressDialog.setMessage("Loading....");
        progressDialog.show();
        final FirebaseAuth mAuth=FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(email,pass)
                .addOnCompleteListener((Activity) getContext(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {

                            String zero="0";
                            progressDialog.dismiss();
                            FirebaseUser user = mAuth.getCurrentUser();

                            String email= user.getEmail();
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

                            DatabaseReference reference=database.getReference("AreaIncharge");
                            reference.child(name).setValue(hashMap);
                            //sucess
                            startActivity(new Intent(getContext(), AIHome.class));
                        }
                        else {
                            progressDialog.dismiss();
                            Toast.makeText(getContext(), "Authentication Failed", Toast.LENGTH_SHORT).show();
                        }


                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(getContext(),""+e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });


    }





}
