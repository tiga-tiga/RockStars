package com.wbtech.rockstars.Activities;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.wbtech.rockstars.Commons.BaseActivity;
import com.wbtech.rockstars.Fragments.BookMarksFragment;
import com.wbtech.rockstars.Fragments.ProfileFragment;
import com.wbtech.rockstars.Fragments.RockStarsFragment;
import com.wbtech.rockstars.R;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.main_bottom_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(bottomNavListener);

        //Load RockStarsFragment
        getSupportFragmentManager().beginTransaction().replace(R.id.container_fragments, new RockStarsFragment()).commit();

    }

    //bottomNavigation Listener
    private BottomNavigationView.OnNavigationItemSelectedListener bottomNavListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;
            switch (item.getItemId()) {
                case R.id.rock_stars_nav:
                    selectedFragment = new RockStarsFragment();
                    break;
                case R.id.bookmarks_nav:
                    selectedFragment = new BookMarksFragment();
                    break;
                case R.id.profile_nav:
                    selectedFragment = new ProfileFragment();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.container_fragments, selectedFragment).commit();
            return true;

        }
    };
}
