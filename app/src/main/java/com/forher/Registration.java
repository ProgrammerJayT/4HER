package com.forher;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class Registration extends AppCompatActivity {
    private TextView name, surname, phone, address, email, password, confirm, submit;

    RegistrationForm form;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        name = findViewById(R.id.reg_name);
        surname = findViewById(R.id.reg_surname);
        phone = findViewById(R.id.reg_phone);
        address = findViewById(R.id.reg_address);
        email = findViewById(R.id.reg_email);
        password = findViewById(R.id.reg_password);
        confirm = findViewById(R.id.reg_confirm);
        submit = findViewById(R.id.reg_submit);

        form = new RegistrationForm();

        submit.setOnClickListener(view -> {
            String getName = name.getText().toString().trim(),
                    getSurname = surname.getText().toString().trim(),
                    getAddress = address.getText().toString().trim(),
                    getPhone = phone.getText().toString().trim(),
                    getEmail = email.getText().toString().trim(),
                    getPassword = password.getText().toString().trim(),
                    getConfirm = confirm.getText().toString().trim();

            if (getName.isEmpty() ||
                    getSurname.isEmpty() ||
                    getAddress.isEmpty() ||
                    getPhone.isEmpty() ||
                    getEmail.isEmpty() ||
                    getPassword.isEmpty() ||
                    getConfirm.isEmpty()){

                Toast.makeText(this, "All fields are mandatory", Toast.LENGTH_SHORT).show();
            } else {

                if (!getPassword.equals(getConfirm)){
                    Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                } else {
                    form.setRegName(getName);
                    form.setRegSurname(getSurname);
                    form.setRegPhone(getPhone);
                    form.setRegAddress(getAddress);
                    form.setRegEmail(getEmail);
                    form.setRegPassword(getPassword);

                    startActivity(new Intent(Registration.this, RegistrationVerification.class));
                    finish();
                }
            }
        });
    }
}