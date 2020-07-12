package com.gautam.medicinetime;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;
import java.util.Map;

public class EditProfileActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    EditText muser;
    EditText mmo;
    TextView name;
    TextView email;
    private RadioButton patient;
    private RadioButton Doctor;
    private RadioButton Medical;
    String finalType;
    private String userId;
    FirebaseFirestore fstore;
    String personName;
    String personEmail;
    Uri personPhoto;
    private String username1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        // Check for existing Google Sign In account, if the user is already signed in
// the GoogleSignInAccount will be non-null.
        mAuth = FirebaseAuth.getInstance();
        Button btn = (Button) findViewById(R.id.upbtn);
        muser = (EditText) findViewById(R.id.etusername);
        mmo = (EditText) findViewById(R.id.etmo);
        fstore = FirebaseFirestore.getInstance();
        patient = findViewById(R.id.radiopatient);
        Doctor = findViewById(R.id.radioDoctor);
        Medical = findViewById(R.id.radioMedical);
        mAuth = FirebaseAuth.getInstance();
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        FirebaseUser user = mAuth.getCurrentUser();
        userId = mAuth.getCurrentUser().getUid();
        updateUI(user);
        DocumentReference documentReference = fstore.collection("users").document(userId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                //data.setText(documentSnapshot.getString("Username"));
                username1 = documentSnapshot.getString("Username");
                //name.setText(documentSnapshot.getString("Mobile"));
                //mobile.setText(documentSnapshot.getString("Mobile"));
                //location.setText(documentSnapshot.getString("Location"));
                email.setText(documentSnapshot.getString("email"));
            }
        });
       // muser.setHint("kk"+username1);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String type = "";
                final String u_email = muser.getText().toString().trim();
                final String u_name = mmo.getText().toString().trim();
                if (patient.isChecked()){
                    type = "PAtient";
                }else if (Doctor.isChecked()){
                    type = "Doctor";
                }else if (Medical.isChecked()) {
                    type = "Medical";
                }
                else{
                    Toast.makeText(EditProfileActivity.this, "Choose Only one Type ", Toast.LENGTH_SHORT).show();
                }

                finalType = type;
                DocumentReference documentReference = fstore.collection("users").document(userId);
                Map<String,Object> user = new HashMap<>();
                user.put("fName",personName);
                user.put("email",personEmail);
                user.put("Username",u_email);
                user.put("Mobile",u_name);
                user.put("type",finalType);
                documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(EditProfileActivity.this, "Updated", Toast.LENGTH_SHORT).show();
                        if (mAuth.getCurrentUser() == null) {
                            Toast.makeText(EditProfileActivity.this, "Error h ", Toast.LENGTH_SHORT).show();
                        } else {
                            userId = mAuth.getCurrentUser().getUid();
                            DocumentReference documentReference = fstore.collection("users").document(userId);
                            documentReference.addSnapshotListener(EditProfileActivity.this, new EventListener<DocumentSnapshot>() {
                                @Override
                                public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                                    if ("Doctor".equals(documentSnapshot.getString("type"))) {
                                        Toast.makeText(EditProfileActivity.this, "Welcome Doctor", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(EditProfileActivity.this, DoctorActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);
                                        finish();
                                    }else if ("PAtient".equals(documentSnapshot.getString("type"))) {
                                        Toast.makeText(EditProfileActivity.this, "Welcome Patient", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(EditProfileActivity.this, HomeActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);
                                        finish();
                                    }
                                    else if ("Medical".equals(documentSnapshot.getString("type"))) {
                                        Toast.makeText(EditProfileActivity.this, "Welcome Medical Owner", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(EditProfileActivity.this, MedicalActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                            });
                        }
                    }
                });


            }
        });

        //updateUI(account);

    }
    private void updateUI(FirebaseUser fUser) {
        //btnSignOut.setVisibility(View.VISIBLE);
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
        if (account != null) {

            personName = account.getDisplayName();
            String personGivenName = account.getGivenName();
            String personFamilyName = account.getFamilyName();
            personEmail = account.getEmail();
            String personId = account.getId();
            personPhoto = account.getPhotoUrl();

            ImageView userImage = findViewById(R.id.imageView2);
            Glide.with(this).load(personPhoto).into(userImage);
            name = (TextView) findViewById(R.id.user_name);
            name.setText(personName);
            email = findViewById(R.id.user_email);
            email.setText(personEmail);

        }
    }
}