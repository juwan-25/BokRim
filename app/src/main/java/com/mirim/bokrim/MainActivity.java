package com.mirim.bokrim;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.mirim.bokrim.Datas.Benefit;

import java.security.MessageDigest;

public class MainActivity extends AppCompatActivity implements FragmentListener {

    HistoryFragment historyFragment = new HistoryFragment();
    MapFragment mapFragment = new MapFragment();
    BenefitFragment benefitFragment = new BenefitFragment();
    Benefit2Fragment benefit2Fragment = new Benefit2Fragment();
    EventFragment eventFragment = new EventFragment();
    PlaceFragment placeFragment = new PlaceFragment();
    ClosetFragment closetFragment = new ClosetFragment();
    HistoryDetailFragment historyDetailFragment = new HistoryDetailFragment();

    MapSearchFragment mapSearchFragment = new MapSearchFragment();
    
    FragmentTransaction transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameLayout, benefitFragment);
        transaction.replace(R.id.frameLayout, benefit2Fragment);

        setContentView(R.layout.activity_main);
        gethash();
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, historyFragment).commit();

        BottomNavigationView bottomNavigationView = findViewById(R.id.btn_navi_menu);
        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener(){
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                switch(item.getItemId()){
                    case R.id.tap_history:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, historyFragment).commit();
                        return true;
                    case R.id.tap_map:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, mapFragment).commit();
                        return true;
                    case R.id.tap_benefit:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, benefitFragment).commit();
                        return true;
                    case R.id.tap_closet:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, closetFragment).commit();
                        return true;
                }
                return true;
            }
        });
        
        
    }
    //프래그먼트 바꾸기
    public void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frameLayout, fragment).addToBackStack(null).commit();
    }

    private void gethash() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md;
                md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String something = new String(Base64.encode(md.digest(), 0));
                Log.d("Hash key", something);
            }
        } catch (Exception e) {
            Log.e("name not found", e.toString());
        }
    }

    @Override
    public void onCommand(int index, String data) {
        switch (index){
            case 0: // MapParentFragment =>
                mapSearchFragment.displayMessage(data);
                mapFragment.displayMessage(data);
                break;
            case 1:
                eventFragment.displayMessage(data);
                break;
            case 2:
                placeFragment.displayMessage(data);
                break;
            case 3:
                historyDetailFragment.displayMessage(data);
                break;

        }
    }
}