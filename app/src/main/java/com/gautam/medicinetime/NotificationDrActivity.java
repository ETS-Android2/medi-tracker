package com.gautam.medicinetime;

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

public class NotificationDrActivity extends AppCompatActivity {
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
        setContentView(R.layout.activity_notification_dr);

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

        fstore.collection("DrNotification")
                .document(userId)
                .collection("DrNotification")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<String> namelist = new ArrayList<>();
                    List<String> timelist = new ArrayList<>();
                    List<String> date23 = new ArrayList<>();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        namelist.add(document.getString("Name"));
                        timelist.add(document.getString("time"));
                        date23.add(document.getString("Date"));

                        //mTitle = new String[]{document.getString("Clinic_Name"),document.getString("Clinic_Name")};

                        MyAdapter adapter = new MyAdapter(NotificationDrActivity.this, namelist, timelist,date23);
                        listView.setAdapter(adapter);
                    }
                    //Log.d(TAG, list.toString());
                    //Toast.makeText(NotificationActivity.this,"Total Clinics:"+namelist,Toast.LENGTH_SHORT).show();
                } else {
                    //Log.d(TAG, "Error getting documents: ", task.getException());
                    Toast.makeText(NotificationDrActivity.this,""+task.getException(),Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    class MyAdapter extends ArrayAdapter<String> {

        Context context;
        List<String> rTitle;
        List<String> rusername;
        List<String> rdate;
        MyAdapter (Context c, List<String> title, List<String> username,List<String> date11) {
            super(c, R.layout.user_info, R.id.textView1, title);
            this.context = c;
            this.rTitle = title;
            this.rusername = username;
            this.rdate = date11;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.noti_dr, parent, false);
            ImageView images = row.findViewById(R.id.image);
            TextView myTitle = row.findViewById(R.id.textView1);
            //TextView username = row.findViewById(R.id.textView2);
            TextView  from= row.findViewById(R.id.textView3);
            TextView to = row.findViewById(R.id.textView4);
            //TextView  endfrom= row.findViewById(R.id.textView5);
            //TextView endto = row.findViewById(R.id.textView6);
            // now set our resources on views
            //images.setImageResource(rImgs[position]);
            images.setImageResource(R.drawable.patient11);
            myTitle.setText(rTitle.get(position));
            //username.setText(rusername.get(position));
            from.setText(rusername.get(position));
            to.setText(rdate.get(position));
            //endfrom.setText(red_hr.get(position));
            //endto.setText(red_ed.get(position));
            return row;
        }
    }
}

