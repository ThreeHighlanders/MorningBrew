package com.example.morningbrew;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.morningbrew.fragments.HomeFragment;
import com.example.morningbrew.fragments.SettingsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
//    private Button settings;
//    private DrawerLayout mDrawer;
//    private Toolbar toolbar;
//    private NavigationView nvDrawer;
//
//    private ActionBarDrawerToggle drawerToggle;

    final FragmentManager fragmentManager = getSupportFragmentManager();
    private BottomNavigationView topNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        settings= findViewById(R.id.settings);
//        settings.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v) {
//                goSettingsActivity();
//            }
//        });

        topNavigationView = findViewById(R.id.topNavigation);
        topNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment;
                switch (item.getItemId()) {
                    case R.id.action_home:
                        fragment = new SettingsFragment();
                        break;
                    case R.id.action_settings:
                        fragment = new SettingsFragment();
                        break;
                    default:
                        fragment = new SettingsFragment();
                        break;
                }
                fragmentManager.beginTransaction().replace(R.id.flContainer, fragment).commit();
                return true;
            }
        });
        // Set default selection
        topNavigationView.setSelectedItemId(R.id.action_home);
    }


//    private void goSettingsActivity() {
//        Intent i = new Intent(this, SettingsActivity.class);
//        this.startActivity(i);
//    }
}