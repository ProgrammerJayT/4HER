package com.forher;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginVerification extends AppCompatActivity {

    LoginCredentials credentials = new LoginCredentials();
    FirebaseDatabase database;
    DatabaseReference testRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_verification);

        Toast.makeText(this, "Please wait while we verify your credentials", Toast.LENGTH_LONG).show();

    }
}