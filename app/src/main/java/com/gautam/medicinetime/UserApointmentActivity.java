package com.gautam.medicinetime;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class UserApointmentActivity extends AppCompatActivity {
    ListView listView;
    String mTitle[] = {"Today","Tomorrow"};
    FirebaseFirestore fstore;
    String userId;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_apointment);
        listView = findViewById(R.id.listView);
        SimpleDateFormat timeStampFormat = new SimpleDateFormat("Mddyyyy");
        Date myDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date tomorrow = calendar.getTime();
        String filename = timeStampFormat.format(myDate);
        String filename1 = timeStampFormat.format(tomorrow);
        String time[] = {filename,filename1};

        mAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        userId = mAuth.getCurrentUser().getUid();
        fstore.collection("Appointments")
                .document(userId)
                .collection("Appointments")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<String> name = new ArrayList<>();
                    List<String> time = new ArrayList<>();
                    List<String> date = new ArrayList<>();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        name.add(document.getString("Clinic_Name"));
                        date.add(document.getString("Date"));
                        time.add(document.getString("time"));
                        //mTitle = new String[]{document.getString("Clinic_Name"),document.getString("Clinic_Name")};
                        MyAdapter adapter = new MyAdapter(UserApointmentActivity.this, name,date ,time);
                        listView.setAdapter(adapter);
                    }
                    //Log.d(TAG, list.toString());
                    //Toast.makeText(UserApointmentActivity.this,"Total Clinics:"+name,Toast.LENGTH_SHORT).show();
                } else {
                    //Log.d(TAG, "Error getting documents: ", task.getException());
                    Toast.makeText(UserApointmentActivity.this,""+task.getException(),Toast.LENGTH_SHORT).show();
                }
            }
        });
        //MyAdapter adapter = new MyAdapter(UserApointmentActivity.this, mTitle, time);
        //listView.setAdapter(adapter);
    }
    class MyAdapter extends ArrayAdapter<String> {

        Context context;
        List<String> rTitle;
        List<String> rDate;
        List<String> time1;

        MyAdapter(Context c, List<String> title, List<String> date, List<String> username) {
            super(c, R.layout.noti_details2, R.id.textView1, title);
            this.context = c;
            this.rTitle = title;
            this.rDate = date;
            this.time1 = username;
        }
        @NonNull
        @Override
        public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.noti_details2, parent, false);
            //ImageView images = row.findViewById(R.id.image);
            TextView myTitle = row.findViewById(R.id.textView1);
            TextView username = row.findViewById(R.id.textView2);
            TextView  from= row.findViewById(R.id.textView3);
            TextView to = row.findViewById(R.id.textView4);
            TextView  endfrom= row.findViewById(R.id.textView5);
            TextView endto = row.findViewById(R.id.textView6);
            // now set our resources on views
//            images.setImageResource(R.drawable.dr671);
            myTitle.setText(rTitle.get(position));
            //username.setText(rusername.get(position));
            from.setText(rDate.get(position));
            to.setText(time1.get(position));
            //endfrom.setText(red_hr.get(position));
            //endto.setText(red_ed.get(position));

            return row;
        }
    }

}