package com.gautam.medicinetime.ui.home;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.gautam.medicinetime.HealthTipsActivity;
import com.gautam.medicinetime.MedicalUserActivity;
import com.gautam.medicinetime.R;

import com.gautam.medicinetime.UserClinicActivity;
import com.gautam.medicinetime.medicine.MedicineActivity;

import com.github.sundeepk.compactcalendarview.AnimationListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

public class HomeFragment extends Fragment implements View.OnClickListener{

    private HomeViewModel homeViewModel;
    private FirebaseAuth mAuth;
    FloatingActionButton fabMain,fabOne, fabTwo, fabThree;
    Float translationY = 100f;
    Dialog myDialog;
    OvershootInterpolator interpolator = new OvershootInterpolator();

    private static final String TAG = "MainActivity";

    Boolean isMenuOpen = false;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        //final TextView textView = root.findViewById(R.id.text_home);
        final Button profile = root.findViewById(R.id.buttonprofile);
        final Button noti = root.findViewById(R.id.buttonnoti);
        final TextView addmedi = root.findViewById(R.id.add_medi);
        final TextView btnmedi = root.findViewById(R.id.buttonmedical);

        fabMain = root.findViewById(R.id.fabMain);
//        fabOne = root.findViewById(R.id.fabOne);
//        fabTwo = root.findViewById(R.id.fabTwo);
        myDialog = new Dialog(getActivity());
        //fabThree = root.findViewById(R.id.fabThree);
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                // textView.setText(s);
            }
        });
        //final FloatingActionButton fab = root.findViewById(R.id.fab);
        //initFabMenu();
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), HealthTipsActivity.class));

            }
        });
        noti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), UserClinicActivity.class));

            }
        });
        btnmedi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), MedicalUserActivity.class));

            }
        });
        fabMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), MedicineActivity.class));
                Toast.makeText(getActivity(), "Add Medicines", Toast.LENGTH_SHORT).show();
            }
        });
        // fade out view nicely after 5 seconds
        AlphaAnimation alphaAnim = new AlphaAnimation(1.0f,0.0f);
        alphaAnim.setStartOffset(5000);                        // start in 5 seconds
        alphaAnim.setDuration(400);
        alphaAnim.setAnimationListener(new AnimationListener()
        {
            public void onAnimationEnd(Animation animation)
            {
                // make invisible when animation completes, you could also remove the view from the layout
                addmedi.setVisibility(View.INVISIBLE);
            }
        });

        addmedi.setAnimation(alphaAnim);
        return root;


    }
//    private void initFabMenu() {
//
//
//
//        fabOne.setAlpha(0f);
//        fabTwo.setAlpha(0f);
//        fabThree.setAlpha(0f);
//
//        fabOne.setTranslationY(translationY);
//        fabTwo.setTranslationY(translationY);
//        fabThree.setTranslationY(translationY);
//
//        fabMain.setOnClickListener(this);
//        fabOne.setOnClickListener(this);
//        fabTwo.setOnClickListener(this);
//        fabThree.setOnClickListener(this);
//    }

    @Override
    public void onClick(View v) {


    }







        /*switch (v.getId()) {
            case R.id.fabMain:

                Log.i(TAG, "onClick: fab main");
                if (isMenuOpen) {
                    closeMenu();
                } else {
                    openMenu();
                }
                break;
            case R.id.fabOne:
                Toast.makeText(getActivity(),"Add Medicines",Toast.LENGTH_SHORT).show();
                Log.i(TAG, "onClick: Add Medicines");
                handleFabOne();
                if (isMenuOpen) {
                    TextView textView ;
                    Button btn;
                    //myDialog.setContentView(R.layout.activity_medication);
                    //textView = (TextView) myDialog.findViewById(R.id.txtclose);
                    //btn = (Button) myDialog.findViewById(R.id.btnsub);
                    textView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            myDialog.dismiss();
                        }
                    });
                    //myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    //myDialog.show();
                    startActivity(new Intent(getActivity(), SetAlarm.class));
                } else {
                    openMenu();
                }
                break;
            case R.id.fabTwo:
                Log.i(TAG, "onClick: fab two");
                Toast.makeText(getActivity(),"Add Medicines",Toast.LENGTH_SHORT).show();
                Log.i(TAG, "onClick: Add Medicines");
                handleFabOne();
                if (isMenuOpen) {
                    TextView textView ;
                    Button btn;
                    //myDialog.setContentView(R.layout.activity_medication);
                    //textView = (TextView) myDialog.findViewById(R.id.txtclose);
                    //btn = (Button) myDialog.findViewById(R.id.btnsub);
                    /*textView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            myDialog.dismiss();
                        }
                    });
                    //myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    //myDialog.show();
                    startActivity(new Intent(getActivity(), UserClinicActivity.class));
                    //startActivity(new Intent(getActivity(), ApointmentActivity.class));
                } else {
                    openMenu();
                }
                break;
            case R.id.fabThree:
                Log.i(TAG, "onClick: fab three");
                Log.i(TAG, "onClick: fab two");
                Toast.makeText(getActivity(),"Add Medicines",Toast.LENGTH_SHORT).show();
                Log.i(TAG, "onClick: Add Medicines");
                handleFabOne();
                if (isMenuOpen) {
                    TextView textView ;
                    Button btn;
                    //myDialog.setContentView(R.layout.activity_medication);
                    //textView = (TextView) myDialog.findViewById(R.id.txtclose);
                    //btn = (Button) myDialog.findViewById(R.id.btnsub);
                    textView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            myDialog.dismiss();
                        }
                    });
                    //myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    //myDialog.show();
                    startActivity(new Intent(getActivity(), ClinicMapsActivity2.class));
                } else {
                    openMenu();
                }
                break;
        }*/

    }

//    private void openMenu() {
//        isMenuOpen = !isMenuOpen;
//        fabMain.animate().setInterpolator(interpolator).rotation(45f).setDuration(300).start();
//        fabOne.animate().translationY(0f).alpha(1f).setInterpolator(interpolator).setDuration(300).start();
//        fabTwo.animate().translationY(0f).alpha(1f).setInterpolator(interpolator).setDuration(300).start();
//        fabThree.animate().translationY(0f).alpha(1f).setInterpolator(interpolator).setDuration(300).start();
//    }
//
//    private void handleFabOne() {
//        Log.i(TAG, "handleFabOne: ");
//    }
//
//    private void closeMenu() {
//        isMenuOpen = !isMenuOpen;
//        fabMain.animate().setInterpolator(interpolator).rotation(0f).setDuration(300).start();
//        fabOne.animate().translationY(translationY).alpha(0f).setInterpolator(interpolator).setDuration(300).start();
//        fabTwo.animate().translationY(translationY).alpha(0f).setInterpolator(interpolator).setDuration(300).start();
//        fabThree.animate().translationY(translationY).alpha(0f).setInterpolator(interpolator).setDuration(300).start();
//
//    }
