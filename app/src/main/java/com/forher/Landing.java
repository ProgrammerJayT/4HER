package com.forher;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;

public class Landing extends AppCompatActivity {

    MeowBottomNavigation bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);

        bottomNavigation = findViewById(R.id.meowBottomNavigation);

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
}