package com.forher;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileFrag extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public ProfileFrag() {
        // Required empty public constructor
    }

    public static ProfileFrag newInstance(String param1, String param2) {
        ProfileFrag fragment = new ProfileFrag();
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

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("user");
        FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();

        assert mUser != null;
        String userID = mUser.getUid();

        TextView name, email, phone, address;

        View v = inflater.inflate(R.layout.fragment_profile, container, false);

        name = v.findViewById(R.id.profile_show_name);
        email = v.findViewById(R.id.profile_show_email);
        phone = v.findViewById(R.id.profile_show_phone);
        address = v.findViewById(R.id.profile_show_address);

        ref.child(userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String getName = snapshot.child("name").getValue(String.class) + " " + snapshot.child("surname").getValue(String.class);
                String getEmail = snapshot.child("email").getValue(String.class);
                String getPhone = snapshot.child("phone").getValue(String.class);
                String getAddress = snapshot.child("address").getValue(String.class);

                name.setText(getName);
                email.setText(getEmail);
                phone.setText(getPhone);
                address.setText(getAddress);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Something went wrong " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        return v;
    }
}