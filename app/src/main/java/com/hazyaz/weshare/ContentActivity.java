package com.hazyaz.weshare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.hazyaz.weshare.users.donater.DonaterRegister;


public class ContentActivity extends AppCompatActivity {

Button bttt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        Toast.makeText(getApplicationContext(),"Sdfsdfsdf",Toast.LENGTH_LONG).show();

        bttt = findViewById(R.id.becomeDonater);
        bttt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Sdfsdfsdf",Toast.LENGTH_LONG).show();
                startActivity(new Intent(ContentActivity.this, DonaterRegister.class));
            }
        });

    }
}