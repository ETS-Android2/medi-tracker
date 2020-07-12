package com.gautam.medicinetime;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class ProfileActivity extends AppCompatActivity {
    private TextView memail;
    private TextView mName;
    private TextView mSurname;
    private TextView mMo;
    private TextView mlocation;
    private EditText mpassword;
    private Button updateBut;
    private RadioButton patient;
    private RadioButton Doctor;
    private RadioButton Medical;
    private FirebaseDatabase mfirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myref;
    private String userId;
    FirebaseFirestore fstore;
    String finalType;
    int i=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        memail = findViewById(R.id.edtemail);
        mName = findViewById(R.id.edtname);
        mSurname = findViewById(R.id.edtSurname);
        mMo = findViewById(R.id.edtmo);
        mlocation = findViewById(R.id.edtLocation);
        updateBut = findViewById(R.id.updatebtn);
        mpassword = findViewById(R.id.edtpass);
        patient = findViewById(R.id.radiopatient);
        Doctor = findViewById(R.id.radioDoctor);
        Medical = findViewById(R.id.radioMedical);
        fstore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        mfirebaseDatabase = FirebaseDatabase.getInstance();
        myref = mfirebaseDatabase.getReference();

        //userId = user.getEmail();
        //email.setText(userId);
        updateBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String type = "";
                final String email = memail.getText().toString().trim();
                final String name = mName.getText().toString().trim();
                final String surname = mSurname.getText().toString().trim();
                final String mo = mMo.getText().toString().trim();
                final String location = mlocation.getText().toString().trim();
                String password = mpassword.getText().toString().trim();
                if (patient.isChecked()){
                    type = "PAtient";
                }else if (Doctor.isChecked()){
                    type = "Doctor";
                }else if (Medical.isChecked()) {
                    type = "Medical";
                }
                else{
                        Toast.makeText(ProfileActivity.this, "Choose Only one Type ", Toast.LENGTH_SHORT).show();
                }

                if (TextUtils.isEmpty(email)){
                    memail.setError("Email is required");
                    return;
                }
                if(!CheckEmail(email)){
                    memail.setError("Wrong Email");

                }
                if (TextUtils.isEmpty(name)){
                    memail.setError("Name is required");
                    return;
                }
                if (TextUtils.isEmpty(surname)){
                    memail.setError("Surname is required");
                    return;
                }
                if (TextUtils.isEmpty(mo)){
                    memail.setError("Mobile Number is required");
                    return;
                }
                if (TextUtils.isEmpty(location)){
                    memail.setError("location is required");
                    return;
                }
                if (TextUtils.isEmpty(password)){
                    memail.setError("Password is required");
                    return;
                }
                finalType = type;
                mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(ProfileActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(ProfileActivity.this, "Updated", Toast.LENGTH_SHORT).show();
                            userId = mAuth.getCurrentUser().getUid();
                            DocumentReference documentReference = fstore.collection("users").document(userId);
                            Map<String,Object> user = new HashMap<>();
                            user.put("fName",name);
                            user.put("Username",surname);
                            user.put("email",email);
                            user.put("Mobile",mo);
                            user.put("Location",location);
                            user.put("type", finalType);

                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    if (finalType == "Doctor") {
                                        Toast.makeText(ProfileActivity.this, "Welcome Doctor", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(ProfileActivity.this, DoctorActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);
                                        finish();
                                    }else if (finalType == "PAtient") {
                                        Toast.makeText(ProfileActivity.this, "Welcome Patient", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(ProfileActivity.this, HomeActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);
                                        finish();
                                    }
                                    else if (finalType == "Medical") {
                                        Toast.makeText(ProfileActivity.this, "Welcome Medical Owner", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(ProfileActivity.this, MedicalActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);
                                        finish();
                                    }
                                    //Toast.makeText(ProfileActivity.this, "Success for"+userId, Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(ProfileActivity.this,"Error="+e.toString(),Toast.LENGTH_SHORT).show();
                                }
                            });


                            //startActivity(new Intent(ProfileActivity.this,HomeActivity.class));
                        }
                        else{
                            Toast.makeText(ProfileActivity.this, "bro Error h", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }

            private boolean CheckEmail(String EmailAddress) {
                return isValid(EmailAddress);
            }
            private boolean isValid(String email){
                String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                        "[a-zA-Z0-9_+&*-]+)*@" +
                        "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                        "A-Z]{2,7}$";
                Pattern pat = Pattern.compile(emailRegex);
                if (email == null){
                    return false;
                }
                return pat.matcher(email).matches();
            }
        });
    }




}
