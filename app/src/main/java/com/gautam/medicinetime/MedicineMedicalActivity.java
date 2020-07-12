package com.gautam.medicinetime;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MedicineMedicalActivity extends AppCompatActivity {
    EditText med_name;
    EditText med_price;
    Button submit_btn;
    RadioButton avail;
    RadioButton notavail;
    private String userId;
    FirebaseFirestore fstore;
    String finalType;
    private FirebaseAuth mAuth;
    public String med_type;
    private Spinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_medical);
        med_name = findViewById(R.id.edtname);
        med_price = findViewById(R.id.edtprice);
        submit_btn = findViewById(R.id.submitbtn);
        avail = findViewById(R.id.radioavail);
        notavail = findViewById(R.id.radionotavail);
        spinner = findViewById(R.id.spinner_dose_units);
        fstore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        userId = mAuth.getCurrentUser().getUid();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Intent intent = getIntent();
        String str = intent.getStringExtra("Medical_Name");
        String id = intent.getStringExtra("id");
        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String type = "";
                final String name = med_name.getText().toString().trim();
                final String price = med_price.getText().toString().trim();
                med_type = spinner.getSelectedItem().toString().trim();
                if (avail.isChecked()){
                    type = "Available";
                }else if (notavail.isChecked()){
                    type = "Not Available";
                }
                else{
                    Toast.makeText(MedicineMedicalActivity.this, "Choose Only one Type ", Toast.LENGTH_SHORT).show();
                }
                if (TextUtils.isEmpty(name)){
                    med_name.setError("Name is required");
                    return;
                }
                if (TextUtils.isEmpty(price)){
                    med_price.setError("Price is required");
                    return;
                }
                finalType = type;
                SimpleDateFormat timeStampFormat = new SimpleDateFormat("dd/M/yyyy");
                Date myDate = new Date();
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.DAY_OF_YEAR, 1);
                Date tomorrow = calendar.getTime();
                String filename = timeStampFormat.format(myDate);
                String filename1 = timeStampFormat.format(tomorrow);
                Map<String,Object> user = new HashMap<>();
                user.put("Medicine_Name",name);
                user.put("Medicine_Price",price);
                user.put("Status", finalType);
                user.put("Type", med_type);
                user.put("MedicalId",id);
                user.put("Date",filename);
                db.collection("Medical")
                        .document(userId)
                        .collection(str)
                        .add(user)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Log.d("TAG", "DocumentSnapshot added with ID: " + documentReference.getId()+"For Doctor");
                                Toast.makeText(MedicineMedicalActivity.this,"Medicine Added Sucessfully!! ",Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w("TAG", "Error adding document", e);
                                Toast.makeText(MedicineMedicalActivity.this,"Medicine "+e,Toast.LENGTH_SHORT).show();

                            }
                        });
                db.collection("notification")
                        .document(userId)
                        .collection("notification")
                        .add(user)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Log.d("TAG", "DocumentSnapshot added with ID: " + documentReference.getId()+"For Doctor");
                                //Toast.makeText(MedicineMedicalActivity.this,"Medicine Added Sucessfully!! ",Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w("TAG", "Error adding document", e);
                                Toast.makeText(MedicineMedicalActivity.this,"Medicine "+e,Toast.LENGTH_SHORT).show();

                            }
                        });
            }
        });

    }
}