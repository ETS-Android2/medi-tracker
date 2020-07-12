package com.gautam.medicinetime;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AccountDrFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AccountDrFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AccountDrFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AccountDrFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AccountDrFragment newInstance(String param1, String param2) {
        AccountDrFragment fragment = new AccountDrFragment();
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


    private FirebaseDatabase mfirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myref;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment



/*
        View root = inflater.inflate(R.layout.fragment_account_dr, container, false);
        //final Button logout = root.findViewById(R.id.logout_btn_dr);
        //TextView text = root.findViewById(R.id.txt_dr);
        mAuth = FirebaseAuth.getInstance();
        mfirebaseDatabase = FirebaseDatabase.getInstance();
        myref = mfirebaseDatabase.getReference();
        FirebaseUser user = mAuth.getCurrentUser();
        //String text1 = String.valueOf(user);
        //text.setText(text1);
        /*logout.setOnClickListener(new View.OnClickListener() {
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
        });*/
        //return inflater.inflate(R.layout.fragment_account_dr, container, false);
        View root = inflater.inflate(R.layout.fragment_account_dr, container, false);
        final Button logout = root.findViewById(R.id.buttonlogout);
        final TextView settings = root.findViewById(R.id.Setting);
        final Button profile = root.findViewById(R.id.buttonprofile);
        final Button setting = root.findViewById(R.id.btnsetting);
        final Button noti = root.findViewById(R.id.buttonnoti);
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
        noti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAuth.getCurrentUser() != null) {
                    //Toast.makeText(getActivity(), "Done", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getActivity(), NotificationDrActivity.class));
                }
                else {
                    Toast.makeText(getActivity(), "Errror Out", Toast.LENGTH_SHORT).show();
                }
            }
        });
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAuth.getCurrentUser() != null) {
                    //Toast.makeText(getActivity(), "Done", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getActivity(), AboutActivity.class));
                }
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
                    startActivity(new Intent(getActivity(), DrProfileActivity.class));
                }
                else {
                    Toast.makeText(getActivity(), "Please,Login In", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getActivity(),MainActivity.class));
                }
            }
        });
        return root;
    }
}
