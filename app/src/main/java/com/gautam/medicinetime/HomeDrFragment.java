package com.gautam.medicinetime;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;


import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeDrFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeDrFragment extends Fragment{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeDrFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeDrFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeDrFragment newInstance(String param1, String param2) {
        HomeDrFragment fragment = new HomeDrFragment();
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

    private FirebaseAuth mAuth;
    FloatingActionButton fabMain, fabOne, fabTwo, fabThree;
    Float translationY = 100f;
    Dialog myDialog;
    OvershootInterpolator interpolator = new OvershootInterpolator();

    private static final String TAG = "MainActivity";

    Boolean isMenuOpen = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_home_dr, container, false);
        fabMain = root.findViewById(R.id.fabMain);
        final Button check_list = (Button) root.findViewById(R.id.buttonprofile);
        myDialog = new Dialog(getActivity());
        fabMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),AddClinicActivity.class));
            }
        });
        check_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),PatientListActivity.class));
            }
        });
        //final FloatingActionButton fab = root.findViewById(R.id.fab);

        //final Button logout = root.findViewById(R.id.buttonlogout);
        /*final RelativeLayout constraintLayout = root.findViewById(R.id.layout);
        //ConstraintLayout constraintLayout = findViewById(R.id.layout);
        AnimationDrawable animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(4000);
        animationDrawable.start();*/
        return root;
    }

    private void closeMenu() {

        isMenuOpen = !isMenuOpen;
        fabMain.animate().setInterpolator(interpolator).rotation(0f).setDuration(300).start();
        fabOne.animate().translationY(translationY).alpha(0f).setInterpolator(interpolator).setDuration(300).start();
        fabTwo.animate().translationY(translationY).alpha(0f).setInterpolator(interpolator).setDuration(300).start();
        fabThree.animate().translationY(translationY).alpha(0f).setInterpolator(interpolator).setDuration(300).start();
    }

    private void handleFabOne() {
        Log.i(TAG, "handleFabOne: ");
    }

    private void openMenu() {

        isMenuOpen = !isMenuOpen;
        fabMain.animate().setInterpolator(interpolator).rotation(45f).setDuration(300).start();
        fabOne.animate().translationY(0f).alpha(1f).setInterpolator(interpolator).setDuration(300).start();
        fabTwo.animate().translationY(0f).alpha(1f).setInterpolator(interpolator).setDuration(300).start();
        fabThree.animate().translationY(0f).alpha(1f).setInterpolator(interpolator).setDuration(300).start();
    }
}