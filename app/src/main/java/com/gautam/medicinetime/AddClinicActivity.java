package com.gautam.medicinetime;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class AddClinicActivity extends AppCompatActivity implements View.OnClickListener{
    TextView textViewtm1;
    TextView textViewtm2;
    TextView minute1;
    TextView minute2;
    private String username;
    private String username1;
    //public LottieAnimationView lottieAnimationView;
    private String list;
    private FirebaseDatabase mfirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myref;
    private DatabaseReference myref1;
    private String userId;
    FirebaseFirestore fstore;
    SharedPreferences sharedPreferences;
    public static final String filename = "login";
    public static final String Username = "username";
    public static final String Password = "password";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_clinic);

        textViewtm1 = findViewById(R.id.clinictimepicker1);
        textViewtm2 = findViewById(R.id.clinictimepicker2);
        minute1 = findViewById(R.id.clinicmin);
        minute2 = findViewById(R.id.clinicmin1);
        mAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        userId = mAuth.getCurrentUser().getUid();
        sharedPreferences = getSharedPreferences(filename, Context.MODE_PRIVATE);
        if (sharedPreferences.contains(Username)){
            if (mAuth.getCurrentUser() == null) {
                Toast.makeText(AddClinicActivity.this, "Error h ", Toast.LENGTH_SHORT).show();

            } else {
                userId = mAuth.getCurrentUser().getUid();
                DocumentReference documentReference = fstore.collection("users").document(userId);
                documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                        username = documentSnapshot.getString("Username");
                        //Toast.makeText(AddClinicActivity.this,"USERNAME"+username,Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
        textViewtm1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog(v);

            }
        });
        textViewtm2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog1(v);
            }
        });
    }

    private void showTimePickerDialog1(View v) {
        DialogFragment newFragment = new TimePickerFragment4();
        newFragment.show(getSupportFragmentManager(), "timePicker");
        Intent intent = new Intent(AddClinicActivity.this, TimePickerFragment4.class);
    }

    private void showTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerFragment3();
        newFragment.show(getSupportFragmentManager(), "timePicker");
        Intent intent = new Intent(AddClinicActivity.this, TimePickerFragment3.class);
        //intent.putExtra("notificationId", notificationId);
        //intent.putExtra("alarmStartTime",alarmStartTime1);
        Calendar startTime = Calendar.getInstance();
        startTime.get(Calendar.HOUR_OF_DAY);
        startTime.get(Calendar.MINUTE);
        startTime.set(Calendar.SECOND, 0);
        //alarmStartTime1 = startTime.getTimeInMillis();
        PendingIntent alarmIntent = PendingIntent.getBroadcast(AddClinicActivity.this, 0,
                intent, PendingIntent.FLAG_CANCEL_CURRENT);
        //Toast.makeText(SetAlarm.this,"Time"+alarmStartTime1,Toast.LENGTH_SHORT).show();
        AlarmManager alarm = (AlarmManager) getSystemService(ALARM_SERVICE);
        //alarm.set(AlarmManager.RTC_WAKEUP, alarmStartTime1, alarmIntent);
    }

    @Override
    public void onClick(View v) {
        //lottieAnimationView = findViewById(R.id.sucess_lottie);
        EditText editname = findViewById(R.id.clinic_name);
        EditText editaddress = findViewById(R.id.clinicaddress);
        EditText editcity = findViewById(R.id.clinicCity);
        String hour = textViewtm1.getText().toString().trim();
        String minute = minute1.getText().toString().trim();
        String hour1 = textViewtm2.getText().toString().trim();
        String min2 = minute2.getText().toString().trim();
        fstore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        mfirebaseDatabase = FirebaseDatabase.getInstance();
        myref = mfirebaseDatabase.getReference();
        myref1 = mfirebaseDatabase.getReference().child("ClinicName");
        final String ClinicName = editname.getText().toString().trim();
        final String ClinicAddress = editaddress.getText().toString().trim();
        final String ClinicCity = editcity.getText().toString().trim();
        userId = mAuth.getCurrentUser().getUid();
        //medicineId = mAuth.getCurrentUser().getUid();
        String email = mAuth.getCurrentUser().getEmail();
        DocumentReference documentReference = fstore.collection("Clinic").document();
        Map<String,Object> user = new HashMap<>();
        user.put("Username",username);
        user.put("Clinic_Name", ClinicName);
        user.put("Clinic_Address", ClinicAddress);
        user.put("Clinic_City", ClinicCity);
        user.put("Start_Hour",hour);
        user.put("Start_Minute",minute);
        user.put("End_Hour",hour1);
        user.put("End_Minute",min2);
        user.put("userId",userId);
        documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(AddClinicActivity.this, "Success for" + userId, Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AddClinicActivity.this, "Error=" + e.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        fstore.collection("Clinic").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {

                    for (QueryDocumentSnapshot document : task.getResult()) {
                        //username1.add(document.getString("Username"));
                        username1 = document.getString("Username");
                        //Toast.makeText(AddClinicActivity.this, "Username1========="+ username1, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        if (username == username1){
            Toast.makeText(AddClinicActivity.this,"You have already added Clinic",Toast.LENGTH_SHORT).show();
        }else {

        }
        /*
        FirebaseDatabase.getInstance().getReference().child("CLINIC12");
        db.collection("users")
                .document(userId)
                .collection("Clinic")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("TAG", "DocumentSnapshot added with ID: " + documentReference.getId());
                        Toast.makeText(AddClinicActivity.this,"DocumentSnapshot added with ID: " + documentReference.getId(),Toast.LENGTH_SHORT).show();                     }

                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("TAG", "Error adding document", e);
                    }
                });
*/

    }

    }
