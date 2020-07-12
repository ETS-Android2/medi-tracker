package com.gautam.medicinetime;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import com.gautam.medicinetime.medicine.MedicineActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainActivity extends AppCompatActivity {
    private EditText email;
    private EditText password;
    private Button login;
    private TextView registerUser;
    private String username1;
    private GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth mAuth;
    FirebaseFirestore fstore;
    String userId;
    private String gemail;
    private String guser;

    private StorageReference storageReference;
    SharedPreferences sharedPreferences;
    public static final String filename = "login";
    public static final String Username = "username";
    public static final String Password = "password";
    private final static int RC_SIGN_IN = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        login = findViewById(R.id.btn_login);
        registerUser = findViewById(R.id.register_user1);

        mAuth = FirebaseAuth.getInstance();

        fstore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();

        sharedPreferences = getSharedPreferences(filename, Context.MODE_PRIVATE);
        if (sharedPreferences.contains(Username)){
            if (mAuth.getCurrentUser() == null) {
                //Toast.makeText(MainActivity.this, "Error h ", Toast.LENGTH_SHORT).show();

            } else {
                userId = mAuth.getCurrentUser().getUid();
                DocumentReference documentReference = fstore.collection("users").document(userId);
                documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                        if ("Doctor".equals(documentSnapshot.getString("type"))) {
                            Toast.makeText(MainActivity.this, "Welcome Doctor", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MainActivity.this, DoctorActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();
                        }else if ("PAtient".equals(documentSnapshot.getString("type"))) {
                            Toast.makeText(MainActivity.this, "Welcome Patient", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();
                        }
                        else if ("Medical".equals(documentSnapshot.getString("type"))) {
                            Toast.makeText(MainActivity.this, "Welcome Medical Owner", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MainActivity.this, MedicalActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();
                        }
                    }
                });
            }
        }
        registerUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,ProfileActivity.class);
                startActivity(intent);
                //startActivity(new Intent(MainActivity.this, ProfileActivity.class));
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt_email = email.getText().toString();
                String txt_password = password.getText().toString();

                if (TextUtils.isEmpty(txt_email) || TextUtils.isEmpty(txt_password)) {
                    Toast.makeText(MainActivity.this, "Empty Credentials!", Toast.LENGTH_SHORT).show();
                    email.setText("");
                    password.setText("");
                    startActivity(new Intent(MainActivity.this, MedicineActivity.class));
                } else {
                    SharedPreferences.Editor editor= sharedPreferences.edit();
                    editor.putString(Username,txt_email);
                    editor.putString(Password,txt_password);
                    editor.commit();
                    loginUser(txt_email, txt_password);

                }
            }
        });
        createRequest();
        findViewById(R.id.google_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser() != null) {
            //Toast.makeText(MainActivity.this, "Done", Toast.LENGTH_SHORT).show();
            //startActivity(new Intent(MainActivity.this, HomeActivity.class));
            adminactivity();
        } else {
            Toast.makeText(MainActivity.this, "Please Login", Toast.LENGTH_SHORT).show();

        }
    }

    private void loginUser(String email, String password) {

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    adminactivity();
                    //Toast.makeText(MainActivity.this, "Welcome " + email+"!", Toast.LENGTH_SHORT).show();

                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }
    private void createRequest(){
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RC_SIGN_IN){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask){
        try{

            GoogleSignInAccount acc = completedTask.getResult(ApiException.class);
            Toast.makeText(MainActivity.this,"Signed In Successfully",Toast.LENGTH_SHORT).show();
            FirebaseGoogleAuth(acc);
        }
        catch (ApiException e){
            Toast.makeText(MainActivity.this,"Sign In Failed",Toast.LENGTH_SHORT).show();
            FirebaseGoogleAuth(null);
        }
    }
    private void FirebaseGoogleAuth(GoogleSignInAccount acct) {
        //check if the account is null
        if (acct != null) {
            AuthCredential authCredential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
            mAuth.signInWithCredential(authCredential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(MainActivity.this, "Successful", Toast.LENGTH_SHORT).show();
                        FirebaseUser user = mAuth.getCurrentUser();

                        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
                        if(account !=  null) {
                            String personName = account.getDisplayName();
                            String personGivenName = account.getGivenName();
                            String personFamilyName = account.getFamilyName();
                            String personEmail = account.getEmail();

                            userId = mAuth.getCurrentUser().getUid();
                            DocumentReference documentReference = fstore.collection("users").document(userId);
                            documentReference.addSnapshotListener(MainActivity.this, new EventListener<DocumentSnapshot>() {
                                @Override
                                public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                                    //data.setText(documentSnapshot.getString("Username"));
                                    gemail = documentSnapshot.getString("email");
                                    guser = documentSnapshot.getString("Username");
                                    //Toast.makeText(MainActivity.this, ""+gemail, Toast.LENGTH_SHORT).show();
                                    //Toast.makeText(MainActivity.this, ""+personEmail, Toast.LENGTH_SHORT).show();
                                    //name.setText(documentSnapshot.getString("Mobile"));
                                    //mobile.setText(documentSnapshot.getString("Mobile"));
                                    //location.setText(documentSnapshot.getString("Location"));
                                    //email.setText(documentSnapshot.getString("email"));
                                    if (gemail != null){
                                        //updateUI(user);
                                        if (guser == "")
                                            startActivity(new Intent(MainActivity.this, EditProfileActivity.class));
                                        else
                                            adminactivity();

                                    }
                                    else{
                                        //Toast.makeText(MainActivity.this, "example", Toast.LENGTH_SHORT).show();
                                        updateUI(user);
                                        startActivity(new Intent(MainActivity.this, EditProfileActivity.class));

                                    }

//                                    if (gemail != personEmail){
//                                        if (guser == "") {
//                                            startActivity(new Intent(MainActivity.this, EditProfileActivity.class));
//                                        }
//                                        else{
//                                            //Toast.makeText(MainActivity.this,"Updated",Toast.LENGTH_SHORT).show();
//                                            adminactivity();
//                                        }
//                                    }
//                                    else {
//                                        updateUI(user);
//                                    }
                                }
                            });

                        }
                        //updateUI(user);

                    } else {
                        Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                        updateUI(null);
                    }
                }
            });
        }
        else{
            Toast.makeText(MainActivity.this, "acc failed", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateUI(FirebaseUser fUser){
        //btnSignOut.setVisibility(View.VISIBLE);
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
        if(account !=  null){
            String personName = account.getDisplayName();
            String personGivenName = account.getGivenName();
            String personFamilyName = account.getFamilyName();
            String personEmail = account.getEmail();
            String personId = account.getId();
            Uri personPhoto = account.getPhotoUrl();
            userId = mAuth.getCurrentUser().getUid();
            DocumentReference documentReference = fstore.collection("users").document(userId);
            Map<String,Object> user = new HashMap<>();
            user.put("fName",personName);
            user.put("Username","");
            user.put("email",personEmail);
            user.put("Mobile","");
            user.put("type","");            //user.put("profile",personPhoto);
            //user.put("Mobile",mo);
            //user.put("Location",location);
            //user.put("type", finalType);

            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
//                    if (finalType == "Doctor") {
////                        Toast.makeText(ProfileActivity.this, "Welcome Doctor", Toast.LENGTH_SHORT).show();
//                        Intent intent = new Intent(MainActivity.this, DoctorActivity.class);
//                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                        startActivity(intent);
//                        finish();
//                    }else if (finalType == "PAtient") {
//                        Toast.makeText(ProfileActivity.this, "Welcome Patient", Toast.LENGTH_SHORT).show();
//                        Intent intent = new Intent(ProfileActivity.this, HomeActivity.class);
//                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                        startActivity(intent);
//                        finish();
//                    }
//                    else if (finalType == "Medical") {
//                        Toast.makeText(ProfileActivity.this, "Welcome Medical Owner", Toast.LENGTH_SHORT).show();
//                        Intent intent = new Intent(ProfileActivity.this, MedicalActivity.class);
//                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                        startActivity(intent);
//                        finish();
//                    }
                    Toast.makeText(MainActivity.this, "Success for"+userId, Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(MainActivity.this,"Error="+e.toString(),Toast.LENGTH_SHORT).show();
                }
            });

            //Toast.makeText(MainActivity.this,personName + personEmail ,Toast.LENGTH_SHORT).show();
        }

    }
    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            //Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            //Log.w(TAG, "signInWithCredential:failure", task.getException());
                            //Snackbar.make(mBinding.mainLayout, "Authentication Failed.", Snackbar.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                        // ...
                    }
                });
    }
    private void adminactivity() {
        if (mAuth.getCurrentUser() == null) {
            Toast.makeText(MainActivity.this, "Error h ", Toast.LENGTH_SHORT).show();
        } else {
            userId = mAuth.getCurrentUser().getUid();
            DocumentReference documentReference = fstore.collection("users").document(userId);
            documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                    if ("Doctor".equals(documentSnapshot.getString("type"))) {
                        Toast.makeText(MainActivity.this, "Welcome Doctor", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity.this, DoctorActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    }else if ("PAtient".equals(documentSnapshot.getString("type"))) {
                        Toast.makeText(MainActivity.this, "Welcome Patient", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    }
                    else if ("Medical".equals(documentSnapshot.getString("type"))) {
                        Toast.makeText(MainActivity.this, "Welcome Medical Owner", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity.this, MedicalActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    }
                }
            });
        }
    }

}