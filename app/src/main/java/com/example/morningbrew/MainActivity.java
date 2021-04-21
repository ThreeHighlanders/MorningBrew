package com.example.morningbrew;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        settings= findViewById(R.id.settings);


        settings.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                goSettingsActivity();
            }
        });
    }

    private void goSettingsActivity() {
        Intent i = new Intent(this, SettingsActivity.class);
        this.startActivity(i);
    }
}