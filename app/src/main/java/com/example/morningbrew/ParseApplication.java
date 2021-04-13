package com.example.morningbrew;

import android.app.Application;
import com.parse.Parse;
import com.parse.ParseObject;


public class ParseApplication extends Application {

    // Initializes Parse SDK as soon as the application is created
    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("4SaobsNgMx0UOiSoqPrcGAcTqGvCObEaQMKkBwow")
                .clientKey("hnrtVbO0UZPvN9M14LVbVkt2D7PZ2TLDMVJJ1OoN")
                .server("https://parseapi.back4app.com")
                .build()
        );
    }
}

