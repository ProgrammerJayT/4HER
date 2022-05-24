package com.forher;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.provider.Settings;
import android.telephony.SmsManager;
import android.widget.Toast;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Landing extends AppCompatActivity implements SensorEventListener {

    private MeowBottomNavigation bottomNavigation;
    private SensorManager sensorManager;
    private Sensor accelerometerSensor;
    private boolean isAccelerometerSensorAvailable, isNotFirstTime = false;
    private float currentX, currentY, currentZ;
    private float lastX, lastY, lastZ;
    private float xDifference, yDifference, zDifference;
    private float shakeThreshold = 20f;
    private Vibrator vibrator;
    private int numShakes = 0;
    private SmsManager smsManager;
    private static final int REQUEST_LOCATION = 1;
    private LocationManager locationManager;
    private String latitude, longitude;
    private FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
    private DatabaseReference emergency = FirebaseDatabase.getInstance().getReference("emergency");
    String c1,c2, c3;
    String n1, n2, n3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);

        String userID = mUser.getUid();

        ActivityCompat.requestPermissions(Landing.this, new String[]{
                Manifest.permission.SEND_SMS, Manifest.permission.READ_SMS},
                PackageManager.PERMISSION_GRANTED);

        if (ContextCompat.checkSelfPermission(Landing.this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(Landing.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    PackageManager.PERMISSION_GRANTED);
        }

        bottomNavigation = findViewById(R.id.meowBottomNavigation);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        smsManager = SmsManager.getDefault();

        emergency.child(userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                c1 = snapshot.child("contact_1").child("number").getValue(String.class);
                c2 = snapshot.child("contact_2").child("number").getValue(String.class);
                c3 = snapshot.child("contact_3").child("number").getValue(String.class);

                n1 = snapshot.child("contact_1").child("name").getValue(String.class);
                n2 = snapshot.child("contact_2").child("name").getValue(String.class);
                n3 = snapshot.child("contact_3").child("name").getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Landing.this, "Something went wrong " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        if (sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null) {
            accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            isAccelerometerSensorAvailable = true;
        }
        else {
            Toast.makeText(this, "Accelerometer sensor not available", Toast.LENGTH_SHORT).show();
            isAccelerometerSensorAvailable = false;
        }

        bottomNavigation.add(new MeowBottomNavigation.Model(1, R.drawable.home));
        bottomNavigation.add(new MeowBottomNavigation.Model(2, R.drawable.contacts));
        bottomNavigation.add(new MeowBottomNavigation.Model(3, R.drawable.person));

        bottomNavigation.setOnShowListener(item -> {
            Fragment fragment = null;

            switch (item.getId()) {
                case 1:
                    fragment = new HomeFrag();
                    break;

                case 2:
                    fragment = new ContactsFrag();
                    break;

                case 3:
                    fragment = new ProfileFrag();
                    break;

                default:
                    break;
            }
            
            loadFragment(fragment);
        });

        bottomNavigation.show(1, true);

        bottomNavigation.setOnClickMenuListener(item -> {

        });

        bottomNavigation.setOnReselectListener(item -> {

        });


    }

    private void loadFragment(Fragment fragment){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment, null)
                .commit();
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        currentX = sensorEvent.values[0];
        currentY = sensorEvent.values[1];
        currentZ = sensorEvent.values[2];

        if (isNotFirstTime){

            xDifference = Math.abs(lastX - currentX);
            yDifference = Math.abs(lastY - currentY);
            zDifference = Math.abs(lastZ - currentZ);

            if (xDifference > shakeThreshold && yDifference > shakeThreshold ||
            xDifference > shakeThreshold && zDifference >shakeThreshold ||
            yDifference > shakeThreshold && zDifference > shakeThreshold) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
                }
                else {
                    vibrator.vibrate(500);

                }

                numShakes = numShakes + 1;

                if(numShakes == 4){

                    if (c1.length() < 10 && c2.length() < 10 && c3.length() < 10) {
                        Toast.makeText(this, "No Emergency contact was found", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        String message = "User might be in danger, and you've been selected as an emergency contact. This is where she " + latitude + " " + longitude;

                        if (c1.length() == 10){
                            String number = c1;
                            smsManager.sendTextMessage(number, null, message, null, null);
                        }
                        if (c2.length() == 10){
                            String number = c2;
                            smsManager.sendTextMessage(number, null, message, null, null);
                        }
                        if (c3.length() == 10){
                            String number = c3;
                            smsManager.sendTextMessage(number, null, message, null, null);
                        }

                        Toast.makeText(this, "Sending emergency SMSs", Toast.LENGTH_SHORT).show();
                    }
                    numShakes = 0;
                }
            }
        }

        lastX = currentX;
        lastY = currentY;
        lastZ = currentZ;

        isNotFirstTime= true;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    protected void onResume() {
        super.onResume();

        if (isAccelerometerSensorAvailable) {
            sensorManager.registerListener(this, accelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (isAccelerometerSensorAvailable) {
            sensorManager.unregisterListener(this);
        }
    }
}