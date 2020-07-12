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

public class MedicalActivity extends AppCompatActivity {
    private FirebaseDatabase mfirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myref;
    private BottomNavigationView bottomNavigationView;
    private FrameLayout frameLayout;

    private HomeMedicalFragment homeMedicalFragment;

    private AccountMedicalFragment accountMedicalFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical);
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
        frameLayout = (FrameLayout) findViewById(R.id.medical_frame);
        homeMedicalFragment = new HomeMedicalFragment();
        accountMedicalFragment = new AccountMedicalFragment();

        //startActivity(new Intent(DoctorActivity.this, ScrollingActivity.class));
        setFragment(homeMedicalFragment);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home_medical:
                        setFragment(homeMedicalFragment);
                        //startActivity(new Intent(DoctorActivity.this, ScrollingActivity.class));

                        return true;
                    case R.id.account_medical:
                        setFragment(accountMedicalFragment);
                        return true;
                }
                return false;
            }
        });
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.medical_frame,fragment);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.doctor_nav_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.item1:
                if (mAuth.getCurrentUser() != null) {
                    FirebaseAuth.getInstance().signOut();
                    Toast.makeText(MedicalActivity.this, "Logged Out", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MedicalActivity.this, MainActivity.class));
                }
                else {
                    Toast.makeText(MedicalActivity.this, "Errror Out", Toast.LENGTH_SHORT).show();
                }
                //Toast.makeText(this, "Item 1 selected", Toast.LENGTH_SHORT).show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}