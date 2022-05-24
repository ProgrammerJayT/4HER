package com.forher;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistrationVerification extends AppCompatActivity {

    private RegistrationForm form;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private final DatabaseReference user = FirebaseDatabase.getInstance().getReference("user");
    private final DatabaseReference contacts = FirebaseDatabase.getInstance().getReference("emergency");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_verification);

        form = new RegistrationForm();
        mAuth = FirebaseAuth.getInstance();

        String name = form.getRegName(),
                surname = form.getRegSurname(),
                phone = form.getRegPhone(),
                address = form.getRegAddress(),
                email = form.getRegEmail(),
                password = form.getRegPassword();

        Toast.makeText(this, "Registering your account", Toast.LENGTH_SHORT).show();

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, task -> {

            if (task.isSuccessful()){
                mUser = mAuth.getCurrentUser();
                assert mUser != null;

                String userID = mUser.getUid();

                user.child(userID).child("id").setValue(userID);
                user.child(userID).child("name").setValue(name);
                user.child(userID).child("surname").setValue(surname);
                user.child(userID).child("phone").setValue(phone);
                user.child(userID).child("address").setValue(address);
                user.child(userID).child("email").setValue(email);

                contacts.child(userID).child("contact_1").child("name").setValue("Not set");
                contacts.child(userID).child("contact_1").child("number").setValue("Not set");
                contacts.child(userID).child("contact_2").child("name").setValue("Not set");
                contacts.child(userID).child("contact_2").child("number").setValue("Not set");
                contacts.child(userID).child("contact_3").child("name").setValue("Not set");
                contacts.child(userID).child("contact_3").child("number").setValue("Not set");

                Toast.makeText(this, "Account created", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(RegistrationVerification.this, Landing.class));
                finish();
            } else {
                Toast.makeText(this, "Failed to register account", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(RegistrationVerification.this, Registration.class));
                finish();
            }
        });
    }
}