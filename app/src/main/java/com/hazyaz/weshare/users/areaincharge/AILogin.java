package com.hazyaz.weshare.users.areaincharge;

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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.hazyaz.weshare.R;
import com.hazyaz.weshare.users.admin.AdminHome;

import static android.content.ContentValues.TAG;

public class AILogin extends Fragment {

    EditText ai_username,ai_password,ai_email;
    Button loginBtn;
    FirebaseAuth mAuth;

    AILogin(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootViewAdmin = inflater.inflate(R.layout.admin_login,
                container, false);

        ai_username=rootViewAdmin.findViewById(R.id.ai_username);
        ai_password=rootViewAdmin.findViewById(R.id.ai_password);
        ai_email=rootViewAdmin.findViewById(R.id.ai_email);
        loginBtn=rootViewAdmin.findViewById(R.id.ai_loginBtn);
        mAuth = FirebaseAuth.getInstance();

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //handle login
                //admin info
                String email,username,password;
                email=ai_email.getText().toString().trim();
                password=ai_password.getText().toString().trim();
                username=ai_username.getText().toString().trim();
                if(username.equals("")){
                    ai_username.setError("Empty");
                    ai_username.setFocusable(true);
                }
                else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    ai_email.setError("Invalided Email");
                    ai_email.setFocusable(true);
                }
                else if(password.length()<6){
                    ai_password.setError("Password length at least 6 characters");
                    ai_password.setFocusable(true);
                }
                else {

                    SharedPreferences.Editor editor;
                    editor= PreferenceManager.getDefaultSharedPreferences(getContext()).edit();
                    editor.putString("admin_username", email.trim());
                    editor.putString("admin_password", password.trim());
                    editor.apply();
                    LoginAreaIncharge(username,email,password);

                }
            }
        });
        return rootViewAdmin;
    }

    void LoginAreaIncharge(String username, String email, String password)
    {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            new Intent(getContext(), AreaIncharge_Home.class);
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
