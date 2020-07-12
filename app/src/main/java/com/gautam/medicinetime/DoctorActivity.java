package com.gautam.medicinetime;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DoctorActivity extends AppCompatActivity {
    private FirebaseDatabase mfirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myref;
    private BottomNavigationView bottomNavigationView;
    private FrameLayout frameLayout;

    private HomeDrFragment homeDrFragment;
    private DashboardDrFragment dashboardDrFragment;
    private AccountDrFragment accountDrFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor);
        mAuth = FirebaseAuth.getInstance();
        mfirebaseDatabase = FirebaseDatabase.getInstance();
        myref = mfirebaseDatabase.getReference();
        //RelativeLayout constraintLayout = findViewById(R.id.layout);
        //ConstraintLayout constraintLayout = findViewById(R.id.layout);
        //AnimationDrawable animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
        //animationDrawable.setEnterFadeDuration(2000);
        //animationDrawable.setExitFadeDuration(4000);
        //animationDrawable.start();
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_nav_dr);
        frameLayout = (FrameLayout) findViewById(R.id.dr_frame);
        homeDrFragment = new HomeDrFragment();

        dashboardDrFragment = new DashboardDrFragment();
        accountDrFragment = new AccountDrFragment();
        //startActivity(new Intent(DoctorActivity.this, ScrollingActivity.class));
        setFragment(homeDrFragment);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home_dr:
                        setFragment(homeDrFragment);
                        //startActivity(new Intent(DoctorActivity.this, ScrollingActivity.class));

                        return true;
//                    case R.id.Dashboard_dr:
//                        setFragment(dashboardDrFragment);
//                        return true;
                    case R.id.account_dr:
                        setFragment(accountDrFragment);
                        return true;
                }
                return false;
            }
        });
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.dr_frame,fragment);
        fragmentTransaction.commit();
    }


}