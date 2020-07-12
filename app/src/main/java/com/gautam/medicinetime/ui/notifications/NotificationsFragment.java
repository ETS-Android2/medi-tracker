package com.gautam.medicinetime.ui.notifications;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.gautam.medicinetime.AboutActivity;
import com.gautam.medicinetime.MainActivity;
import com.gautam.medicinetime.NotificationActivity;
import com.gautam.medicinetime.R;
import com.gautam.medicinetime.UserApointmentActivity;
import com.gautam.medicinetime.UserProfileActivity;
import com.gautam.medicinetime.medicines.UserMedicinesActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class NotificationsFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;
    private FirebaseDatabase mfirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myref;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                ViewModelProviders.of(this).get(NotificationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);

        final Button logout = root.findViewById(R.id.buttonlogout);
        final TextView settings = root.findViewById(R.id.Setting);
        final Button profile = root.findViewById(R.id.buttonprofile);
        final Button help = root.findViewById(R.id.btnhelp);
        final Button noti = root.findViewById(R.id.buttonnoti);
        final Button medi = root.findViewById(R.id.buttonMedicines);
        final Button msg = root.findViewById(R.id.buttonfinance);
        notificationsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
               // textView.setText(s);
            }
        });
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
                    startActivity(new Intent(getActivity(), UserMedicinesActivity.class));
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
                    startActivity(new Intent(getActivity(), NotificationActivity.class));
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
                    Toast.makeText(getActivity(), "Done", Toast.LENGTH_SHORT).show();
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
                    startActivity(new Intent(getActivity(), UserApointmentActivity.class));                }
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
                    startActivity(new Intent(getActivity(), UserProfileActivity.class));
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
