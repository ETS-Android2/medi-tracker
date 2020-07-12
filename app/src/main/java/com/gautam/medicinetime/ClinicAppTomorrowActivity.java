package com.gautam.medicinetime;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

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

public class ClinicAppTomorrowActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    FirebaseFirestore fstore;
    String userId;
    TextView textView;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clinic_app_tomorrow);
        listView = findViewById(R.id.listView);
        mAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        userId = mAuth.getCurrentUser().getUid();
        SimpleDateFormat timeStampFormat = new SimpleDateFormat("Mddyyyy");
        Date myDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date tomorrow = calendar.getTime();
        //String filename = timeStampFormat.format(myDate);
        String filename1 = timeStampFormat.format(tomorrow);
        //Toast.makeText(ClinicAppDrActivity.this,""+filename1,Toast.LENGTH_SHORT).show();
        fstore.collection("DrAppointment")
                .document(userId)
                .collection("DrAppointment")
                .document(filename1)
                .collection("DrAppointment")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<String> name = new ArrayList<>();
                    List<String> time = new ArrayList<>();
                    List<String> email = new ArrayList<>();
                    List<String> mo = new ArrayList<>();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        name.add(document.getString("Name"));
                        time.add(document.getString("time"));
                        email.add(document.getString("Email"));
                        mo.add(document.getString("mo"));

                        //textView.setText(name);
                        //Toast.makeText(ClinicAppTomorrowActivity.this,"NAMES"+name,Toast.LENGTH_SHORT).show();
                        MyAdapter adapter = new MyAdapter(ClinicAppTomorrowActivity.this, name, email,mo,time);
                        listView.setAdapter(adapter);
                    }
                }
            }
        });
    }

    class MyAdapter extends ArrayAdapter<String> {

        Context context;
        List<String> name;
        List<String> email;
        List<String> mo;
        List<String> time;

        MyAdapter (Context c, List<String> Pname, List<String> Pemail,List<String> Pmo,List<String> Ptime) {
            super(c, R.layout.appointment_details, R.id.textView1, Pname);
            this.context = c;
            this.name = Pname;
            this.email = Pemail;
            this.mo = Pmo;
            this.time = Ptime;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.appointment_details, parent, false);
            //ImageView images = row.findViewById(R.id.image);
            TextView Pname = row.findViewById(R.id.txtlbl1);
            TextView Pemail = row.findViewById(R.id.txtlbl3);
            TextView  Pmo= row.findViewById(R.id.textView3);
            TextView Ptime = row.findViewById(R.id.textView1);
            //TextView  endfrom= row.findViewById(R.id.textView5);
            //TextView endto = row.findViewById(R.id.textView6);
            // now set our resources on views
            //images.setImageResource(rImgs[position]);
            //images.setImageResource(R.drawable.patient11);
            Pname.setText(name.get(position));
            //username.setText(rusername.get(position));
            Pemail.setText(email.get(position));
            Pmo.setText(mo.get(position));
            Ptime.setText(time.get(position));
            //endto.setText(red_ed.get(position));
            return row;
        }
    }
}