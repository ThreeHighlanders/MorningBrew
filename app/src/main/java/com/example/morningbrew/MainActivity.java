package com.example.morningbrew;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.morningbrew.fragments.HomeFragment;
import com.example.morningbrew.fragments.SettingsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    final FragmentManager fragmentManager = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_morning_brew);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        getMenuInflater().inflate(R.menu.menu_top_navigation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        Fragment fragment;
        switch (item.getItemId()) {
            case R.id.action_home:
                fragment = new HomeFragment();
                break;
            case R.id.action_settings:
                fragment = new SettingsFragment();
                break;
            default:
                fragment = new HomeFragment();
                break;
        }
        fragmentManager.beginTransaction().replace(R.id.flContainer, fragment).commit();
        return true;
    }
}