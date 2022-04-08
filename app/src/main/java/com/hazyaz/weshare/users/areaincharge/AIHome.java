package com.hazyaz.weshare.users.areaincharge;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.hazyaz.weshare.R;

public class AIHome extends AppCompatActivity {
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ai_home);

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onStart() {
        super.onStart();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        String AILogged =prefs.getString("AreaIncharge","");

        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser==null && AILogged.equals("LoggedIn")){
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, AILogin.class, null)
                    .commit();
        }
    }
}