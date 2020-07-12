package com.gautam.medicinetime.ui.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.gautam.medicinetime.R;

import com.gautam.medicinetime.UserApointmentActivity;
import com.gautam.medicinetime.medicine.MedicineActivity;
import com.gautam.medicinetime.medicines.UserMedicinesActivity;
import com.google.firebase.auth.FirebaseAuth;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;
    private FirebaseAuth mAuth;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        //final TextView textView = root.findViewById(R.id.text_dashboard);
        final LinearLayout linearLayout1 = root.findViewById(R.id.appointmntll);
        final LinearLayout linearLayout2 = root.findViewById(R.id.ttlmedicines);
        final LinearLayout linearLayout3 = root.findViewById(R.id.alarmll);
        linearLayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), UserApointmentActivity.class));
            }
        });
        linearLayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), UserMedicinesActivity.class));
            }
        });
        linearLayout3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), MedicineActivity.class));
            }
        });
        dashboardViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
          //      textView.setText(s);
            }
        });

        return root;
    }


}
