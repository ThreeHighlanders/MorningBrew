package com.example.morningbrew.fragments;

import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.morningbrew.LoginActivity;
import com.example.morningbrew.MainActivity;
import com.example.morningbrew.R;
import com.example.morningbrew.User;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import static com.parse.ParseQuery.*;

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
        ParseUser user= ParseUser.getCurrentUser();
        setField(user);
//        calendar = Calendar.getInstance();

        btnSet.setOnClickListener(new View.OnClickListener(){
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                // set the time for when the user wants to receive notifications
                hour = time.getHour();
                min = time.getMinute();

                setTime(hour, min);
                String zipcode = etZipcode.getText().toString();
                String set_Time= showTime.getText().toString();
                updateUser(set_Time,zipcode);
            }
        });
    }

    private void setField(ParseUser user) {
        String objectId= user.getObjectId();
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Users");
        query.whereEqualTo("objectId", objectId);
        query.getInBackground(objectId, new GetCallback<ParseObject>() {
            public void done(ParseObject user, ParseException e) {
                if (e == null) {
                    String time = user.getString("time");
                    String zipcode = user.getString("zipcode");
                    Log.i(TAG, "hi "+ time);
                    Log.i(TAG, "hi "+ time);
                    showTime.setText(time);
                    etZipcode.setText(zipcode);

                }
                else{
                    Log.e(TAG, "failfail",e);
                }
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

    private void updateUser(String time, String zipcode) {
        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser != null) {

            currentUser.put("time", time);
            currentUser.put("zipcode",zipcode);

            // Saves the object.
            currentUser.saveInBackground(e -> {
                if (e == null) {
                    //Save successful
                    Toast.makeText(getContext(), "Save Successful", Toast.LENGTH_SHORT).show();
                } else {
                    // Something went wrong while saving
                    Log.e(TAG, "Failure to update",e);
                }
            });
        }
    }
//    public void findUsers() {
//        ParseQuery<ParseUser> query = ParseUser.getQuery();
//        query.whereEqualTo("user", User.getUserName());
//        query.findInBackground((users, e) -> {
//            if (e == null) {
//                Us
//            } else {
//                // Something went wrong.
//                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
}
