package com.example.morningbrew;

import android.app.Application;

import androidx.appcompat.app.ActionBar;

import com.parse.Parse;
import com.parse.ParseObject;


public class ParseApplication extends Application {

    // Initializes Parse SDK as soon as the application is created
    @Override
    public void onCreate() {
        super.onCreate();

        ParseObject.registerSubclass(Brew.class);
        ParseObject.registerSubclass(User.class);
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("4I5sJqnKTsErJVqm6Ro9XuoebzdabXbasOFyn6Ff")
                .clientKey("04CRc5B34ips9EVzeXrxOFjKwhzsDO2VLMYfSkpD")
                .server("https://parseapi.back4app.com")
                .build()
        );
    }
}

