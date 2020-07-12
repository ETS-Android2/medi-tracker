
package com.gautam.medicinetime;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class PatientListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_list);
        ListView listView = (ListView) findViewById(R.id.listView);
        SimpleDateFormat timeStampFormat = new SimpleDateFormat("dd/M/yyyy");
        Date myDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date tomorrow = calendar.getTime();
        String filename = timeStampFormat.format(myDate);
        String filename1 = timeStampFormat.format(tomorrow);
        String mTitle[] = {"Today","Tomorrow"};
        String time[] = {filename,filename1};
        //ArrayAdapter adapter1 = new ArrayAdapter(getApplicationContext(),mTitle,time);
        MyAdapter adapter = new MyAdapter(getApplicationContext(), mTitle, time);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //view itemClicked = (View) parent.getItemAtPosition(position);
                String selectedItem = (String) parent.getItemAtPosition(position);
                String selectedItem1 = (String) parent.getItemAtPosition(position);
                // Display the selected item text on TextView
                //tv.setText("Your favorite : " + selectedItem);
                //Toast.makeText(UserClinicActivity.this,"Clinic"+selectedItem,Toast.LENGTH_SHORT).show();
                if (position ==  0) {
                    //Toast.makeText(getActivity(), "Youtube Description", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(view.getContext(), ClinicAppDrActivity.class);
                    intent.putExtra("time", filename);
                    //intent.putParcelableArrayListExtra("data",rusername);
                    startActivity(intent);
                }
                if (position ==  1) {
                    //Toast.makeText(getActivity(), "Youtube Description", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(view.getContext(), ClinicAppTomorrowActivity.class);
                    intent.putExtra("time", filename1);
                    //intent.putParcelableArrayListExtra("data",rusername);
                    startActivity(intent);
                }
                //startActivity(new Intent(UserClinicActivity.this, ClinicAppointmentActivity.class));

            }
        });
    }
    class MyAdapter extends ArrayAdapter<String> {

        Context context;
        String[] rTitle;
        String[] time1;

        MyAdapter(Context c, String[] title, String[] username) {
            super(c, R.layout.noti_details1, R.id.textView1, title);
            this.context = c;
            this.rTitle = title;
            this.time1 = username;
        }
        @NonNull
        @Override
        public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.noti_details1, parent, false);
            ImageView images = row.findViewById(R.id.image);
            TextView myTitle = row.findViewById(R.id.textView1);
            //TextView username = row.findViewById(R.id.textView2);
            TextView  from= row.findViewById(R.id.textView3);
            //TextView to = row.findViewById(R.id.textView4);
            //TextView  endfrom= row.findViewById(R.id.textView5);
            //TextView endto = row.findViewById(R.id.textView6);
            // now set our resources on views
            images.setImageResource(R.drawable.checklist);
            myTitle.setText(rTitle[position]);
            //username.setText(rusername.get(position));
            from.setText(time1[position]);
            //to.setText(rst_ed.get(position));
            //endfrom.setText(red_hr.get(position));
            //endto.setText(red_ed.get(position));

            return row;
        }
    }

}