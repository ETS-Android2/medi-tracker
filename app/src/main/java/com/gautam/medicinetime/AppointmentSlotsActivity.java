package com.gautam.medicinetime;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.sundeepk.compactcalendarview.AnimationListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AppointmentSlotsActivity extends AppCompatActivity {
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    String date;
    String date1;
    ListView listView;
    FirebaseFirestore fstore;
    String userId;
    FirebaseAuth mAuth;
    private String id;
    SharedPreferences sharedPreferences;
    public static final String filename = "login";
    public static final String Username = "username";
    public static final String Password = "password";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_slots);
        TextView clN1 = findViewById(R.id.datepick);
        TextView clN2 = findViewById(R.id.datelbl);
        listView = findViewById(R.id.listView);

        mAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        userId = mAuth.getCurrentUser().getUid();
        Intent intent = getIntent();
        String str = intent.getStringExtra("Clinic_Name");

        clN1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DATE);
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        AppointmentSlotsActivity.this,
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
                date = Date +"/"+month+"/"+year;
                date1 = ""+month+Date+year;
                SimpleDateFormat timeStampFormat = new SimpleDateFormat("MMddyyyy");
                java.util.Date myDate = new Date(month+Date+year);
                String filename = timeStampFormat.format(myDate);
                clN1.setText(date1);
                clN1.setVisibility(View.INVISIBLE);
                clN2.setText(date);

                //Toast.makeText(AppointmentSlotsActivity.this,"DAte"+date,Toast.LENGTH_SHORT).show();
                if (date1 == null){
                    Toast.makeText(AppointmentSlotsActivity.this, "Chooose Date", Toast.LENGTH_SHORT).show();
                }
                else {
                    fstore.collection("DrAppointment").document(id).collection("DrAppointment").document(date1).collection("DrAppointment").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                List<String> namelist = new ArrayList<>();
                                List<String> usernamelist = new ArrayList<>();
                                List<String> starthlist = new ArrayList<>();
                                //List<String> startmlist = new ArrayList<>();
                                //List<String> endhlist = new ArrayList<>();
                                //List<String> endmlist = new ArrayList<>();

                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    namelist.add(document.getString("Clinic_Name"));
                                    usernamelist.add(document.getString("Date"));
                                    starthlist.add(document.getString("time"));
                                    //startmlist.add(document.getString("Name"));
                                    //endhlist.add(document.getString("mo"));
                                    //endmlist.add(document.getString("Email"));

                                    //mTitle = new String[]{document.getString("Clinic_Name"),document.getString("Clinic_Name")};
                                    //adapter adp = new adapter(UserClinicActivity.this, namelist, usernamelist,starthlist,startmlist,endhlist,endmlist);
                                    MyAdapter adapter = new MyAdapter(AppointmentSlotsActivity.this, namelist, usernamelist,starthlist);
                                    listView.setAdapter(adapter);
                                }
                                //Log.d(TAG, list.toString());
                                //Toast.makeText(UserClinicActivity.this,"Total Clinics:"+countdb,Toast.LENGTH_SHORT).show();
                            } else {
                                //Log.d(TAG, "Error getting documents: ", task.getException());
                                Toast.makeText(AppointmentSlotsActivity.this,""+task.getException(),Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }
            }
        };

        sharedPreferences = getSharedPreferences(filename, Context.MODE_PRIVATE);
        if (sharedPreferences.contains(Username)) {
            if (mAuth.getCurrentUser() == null) {
                Toast.makeText(AppointmentSlotsActivity.this, "Error h ", Toast.LENGTH_SHORT).show();
            } else {
                DocumentReference documentReference = fstore.collection("Clinic").document();
                documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                        if (str.equals(documentSnapshot.getString("Clinic_Name"))) {
                            String id = documentSnapshot.getId();
                            //Toast.makeText(ClinicAppointmentActivity.this,"USERNAME"+id,Toast.LENGTH_SHORT).show();
                        } else {
                            //Toast.makeText(ClinicAppointmentActivity.this,"ERROR",Toast.LENGTH_SHORT).show();
                            fstore.collection("Clinic").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            if (str.equals(document.getString("Clinic_Name"))) {
                                                id = document.getString("userId");
                                                //Toast.makeText(AppointmentSlotsActivity.this,"Dr. Id"+id,Toast.LENGTH_SHORT).show();
                                            } else {
                                                //Toast.makeText(ClinicAppointmentActivity.this,"ERROR",Toast.LENGTH_SHORT).show();

                                            }
                                        }
                                    } else {
                                        //Toast.makeText(ClinicAppointmentActivity.this,"ERROR",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                        }
                    }
                });
            }
        }


    }
    public class MyAdapter extends ArrayAdapter<String>{
        Context context;
        List<String> rTitle;
        List<String> rusername;
        List<String> rSt_hr;

        MyAdapter (Context c, List<String> title, List<String> username, List<String> st_hr){
        super(c, R.layout.user_appointment, R.id.textView1, title);
        this.context = c;
        this.rTitle = title;
        this.rusername = username;
        this.rSt_hr = st_hr;

    }

        public List<String> getRusername() {
        return rusername;
    }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = layoutInflater.inflate(R.layout.user_appointment, parent, false);
        //ImageView images = row.findViewById(R.id.image);
        TextView myTitle = row.findViewById(R.id.textView1);
        TextView username = row.findViewById(R.id.textView2);
        TextView  from= row.findViewById(R.id.textView3);
//        TextView to = row.findViewById(R.id.textView4);
//        TextView  endfrom= row.findViewById(R.id.textView5);
//        TextView endto = row.findViewById(R.id.textView6);
        // now set our resources on views
        //images.setImageResource(rImgs[position]);
        myTitle.setText(rTitle.get(position));
        username.setText(rusername.get(position));
        from.setText(rSt_hr.get(position));
//        to.setText(rst_ed.get(position));
//        endfrom.setText(red_hr.get(position));
//        endto.setText(red_ed.get(position));

        return row;
    }
    }
}