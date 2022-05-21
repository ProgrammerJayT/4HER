package com.forher;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class Login extends AppCompatActivity {

    TextView register, login, reset, greetings;
    FirebaseDatabase database;
    DatabaseReference testRef;
    TextInputEditText email, password;

    LoginCredentials credentials;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        register = findViewById(R.id.register_redirect);
        login = findViewById(R.id.login_button);
        reset = findViewById(R.id.reset_password);
        greetings = findViewById(R.id.login_greetings);
        email = findViewById(R.id.login_email);
        password = findViewById(R.id.login_password);

        database = FirebaseDatabase.getInstance();
        testRef = database.getReference("test");

        testRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String val = snapshot.getValue(String.class);
                if (!val.isEmpty()){
                    greetings.setText(val);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        login.setOnClickListener(view -> {
            String getEmail = Objects.requireNonNull(email.getText()).toString().trim();
            String getPassword = Objects.requireNonNull(password.getText()).toString().trim();

            if (getEmail.isEmpty() && getPassword.isEmpty()){
                Toast.makeText(this, "All fields are mandatory", Toast.LENGTH_SHORT).show();
            }
            else {

            }
        });

        register.setOnClickListener(view -> {
            startActivity(new Intent(Login.this, Registration.class));

        });

    }
}