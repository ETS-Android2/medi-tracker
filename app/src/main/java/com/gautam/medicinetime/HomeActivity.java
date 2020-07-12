package com.gautam.medicinetime;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.gautam.medicinetime.ui.home.HomeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.annotations.Nullable;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class HomeActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private String data;
    private FirebaseFirestore fstore;
    private String userId;
    SharedPreferences sharedPreferences;
    public static final String filename = "login";
    public static final String Username = "username";
    public static final String Password = "password";
    TextView type;
    @Override
    protected void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser() == null) {
            //Toast.makeText(HomeActivity.this, "Log in ", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(HomeActivity.this, MainActivity.class));
        } else {
            /*Intent intent = new Intent(HomeActivity.this, AlarmReceiver.class);
            PendingIntent alarmIntent = PendingIntent.getBroadcast(HomeActivity.this, 22222,
                    intent, PendingIntent.FLAG_IMMUTABLE);

            AlarmManager alarm = (AlarmManager) getSystemService(ALARM_SERVICE);

            fstore.collection("AlarmTime")
                    .document(userId)
                    .collection("AlarmTime")
                    .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        List<Long> time = new ArrayList<Long>();
                        //List<String> time = new ArrayList<>();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            //name.add(document.getString("AlarmTime"));
                            //String alarmTime = document.getString("AlarmTime");
                            time.add(document.getLong("AlarmTime"));
                            //for (int i = 0; i<=time.size(); i++) {
                            Long tt = document.getLong("AlarmTime");
                            //Toast.makeText(SetAlarm.this,""+tt,Toast.LENGTH_SHORT).show();
                            //mTitle = new String[]{document.getString("Clinic_Name"),document.getString("Clinic_Name")};
                            alarm.setInexactRepeating(AlarmManager.RTC_WAKEUP, tt, AlarmManager.INTERVAL_DAY, alarmIntent);
                            //}
                            //UserMedicinesActivity.MyAdapter adapter = new UserMedicinesActivity.MyAdapter(UserMedicinesActivity.this, name, time);
                            //listView.setAdapter(adapter);
                        }
                        //Log.d(TAG, list.toString());
                        //Toast.makeText(HomeActivity.this,"Total Clinics:"+alarmtime,Toast.LENGTH_SHORT).show();

                    } else {
                        //Log.d(TAG, "Error getting documents: ", task.getException());
                        // Toast.makeText(UserMedicinesActivity.this,""+task.getException(),Toast.LENGTH_SHORT).show();
                    }
                }
            });*/
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        //NavigationUI.setupActionBarWithNavController(HomeActivity.this,navController,appBarConfiguration);
        //NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
        //ActionBar actionBar = getSupportActionBar();
        //actionBar.hide();
        mAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();


        sharedPreferences = getSharedPreferences(filename, Context.MODE_PRIVATE);
        if (sharedPreferences.contains(Username)){
            if (mAuth.getCurrentUser() == null) {
                Toast.makeText(HomeActivity.this, "Login Error Found ", Toast.LENGTH_SHORT).show();

            } else {
                userId = mAuth.getCurrentUser().getUid();
                DocumentReference documentReference = fstore.collection("users").document(userId);
                documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
//                        if ("Doctor".equals(documentSnapshot.getString("type"))) {
//                            Toast.makeText(HomeActivity.this, "Welcome Doctor", Toast.LENGTH_SHORT).show();
//                            Intent intent = new Intent(HomeActivity.this, DoctorActivity.class);
//                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                            startActivity(intent);
//                            finish();
//                        }
                    }
                });

            }
        }
        /*if (mAuth.getCurrentUser() == null) {
            Toast.makeText(HomeActivity.this, "Log in ", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(HomeActivity.this, MainActivity.class));
        } else {
            userId = mAuth.getCurrentUser().getUid();
            Toast.makeText(HomeActivity.this, "Done", Toast.LENGTH_SHORT).show();
            type = findViewById(R.id.type);

            DocumentReference documentReference = fstore.collection("users").document(userId);
            documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                   // type.setText(documentSnapshot.getString("type"));

                }
            });
            Toast.makeText(HomeActivity.this, "Welcome"+type, Toast.LENGTH_SHORT).show();
        }*/



    }


}
