package com.hazyaz.weshare.users.admin;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.hazyaz.weshare.R;

import static android.content.ContentValues.TAG;

public class AdminLogin extends Fragment {

    EditText admin_username,admin_password,admin_email;
    Button loginBtn;
    FirebaseAuth mAuth;


    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View rootViewAdmin = inflater.inflate(R.layout.admin_login,
                container, false);

        admin_username=rootViewAdmin.findViewById(R.id.admin_username);
        admin_password=rootViewAdmin.findViewById(R.id.admin_password);
        admin_email=rootViewAdmin.findViewById(R.id.admin_email);
        loginBtn=rootViewAdmin.findViewById(R.id.loginBtn);

        mAuth = FirebaseAuth.getInstance();
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //handle login
                //admin info
                String email,username,password;
                email=admin_email.getText().toString().trim();
                password=admin_password.getText().toString().trim();
                username=admin_username.getText().toString().trim();
                if(username.equals("")){
                    admin_username.setError("Empty");
                    admin_username.setFocusable(true);
                }
                else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    admin_email.setError("Invalided Email");
                    admin_email.setFocusable(true);
                }
                else if(password.length()<6){
                    admin_password.setError("Password length at least 6 characters");
                    admin_password.setFocusable(true);
                }
                else {

                    SharedPreferences.Editor editor;
                    editor= PreferenceManager.getDefaultSharedPreferences(getContext()).edit();
                    editor.putString("admin_username", email.trim());
                    editor.putString("admin_password", password.trim());
                    editor.apply();
                    LoginAdmin(username,email,password);

                }
            }
        });

        return rootViewAdmin;
    }

    void LoginAdmin(String username, String email, String password){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            new Intent(getContext(), AdminHome.class);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(getContext(),"Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                });

    }


}
