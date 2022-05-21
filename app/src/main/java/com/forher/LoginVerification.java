package com.forher;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginVerification extends AppCompatActivity {

    private final LoginCredentials credentials = new LoginCredentials();
    private FirebaseDatabase database;
    private DatabaseReference testRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_verification);

        String email = credentials.getEmail();
        String password = credentials.getPassword();

        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, task -> {
            if (task.isSuccessful()){
                Toast.makeText(this, "Welcome back", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(LoginVerification.this, Home.class));
            }
            else {
                Toast.makeText(this, "Couldn't verify credentials", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(LoginVerification.this, Login.class));
            }
            finish();
        });
    }
}