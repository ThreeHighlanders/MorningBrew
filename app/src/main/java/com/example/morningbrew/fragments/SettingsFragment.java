package com.example.morningbrew.fragments;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.morningbrew.MainActivity;
import com.example.morningbrew.R;
import com.example.morningbrew.SettingsActivity;

import java.util.Calendar;

public class SettingsFragment extends Fragment {
    private static final String TAG = "SettingsFragment";
    TextView showTime;
    TimePicker time;
    int hour, min;
    EditText etZipcode;
    Button btnSet;
    Button btnLogout;
//    Calendar calendar;

    public SettingsFragment () {
        //empty constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        time = (TimePicker) view.findViewById(R.id.timePicker);
        etZipcode = view.findViewById(R.id.etZipcode);
        showTime = view.findViewById(R.id.tvShowTime);
        btnSet = view.findViewById(R.id.btnSet);
        btnLogout = view.findViewById(R.id.btnLogout);
//        calendar = Calendar.getInstance();

        btnSet.setOnClickListener(new View.OnClickListener(){
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                // set the time for when the user wants to receive notifications
                hour = time.getHour();
                min = time.getMinute();

                setTime(hour, min);
            }
        });
    }

    private void setTime(int hour, int min) {
        String am_pm;
        if (hour == 0) {
            hour += 12;
            am_pm = "AM";
        } else if (hour == 12) {
            am_pm = "PM";
        } else if (hour > 12) {
            hour -= 12;
            am_pm = "PM";
        } else {
            am_pm = "AM";
        }

        showTime.setText(new StringBuilder().append(hour).append(" : ").append(min)
                .append(" ").append(am_pm));
    }
}
