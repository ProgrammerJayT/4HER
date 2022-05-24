package com.forher;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ContactsFrag extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public ContactsFrag() {
        // Required empty public constructor
    }

    public static ContactsFrag newInstance(String param1, String param2) {
        ContactsFrag fragment = new ContactsFrag();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        TextView showName1, showName2, showName3;
        TextView showNumber1, showNumber2, showNumber3;

        ImageView editContact1, editContact2, editContact3;
        ImageView deleteContact1, deleteContact2, deleteContact3;

        FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();

        assert mUser != null;
        String userID = mUser.getUid();
        DatabaseReference ref = FirebaseDatabase
                .getInstance().getReference("emergency").child(userID);

        View v = inflater.inflate(R.layout.fragment_contacts, container, false);

        showName1 = v.findViewById(R.id.contact_name_1);
        showNumber1  = v.findViewById(R.id.contact_phone_1);
        showName2 = v.findViewById(R.id.contact_name_2);
        showNumber2  = v.findViewById(R.id.contact_phone_2);
        showName3 = v.findViewById(R.id.contact_name_3);
        showNumber3  = v.findViewById(R.id.contact_phone_3);
        editContact1 = v.findViewById(R.id.edit_contact_1);
        editContact2 = v.findViewById(R.id.edit_contact_2);
        editContact3 = v.findViewById(R.id.edit_contact_3);
        deleteContact1 = v.findViewById(R.id.delete_contact_1);
        deleteContact2 = v.findViewById(R.id.delete_contact_2);
        deleteContact3 = v.findViewById(R.id.delete_contact_3);


        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String name1, name2, name3;
                String phone1, phone2, phone3;

                name1 = snapshot.child("contact_1").child("name").getValue(String.class);
                name2 = snapshot.child("contact_2").child("name").getValue(String.class);
                name3 = snapshot.child("contact_3").child("name").getValue(String.class);

                showName1.setText(name1);
                showName2.setText(name2);
                showName3.setText(name3);

                phone1 = snapshot.child("contact_1").child("number").getValue(String.class);
                phone2 = snapshot.child("contact_2").child("number").getValue(String.class);
                phone3 = snapshot.child("contact_3").child("number").getValue(String.class);

                showNumber1.setText(phone1);
                showNumber2.setText(phone2);
                showNumber3.setText(phone3);

                editContact1.setOnClickListener(view -> {
                    Toast.makeText(getContext(), "Editing 1", Toast.LENGTH_SHORT).show();
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        return v;
    }
}