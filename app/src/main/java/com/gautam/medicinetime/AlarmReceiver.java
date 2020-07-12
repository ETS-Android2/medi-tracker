package com.gautam.medicinetime;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Vibrator;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.gautam.medicinetime.addmedicine.AddMedicineFragment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AlarmReceiver extends BroadcastReceiver {
    SharedPreferences sharedPreferences;
    public static final String filename = "login";
    public static final String Username = "username";
    public static final String Password = "password";
    private FirebaseAuth mAuth;
    FirebaseFirestore fstore;
    private String userId;
    private String username;
    @Override
    public void onReceive(Context context, Intent intent) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        // Get id & message from intent.
        int notificationId = intent.getIntExtra("notificationId", 0);
        String message = intent.getStringExtra("todo");
        String alarm = intent.getStringExtra("Time");
        String username = intent.getStringExtra("username");
        long[] tt = intent.getLongArrayExtra("time");
        mAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        userId = mAuth.getCurrentUser().getUid();

        // When notification is tapped, call MainActivity.
        Intent mainIntent = new Intent(context, AddMedicineFragment.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 22222, mainIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationManager myNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        // Prepare notification.
        Toast.makeText(context, "NAmr"+message, Toast.LENGTH_SHORT).show();
        Notification.Builder builder = new Notification.Builder(context);
        builder.setSmallIcon(R.drawable.ic_baseline_add_alert_24)
                .setContentTitle("It's Time!")
                .setContentText(message)
                .setWhen(System.currentTimeMillis())
                .setAutoCancel(true)
                .setContentIntent(contentIntent)
                .setPriority(Notification.PRIORITY_MAX)
                .setDefaults(Notification.DEFAULT_ALL);
//        Map<String,Object> user = new HashMap<>();
//        user.put("MedicineName", message);
//        user.put("Time", alarm);
//        user.put("username", username);
//
//        db.collection("notification")
//                .document(userId)
//                .collection("notification")
//                .add(user)
//                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//                    @Override
//                    public void onSuccess(DocumentReference documentReference) {
//                        Log.d("TAG", "DocumentSnapshot added with ID: " + filename);
//                        Toast.makeText(context,"DocumentSnapshot added with ID: " + documentReference.getId(),Toast.LENGTH_SHORT).show();
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Log.w("TAG", "Error adding document", e);
//                    }
//                });

        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Ringtone r = RingtoneManager.getRingtone(context, notification);
        r.play();
        // Notify
        myNotificationManager.notify(notificationId, builder.build());
        Vibrator vibrator = (Vibrator)context
                .getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(5000);
    }
}
