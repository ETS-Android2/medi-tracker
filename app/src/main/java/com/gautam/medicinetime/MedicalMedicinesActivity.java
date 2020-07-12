package com.gautam.medicinetime;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
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

import java.util.ArrayList;
import java.util.List;

public class MedicalMedicinesActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    public static final String filename = "login";
    public static final String Username = "username";
    public static final String Password = "password";
    private int countdb = 0;
    ListView listView;
    FirebaseAuth mAuth;
    String mTitle[] = {"Facebook", "Whatsapp", "Twitter", "Instagram", "Youtube"};
    FirebaseFirestore fstore;
    String userId;
    String medid;
    private String idid;
    String[] data = {"c","c++","java","rahul","khambe"};
    FirebaseDatabase database;
    DatabaseReference ref,ref1;
    ArrayList<String> list;
    ArrayAdapter<String> arrayAdapter;
    User user;
    private String username;
    private int position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_medicines);
        Intent intent = getIntent();
        String str = intent.getStringExtra("Medical_Name");
        String uid = intent.getStringExtra("id");
        user = new User();
        listView = findViewById(R.id.listView);
        database = FirebaseDatabase.getInstance();
        ref = database.getReference();
        mAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        userId = mAuth.getCurrentUser().getUid();
        sharedPreferences = getSharedPreferences(filename, Context.MODE_PRIVATE);
        if (sharedPreferences.contains(Username)) {
            if (mAuth.getCurrentUser() == null) {
                Toast.makeText(MedicalMedicinesActivity.this, "Error", Toast.LENGTH_SHORT).show();
            } else {
                DocumentReference documentReference = fstore.collection("MedicalDetails").document();
                documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                        if (str.equals(documentSnapshot.getString("MedicalDetails"))) {
                            String id1 = documentSnapshot.getId();
                            //Toast.makeText(ClinicAppointmentActivity.this,"USERNAME"+id,Toast.LENGTH_SHORT).show();
                        } else {
                            //Toast.makeText(ClinicAppointmentActivity.this,"ERROR",Toast.LENGTH_SHORT).show();
                            fstore.collection("MedicalDetails").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            if (str.equals(document.getString("Medical_Name"))) {
                                                medid = document.getString("userId");
                                                idid = medid;
                                               // Toast.makeText(MedicalMedicinesActivity.this,"Id"+medid,Toast.LENGTH_SHORT).show();
                                                fstore.collection("Medical").document(medid).collection(str).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                        if (task.isSuccessful()) {
                                                            List<String> namelist = new ArrayList<>();
                                                            List<String> usernamelist = new ArrayList<>();
                                                            List<String> starthlist = new ArrayList<>();
                                                            List<String> startmlist = new ArrayList<>();
                                                            List<String> endhlist = new ArrayList<>();
                                                            List<String> endmlist = new ArrayList<>();

                                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                                namelist.add(document.getString("Medicine_Name"));
                                                                usernamelist.add(document.getString("Medicine_Price"));
                                                                starthlist.add(document.getString("Status"));
                                                                startmlist.add(document.getString("Type"));

                                                                //mTitle = new String[]{document.getString("Clinic_Name"),document.getString("Clinic_Name")};
                                                                //adapter adp = new adapter(UserClinicActivity.this, namelist, usernamelist,starthlist,startmlist,endhlist,endmlist);
                                                                MyAdapter adapter = new MyAdapter(MedicalMedicinesActivity.this, namelist, usernamelist,startmlist,starthlist);
                                                                listView.setAdapter(adapter);
                                                            }
                                                            //Log.d(TAG, list.toString());
                                                            //Toast.makeText(UserClinicActivity.this,"Total Clinics:"+countdb,Toast.LENGTH_SHORT).show();
                                                        } else {
                                                            //Log.d(TAG, "Error getting documents: ", task.getException());
                                                            Toast.makeText(MedicalMedicinesActivity.this,""+task.getException(),Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                });


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
    public class MyAdapter extends ArrayAdapter<String> {

        Context context;
        List<String> rTitle;
        List<String> rtype;
        List<String> rstatus;
        List<String> rusername;

        MyAdapter(Context c, List<String> title, List<String> username,List<String> namelist, List<String> usernamelist){
            super(c, R.layout.medical_medicines, R.id.textView1, title);
            this.context = c;
            this.rTitle = title;
            this.rusername = username;
            this.rtype = namelist;
            this.rstatus = usernamelist;
        }

        public List<String> getRusername() {
            return rusername;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.medical_medicines, parent, false);
            //ImageView images = row.findViewById(R.id.image);
            TextView myTitle = row.findViewById(R.id.textView1);
            TextView username = row.findViewById(R.id.textView2);
            TextView  from = row.findViewById(R.id.textView3);
            TextView to = row.findViewById(R.id.textView4);
            //TextView  endfrom= row.findViewById(R.id.textView5);
            //TextView endto = row.findViewById(R.id.textView6);
            // now set our resources on views
            //images.setImageResource(rImgs[position]);
            myTitle.setText(rTitle.get(position));
            username.setText(rusername.get(position));
            from.setText(rtype.get(position));
            to.setText(rstatus.get(position));

            return row;
        }
    }

}