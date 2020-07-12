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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MedicalListActivity2 extends AppCompatActivity {
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
    String cl;
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
        setContentView(R.layout.activity_medical_list2);


        user = new User();
        listView = findViewById(R.id.listView);

        database = FirebaseDatabase.getInstance();
        ref = database.getReference();
        mAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        userId = mAuth.getCurrentUser().getUid();
        ref1 = FirebaseDatabase.getInstance().getReference("Clinic");
        ref1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                countdb = (int) dataSnapshot.getChildrenCount();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        fstore.collection("MedicalDetails").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
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
                        namelist.add(document.getString("Medical_Name"));
                        usernamelist.add(document.getString("Username"));
                        starthlist.add(document.getString("Start_Hour"));
                        startmlist.add(document.getString("Start_Minute"));
                        endhlist.add(document.getString("End_Hour"));
                        endmlist.add(document.getString("End_Minute"));
                        cl = document.getString("userId");
                        //Toast.makeText(MedicalListActivity2.this, ""+cl, Toast.LENGTH_SHORT).show();
                        //mTitle = new String[]{document.getString("Clinic_Name"),document.getString("Clinic_Name")};
                        //adapter adp = new adapter(UserClinicActivity.this, namelist, usernamelist,starthlist,startmlist,endhlist,endmlist);
                        MyAdapter adapter = new MyAdapter(MedicalListActivity2.this, namelist, usernamelist,starthlist,startmlist,endhlist,endmlist);
                        listView.setAdapter(adapter);
                    }
                    //Log.d(TAG, list.toString());
                    //Toast.makeText(UserClinicActivity.this,"Total Clinics:"+countdb,Toast.LENGTH_SHORT).show();
                } else {
                    //Log.d(TAG, "Error getting documents: ", task.getException());
                    Toast.makeText(MedicalListActivity2.this,""+task.getException(),Toast.LENGTH_SHORT).show();
                }
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //view itemClicked = (View) parent.getItemAtPosition(position);
                String selectedItem = (String) parent.getItemAtPosition(position);
                String selectedItem1 = (String) parent.getItemAtPosition(position);
                // Display the selected item text on TextView
                //tv.setText("Your favorite : " + selectedItem);
                //Toast.makeText(UserClinicActivity.this,"Clinic"+selectedItem,Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(view.getContext(), MedicalMedicinesActivity.class);
                intent.putExtra("Medical_Name", selectedItem);
                intent.putExtra("id",cl);
                //intent.putParcelableArrayListExtra("data",rusername);
                startActivity(intent);
                //startActivity(new Intent(UserClinicActivity.this, ClinicAppointmentActivity.class));

            }
        });
    }

    public class MyAdapter extends ArrayAdapter<String> {

        Context context;
        List<String> rTitle;

        List<String> rusername;
        List<String> rSt_hr;
        List<String> rst_ed;
        List<String> red_hr;
        List<String> red_ed;
        MyAdapter (Context c, List<String> title, List<String> username, List<String> st_hr, List<String> st_ed, List<String> ed_hr, List<String> ed_ed) {
            super(c, R.layout.medical_info, R.id.textView1, title);
            this.context = c;
            this.rTitle = title;
            this.rusername = username;
            this.rSt_hr = st_hr;
            this.rst_ed = st_ed;
            this.red_hr = ed_hr;
            this.red_ed = ed_ed;
        }

        public List<String> getRusername() {
            return rusername;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.medical_info, parent, false);
            //ImageView images = row.findViewById(R.id.image);
            TextView myTitle = row.findViewById(R.id.textView1);
            TextView username = row.findViewById(R.id.textView2);
            TextView  from= row.findViewById(R.id.textView3);
            TextView to = row.findViewById(R.id.textView4);
            TextView  endfrom= row.findViewById(R.id.textView5);
            TextView endto = row.findViewById(R.id.textView6);
            // now set our resources on views
            //images.setImageResource(rImgs[position]);
            myTitle.setText(rTitle.get(position));
            username.setText(rusername.get(position));
            from.setText(rSt_hr.get(position));
            to.setText(rst_ed.get(position));
            endfrom.setText(red_hr.get(position));
            endto.setText(red_ed.get(position));

            return row;
        }
    }

}

