package com.mirim.bokrim;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    HistoryFragment historyFragment = new HistoryFragment();
    MapFragment mapFragment = new MapFragment();
    BenefitFragment benefitFragment = new BenefitFragment();
    ClosetFragment closetFragment = new ClosetFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction().replace(R.id.containers, historyFragment).commit();

        BottomNavigationView bottomNavigationView = findViewById(R.id.btn_navi_menu);
        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener(){
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                switch(item.getItemId()){
                    case R.id.tap_history:
                        getSupportFragmentManager().beginTransaction().replace(R.id.containers, historyFragment).commit();
                        return true;
                    case R.id.tap_map:
                        getSupportFragmentManager().beginTransaction().replace(R.id.containers, mapFragment).commit();
                        return true;
                    case R.id.tap_benefit:
                        getSupportFragmentManager().beginTransaction().replace(R.id.containers, benefitFragment).commit();
                        return true;
                    case R.id.tap_closet:
                        getSupportFragmentManager().beginTransaction().replace(R.id.containers, closetFragment).commit();
                        return true;
                }
                return true;
            }
        });
    }

}