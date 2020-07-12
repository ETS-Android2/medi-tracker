package com.gautam.medicinetime;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;



import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;



/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DashboardDrFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class DashboardDrFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DashboardDrFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DashboardDrFragment newInstance(String param1, String param2) {
        DashboardDrFragment fragment = new DashboardDrFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public DashboardDrFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_dashboard_dr, container, false);
        final ListView listView = root.findViewById(R.id.listView);
        SimpleDateFormat timeStampFormat = new SimpleDateFormat("Mddyyyy");
        Date myDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date tomorrow = calendar.getTime();
        String filename = timeStampFormat.format(myDate);
        String filename1 = timeStampFormat.format(tomorrow);
        String mTitle[] = {"Today","Tomorrow"};
        String time[] = {filename,filename1};
        //ArrayAdapter adapter1 = new ArrayAdapter(getApplicationContext(),mTitle,time);
        MyAdapter adapter = new MyAdapter(getActivity(), mTitle, time);
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
        return root;
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
            //ImageView images = row.findViewById(R.id.image);
            TextView myTitle = row.findViewById(R.id.textView1);
            //TextView username = row.findViewById(R.id.textView2);
            TextView  from= row.findViewById(R.id.textView3);
            //TextView to = row.findViewById(R.id.textView4);
            //TextView  endfrom= row.findViewById(R.id.textView5);
            //TextView endto = row.findViewById(R.id.textView6);
            // now set our resources on views
//            images.setImageResource(R.drawable.dr671);
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