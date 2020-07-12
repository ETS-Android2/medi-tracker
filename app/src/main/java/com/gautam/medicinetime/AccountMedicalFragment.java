package com.gautam.medicinetime;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.gautam.medicinetime.medicines.UserMedicinesActivity;
import com.gautam.medicinetime.ui.notifications.NotificationsViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AccountMedicalFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AccountMedicalFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AccountMedicalFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AccountMedicalFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AccountMedicalFragment newInstance(String param1, String param2) {
        AccountMedicalFragment fragment = new AccountMedicalFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private NotificationsViewModel notificationsViewModel;
    private FirebaseDatabase mfirebaseDatabase;
    private FirebaseAuth mAuth;
    private DatabaseReference myref;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_account_medical, container, false);

        final Button logout = root.findViewById(R.id.buttonlogout);
        final TextView settings = root.findViewById(R.id.Setting);
        final Button profile = root.findViewById(R.id.buttonprofile);
        final Button help = root.findViewById(R.id.btnhelp);
        final Button noti = root.findViewById(R.id.buttonnoti);
        final Button medi = root.findViewById(R.id.buttonMedicines);
        final Button msg = root.findViewById(R.id.buttonfinance);
        mAuth = FirebaseAuth.getInstance();
        mfirebaseDatabase = FirebaseDatabase.getInstance();
        myref = mfirebaseDatabase.getReference();
        FirebaseUser user = mAuth.getCurrentUser();
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAuth.getCurrentUser() != null) {
                    FirebaseAuth.getInstance().signOut();
                    Toast.makeText(getActivity(), "Logged Out", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getActivity(), MainActivity.class));
                }
                else {
                    Toast.makeText(getActivity(), "Errror Out", Toast.LENGTH_SHORT).show();
                }
            }
        });
        medi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAuth.getCurrentUser() != null) {
                    //Toast.makeText(getActivity(), "Done", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getActivity(), MedicalListActivity2.class));
                }
                else {
                    Toast.makeText(getActivity(), "Errror Out", Toast.LENGTH_SHORT).show();
                }
            }
        });
        noti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAuth.getCurrentUser() != null) {
                    //Toast.makeText(getActivity(), "Done", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getActivity(), MedicalNotificationActivity.class));
                }
                else {
                    Toast.makeText(getActivity(), "Errror Out", Toast.LENGTH_SHORT).show();
                }
            }
        });
        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAuth.getCurrentUser() != null) {

                    startActivity(new Intent(getActivity(), AboutActivity.class));
                }
                else {
                    Toast.makeText(getActivity(), "Errror Out", Toast.LENGTH_SHORT).show();
                }
            }
        });
        msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAuth.getCurrentUser() != null) {
                    Toast.makeText(getActivity(), "Done", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getActivity(), MedicalListActivity.class));                }
                else {
                    Toast.makeText(getActivity(), "Errror Out", Toast.LENGTH_SHORT).show();
                }
            }
        });
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAuth.getCurrentUser() != null) {
                    //Toast.makeText(getActivity(), "Update Your Profile for Better Experiment", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getActivity(), MedicalProfileActivity.class));
                }
                else {
                    Toast.makeText(getActivity(), "Please,Login In", Toast.LENGTH_SHORT).show();
                    //startActivity(new Intent(getActivity(),MainActivity.class));
                }
            }
        });
        return root;
    }
}
