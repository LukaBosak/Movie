package com.progamermobilnih.movie.Activities;


import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.progamermobilnih.movie.Adapters.FragmentAdapter;
import com.progamermobilnih.movie.R;


public class MainActivity extends AppCompatActivity {



    public static final String API_KEY = "fe3b8cf16d78a0e23f0c509d8c37caad";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        TabLayout tabLayout = findViewById(R.id.tabLayoutTL);
        ViewPager viewPager = findViewById(R.id.viewPagerVP);

        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager(),this);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);


    }







}
