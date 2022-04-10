package com.hazyaz.weshare;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.os.Handler;
import android.widget.Toolbar;

import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import static java.security.AccessController.getContext;

public class ContentActivity extends AppCompatActivity {



    final int[] images ={R.drawable.do1,R.drawable.do2,R.drawable.do3,R.drawable.do4,R.drawable.do5};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);


    }
}