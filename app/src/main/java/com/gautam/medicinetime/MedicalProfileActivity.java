package com.gautam.medicinetime;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class MedicalProfileActivity extends AppCompatActivity {
    TextView data,name,mobile,location,email;
    FirebaseAuth mAuth;
    FirebaseFirestore fstore;
    String userId;
    Button gotohome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_profile);
        data = findViewById(R.id.data);
        name = findViewById(R.id.Name);
        mobile = findViewById(R.id.mobile);
        location = findViewById(R.id.location);
        email = findViewById(R.id.email);
        gotohome = findViewById(R.id.gotohome);
        mAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        userId = mAuth.getCurrentUser().getUid();
        DocumentReference documentReference = fstore.collection("users").document(userId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                data.setText(documentSnapshot.getString("Username"));
                name.setText(documentSnapshot.getString("fName"));
                mobile.setText(documentSnapshot.getString("Mobile"));
                location.setText(documentSnapshot.getString("Location"));
                email.setText(documentSnapshot.getString("email"));
            }
        });
        gotohome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MedicalProfileActivity.this,MedicalActivity.class);
                startActivity(intent);
            }
        });
    }
}