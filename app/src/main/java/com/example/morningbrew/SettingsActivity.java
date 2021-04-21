package com.example.morningbrew;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class SettingsActivity extends AppCompatActivity {
    private static final String TAG = "SettingsActivity";
    TextView tvTimer;
    int hour, min;
    String time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        tvTimer=findViewById(R.id.tvTimer);
        
        tvTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog= new TimePickerDialog(
                        SettingsActivity.this,
                        new TimePickerDialog.OnTimeSetListener(){
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                hour=hourOfDay;
                                min=minute;
                                Calendar calendar=Calendar.getInstance();
                                calendar.set(0,0,0,hour,min);
                                time=String.valueOf(DateFormat.format("hh:mm aa",calendar));
                                tvTimer.setText(DateFormat.format("hh:mm aa",calendar));
                            }
                        },12,0,false
                );
                timePickerDialog.updateTime(hour,min);
                timePickerDialog.show();
                Log.i(TAG,"Nice"+tvTimer.getText().toString());
            }
        });


    }



    private void goMainActivity() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
}