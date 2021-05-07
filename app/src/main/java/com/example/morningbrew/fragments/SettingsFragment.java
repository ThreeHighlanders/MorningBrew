package com.example.morningbrew.fragments;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
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

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.morningbrew.Brew;
import com.example.morningbrew.BrewNotification;
import com.example.morningbrew.BrewNotificationReceiver;
import com.example.morningbrew.LoginActivity;
import com.example.morningbrew.R;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;

import okhttp3.Headers;

import static com.parse.Parse.getApplicationContext;

public class SettingsFragment extends Fragment {
    private static final String TAG = "SettingsFragment";
    public static String API_URL= "http://api.openweathermap.org/data/2.5/weather?";
    public static String URL_END = ",us&appid=d162c47b7d6374a9a98b555ade89ad29&units=imperial";
    private int low;
    private int high;
    public String desc;
    TextView showTime;
    TimePicker time;
    int hour, min;
    EditText etZipcode;
    Button btnSet;
    Button btnLogout;
    Calendar calendar = Calendar.getInstance();
    public String content;

    public SettingsFragment() {
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
        if (user != null) {
            setField(user);
            //getting user's set zipcode
            String zip = "zip="+user.getString("zipcode");
            API_URL = API_URL+""+zip;
        }
        else {//if no user, it gets set to default zipcode
            API_URL = API_URL+"zip=07103";
            Log.e(TAG, "no current user");
        }

        btnSet.setOnClickListener(new View.OnClickListener(){
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                // set the time for when the user wants to receive notifications
                hour = time.getHour();
                min = time.getMinute();

                //set zip zode and time in database
                String zipcode = etZipcode.getText().toString();
                String set_Time= showTime.getText().toString();
                updateUser(set_Time,zipcode);
                Log.i(TAG,"hour "+hour);
                Log.i(TAG,"min "+min);
                //set alarm
                setAlarm(hour, min);
                //show time in 12 hour notation
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ParseUser.logOut();
                goLoginActivity();
            }
        });

        AsyncHttpClient client= new AsyncHttpClient();
        API_URL = API_URL+""+URL_END;
        Log.i(TAG,API_URL);
        client.get(API_URL, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Headers headers, JSON json) {
                Log.i(TAG,"getting API URL");
                JSONObject jsonObject= json.jsonObject;
                try {
                    JSONObject main = jsonObject.getJSONObject("main");
                    low= main.getInt("temp_min");
                    high= main.getInt("temp_max");
                    JSONArray weather= jsonObject.getJSONArray("weather");
                    JSONObject jsonObject1= weather.getJSONObject(0);
                    desc= jsonObject1.getString("description");
                    content = desc+" "+low+" "+high;
                    Log.i(TAG,desc+" "+low+" "+high);
                    // save brew if there isn't one saved already
                    saveBrews(high,low,desc,user);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int i, Headers headers, String s, Throwable throwable) {
                Log.e(TAG,"onFailure",throwable);
            }
        });
    }

    private void goLoginActivity() {
        Intent i = new Intent(getContext(), LoginActivity.class);
        startActivity(i);
        getActivity().finish();
    }

    private void setField(ParseUser user) { // queries database to retrieve zipcode and time
        String username = user.getUsername();
        ParseQuery<ParseUser> query = ParseQuery.getQuery("User");
        query.whereEqualTo("username", username);
        query.getInBackground(user.getObjectId(), new GetCallback<ParseUser>() {
            public void done(ParseUser user, ParseException e) {
                if (e == null) {
                    String time = user.getString("time");
                    String zipcode = user.getString("zipcode");
                    Log.i(TAG, "hi "+ time);
                    Log.i(TAG, "hi "+ zipcode);
                    showTime.setText(time);
                    etZipcode.setText(zipcode);

                }
                else{
                    Log.e(TAG, "failfail",e);
                }
            }
        });
    }

    public void setAlarm(int hour, int min) {

        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, min);
        calendar.set(Calendar.SECOND, 0);

        //setting alarm to the time the user set
        Intent intent = new Intent(getContext(), BrewNotificationReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);

        if (alarmManager != null && getContext() != null) {
            Log.i(TAG, "setting alarm");
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);

            //enable boot receiver once alarm is set
            ComponentName receiver = new ComponentName(getContext(), BrewNotificationReceiver.class);
            PackageManager pm = getContext().getPackageManager();

            pm.setComponentEnabledSetting(receiver,
                    PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                    PackageManager.DONT_KILL_APP);

            BrewNotification brewNotification = new BrewNotification(getContext(), content);
            brewNotification.createNotification();
        }

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

    protected void saveBrews(int high,int low, String desc, ParseUser user){
        Brew brew= new Brew();
        brew.setHigh(high);
        brew.setLow(low);
        brew.setDescription(desc);
        brew.setUser(user);
        brew.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null){
                    Log.e(TAG, "Error saving brew in backend", e);
                    Toast.makeText(getContext(), "Error Posting!", Toast.LENGTH_SHORT).show();
                    return;
                }
                Log.i(TAG,"Post was successful");

            }
        });
    }
}
