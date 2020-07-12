package com.gautam.medicinetime;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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

public class MedicalNotificationActivity extends AppCompatActivity {
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
        setContentView(R.layout.activity_medical_notification);

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

        fstore.collection("notification")
                .document(userId)
                .collection("notification")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<String> namelist = new ArrayList<>();
                    List<String> timelist = new ArrayList<>();
                    List<String> date23 = new ArrayList<>();
                    List<String> dose12 = new ArrayList<>();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        namelist.add(document.getString("Medicine_Name"));
                        timelist.add(document.getString("Medicine_Price"));
                        date23.add(document.getString("Date"));
                        dose12.add(document.getString("Type"));
                        //mTitle = new String[]{document.getString("Clinic_Name"),document.getString("Clinic_Name")};

                        MyAdapter adapter = new MyAdapter(MedicalNotificationActivity.this, namelist, timelist,date23,dose12);
                        listView.setAdapter(adapter);
                    }
                    //Log.d(TAG, list.toString());
                    //Toast.makeText(NotificationActivity.this,"Total Clinics:"+namelist,Toast.LENGTH_SHORT).show();
                } else {
                    //Log.d(TAG, "Error getting documents: ", task.getException());
                    Toast.makeText(MedicalNotificationActivity.this,""+task.getException(),Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    class MyAdapter extends ArrayAdapter<String> {

        Context context;
        List<String> rTitle;
        List<String> rusername;
        List<String> rdate;
        List<String> rdose;
        MyAdapter (Context c, List<String> title, List<String> username,List<String> date11, List<String> dose1) {
            super(c, R.layout.notification_medical, R.id.textView1, title);
            this.context = c;
            this.rTitle = title;
            this.rusername = username;
            this.rdate = date11;
            this.rdose = dose1;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.notification_medical, parent, false);
            ImageView images = row.findViewById(R.id.image);
            TextView med_name = row.findViewById(R.id.textView1);
            TextView time1 = row.findViewById(R.id.texthour);
            TextView  dose1 = row.findViewById(R.id.textquantity);
            TextView date1 = row.findViewById(R.id.textdate);
            //TextView  endfrom= row.findViewById(R.id.textView5);
            //TextView endto = row.findViewById(R.id.textView6);
            // now set our resources on views
            //images.setImageResource(rImgs[position]);
            images.setImageResource(R.drawable.ic_notifications_red);
            med_name.setText(rTitle.get(position));
            //username.setText(rusername.get(position));
            time1.setText(rusername.get(position));
            date1.setText(rdate.get(position));
            dose1.setText(rdose.get(position));
            //endto.setText(red_ed.get(position));
            return row;
        }
    }
}