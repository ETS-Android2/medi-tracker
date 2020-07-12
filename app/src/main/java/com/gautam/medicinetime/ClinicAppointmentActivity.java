package com.gautam.medicinetime;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class ClinicAppointmentActivity extends AppCompatActivity {
    TextView clN;
    TextView clN1;
    FirebaseAuth mAuth;
    private String userId;
    FirebaseFirestore fstore;
    private TextView check;
    private TextView mName;
    private TextView memail;
    private TextView mMo;
    private Spinner spinner;
    private Button updateBut;
    public String time;
    private String id;
    SharedPreferences sharedPreferences;
    public static final String filename = "login";
    public static final String Username = "username";
    public static final String Password = "password";
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    String date;
    String date1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clinic_appointment);
        Spinner dropdown = findViewById(R.id.spinner1);
        String[] items = new String[]{"9:00 AM to 9:30 AM", "9:30 AM to 10:00 AM", "10:00 AM to 10:30 AM","10:30 AM to 11:00 AM","11:00 AM to 11:30 AM","11:30 AM to 12:00 PM"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);
        Intent intent = getIntent();
        String str = intent.getStringExtra("Clinic_Name");
        //String str1 = intent.getStringExtra("Clinic_Username");
        clN = findViewById(R.id.textv);
        clN1 = findViewById(R.id.datepick);
        check = findViewById(R.id.check);
        clN.setText(str);
        TextView txt_slot = findViewById(R.id.txt_slot);
        txt_slot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_slot = new Intent(v.getContext(), AppointmentSlotsActivity.class);
                intent_slot.putExtra("Clinic_Name", str);
                startActivity(intent_slot);
            }
        });
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
                CollectionReference allUsersRef = rootRef.collection("DrAppointment").document(id).collection("DrAppointment").document(date1).collection("DrAppointment");
                Query userNameQuery = allUsersRef.whereEqualTo("time", spinner.getSelectedItem().toString().trim());
                userNameQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<String> tym = new ArrayList<>();
                            for (DocumentSnapshot document : task.getResult()) {
                                if ( document.exists()) {
                                    String userName = document.getString("username");
                                    check.setError("Choose different time");
                                    updateBut.setVisibility(View.INVISIBLE);
                                    Toast.makeText(ClinicAppointmentActivity.this, "Time Taken", Toast.LENGTH_SHORT).show();

                                }
                            }
                        } else {
                            Toast.makeText(ClinicAppointmentActivity.this, "Time Taken"+task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }

                });

                check.setError(null);

                updateBut.setVisibility(View.VISIBLE);
            }
        });
        clN1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DATE);
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        ClinicAppointmentActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year,month,day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int Date) {
                month = month + 1;
                date = month +"/"+Date+"/"+year;
                date1 = ""+month+Date+year;
                SimpleDateFormat timeStampFormat = new SimpleDateFormat("MMddyyyy");
                Date myDate = new Date(month+Date+year);
                String filename = timeStampFormat.format(myDate);
                clN1.setText(date1);
                Toast.makeText(ClinicAppointmentActivity.this,"DAte"+date,Toast.LENGTH_SHORT).show();
            }
        };

        mAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        mName = findViewById(R.id.edtname);
        memail = findViewById(R.id.edtemail);
        mMo = findViewById(R.id.edtmo);
        spinner = findViewById(R.id.spinner1);
        updateBut = findViewById(R.id.updatebtn);
        userId = mAuth.getCurrentUser().getUid();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        sharedPreferences = getSharedPreferences(filename, Context.MODE_PRIVATE);
        if (sharedPreferences.contains(Username)){
            if (mAuth.getCurrentUser() == null) {
                Toast.makeText(ClinicAppointmentActivity.this, "Error h ", Toast.LENGTH_SHORT).show();
            } else {
                DocumentReference documentReference = fstore.collection("Clinic").document();
                documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                        if (str.equals(documentSnapshot.getString("Clinic_Name"))) {
                        String id = documentSnapshot.getId();
                        //Toast.makeText(ClinicAppointmentActivity.this,"USERNAME"+id,Toast.LENGTH_SHORT).show();
                        }
                        else{
                            //Toast.makeText(ClinicAppointmentActivity.this,"ERROR",Toast.LENGTH_SHORT).show();
                            fstore.collection("Clinic").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            if (str.equals(document.getString("Clinic_Name"))) {
                                                id = document.getString("userId");
                                                //Toast.makeText(ClinicAppointmentActivity.this,"ERROR"+id,Toast.LENGTH_SHORT).show();
                                            } else {
                                                //Toast.makeText(ClinicAppointmentActivity.this,"ERROR",Toast.LENGTH_SHORT).show();

                                            }
                                        }
                                    }
                                    else {
                                        //Toast.makeText(ClinicAppointmentActivity.this,"ERROR",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                        }
                    }
                });
            }
        }
        updateBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = memail.getText().toString().trim();
                final String name = mName.getText().toString().trim();
                final String mo = mMo.getText().toString().trim();
                time = spinner.getSelectedItem().toString().trim();

                if (TextUtils.isEmpty(email)){
                    memail.setError("Email is required");
                    return;
                }
                if(!CheckEmail(email)){
                    memail.setError("Wrong Email");

                }
                if (TextUtils.isEmpty(name)){
                    memail.setError("Name is required");
                    return;
                }
                if (TextUtils.isEmpty(mo)){
                    memail.setError("Mobile Number is required");
                    return;
                }

                Map<String,Object> user = new HashMap<>();
                user.put("Clinic_Name",str);
                user.put("Name", name);
                user.put("Email",email);
                user.put("mo",mo);
                user.put("time",time);
                user.put("Date",date);
                user.put("Clinic_id",id);
                Map<String,Object> user3 = new HashMap<>();
                user3.put("Clinic_Name",str);
                user3.put("Name", name);
                user3.put("Email",email);
                user3.put("mo",mo);
                user3.put("time",time);
                user3.put("Date",date);
                user3.put("Patient_id",userId);
                Map<String,Object> user1 = new HashMap<>();
                user1.put("Clinic_Name",str);
                user1.put("Name", name);
                user1.put("Email",email);
                user1.put("mo",mo);
                user1.put("time",time);
                user1.put("Date",date);
                user1.put("Patientid",userId);
                String string = time;

                db.collection("DrAppointment")
                        .document(id)
                        .collection("DrAppointment")
                        .document(String.valueOf(date1))
                        .collection("DrAppointment")
                        .add(user1)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Log.d("TAG", "DocumentSnapshot added with ID: " + documentReference.getId()+"For Doctor");
                                Toast.makeText(ClinicAppointmentActivity.this,"Appointment Booked ",Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w("TAG", "Error adding document", e);
                            }
                        });
                db.collection("DrNotification")
                        .document(id)
                        .collection("DrNotification")
                        .add(user3)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Log.d("TAG", "DocumentSnapshot added with ID: " + documentReference.getId());
                                //Toast.makeText(ClinicAppointmentActivity.this,"DocumentSnapshot added with ID: " + documentReference.getId(),Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w("TAG", "Error adding document", e);
                            }
                        });
                db.collection("Appointments")
                        .document(userId)
                        .collection("Appointments")
                        .add(user)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Log.d("TAG", "DocumentSnapshot added with ID: " + documentReference.getId());
                                //Toast.makeText(ClinicAppointmentActivity.this,"DocumentSnapshot added with ID: " + documentReference.getId(),Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w("TAG", "Error adding document", e);
                            }
                        });

            }

            private boolean CheckEmail(String EmailAddress) {
                return isValid(EmailAddress);
            }
            private boolean isValid(String email){
                String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                        "[a-zA-Z0-9_+&*-]+)*@" +
                        "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                        "A-Z]{2,7}$";
                Pattern pat = Pattern.compile(emailRegex);
                if (email == null){
                    return false;
                }
                return pat.matcher(email).matches();
            }
        });
//
//        fstore.collection("Clinic").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                if (task.isSuccessful()) {
//
//                }
//            }
//        });

     }
}
