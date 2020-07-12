package com.gautam.medicinetime.medicines;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.gautam.medicinetime.R;
import com.gautam.medicinetime.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


import java.util.ArrayList;
import java.util.List;

public class UserMedicinesActivity extends AppCompatActivity {
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_medicines);
        user = new User();
        listView = findViewById(R.id.listView);
        database = FirebaseDatabase.getInstance();
        ref = database.getReference();
        mAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        userId = mAuth.getCurrentUser().getUid();
        fstore.collection("medicines")
                .document(userId)
                .collection("medicines")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<String> medname = new ArrayList<>();
                    List<String> medhour = new ArrayList<>();
                    List<String> minute = new ArrayList<>();
                    List<String> quantity = new ArrayList<>();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        medname.add(document.getString("Medicine_Name"));
                        medhour.add(document.getString("Dose_Unit"));
                        //minute.add(document.getString("Med_Minute"));
                        quantity.add(document.getString("Dose_quantity"));
                        //mTitle = new String[]{document.getString("Clinic_Name"),document.getString("Clinic_Name")};
                        MyAdapter adapter = new MyAdapter(UserMedicinesActivity.this, medname, medhour,quantity);
                        listView.setAdapter(adapter);
                    }
                    //Log.d(TAG, list.toString());
                    //Toast.makeText(UserMedicinesActivity.this,"Total Clinics:"+name,Toast.LENGTH_SHORT).show();
                } else {
                    //Log.d(TAG, "Error getting documents: ", task.getException());
                    Toast.makeText(UserMedicinesActivity.this,""+task.getException(),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    class MyAdapter extends ArrayAdapter<String> {

        Context context;
        List<String> rName;
        List<String> rHour;
        List<String> rMinute;
        List<String> rQuantity;

        MyAdapter(Context c, List<String> name, List<String> hour, List<String> quantity1) {
            super(c, R.layout.noti_deetails, R.id.textView1, name);
            this.context = c;
            this.rName = name;
            this.rHour = hour;

            this.rQuantity = quantity1;
        }
        @NonNull
        @Override
        public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.noti_deetails, parent, false);
            ImageView images = row.findViewById(R.id.image);
            TextView med_name = row.findViewById(R.id.textView1);
            //TextView username = row.findViewById(R.id.textView2);
            TextView  hour_tym= row.findViewById(R.id.texthour);
            //TextView min_tym = row.findViewById(R.id.textmin);
            TextView  med_quantity= row.findViewById(R.id.textquantity);
            //TextView endto = row.findViewById(R.id.textView6);
            // now set our resources on views
            images.setImageResource(R.drawable.allmedi);
            med_name.setText(rName.get(position));
            //username.setText(rusername.get(position));
            hour_tym.setText(rHour.get(position));
            //min_tym.setText(rMinute.get(position));
            med_quantity.setText(rQuantity.get(position));
            //endto.setText(red_ed.get(position));

            return row;
        }
    }
}
