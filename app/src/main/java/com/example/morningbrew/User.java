package com.example.morningbrew;

import com.parse.ParseClassName;
import com.parse.ParseObject;

@ParseClassName("User")
public class User extends ParseObject {
    public static final String OBJECT = "objectId";
    public static final String USER_NAME = "username";
    public static final String TIME = "time";
    public static final String ZIPCODE = "zipcode";
    public static final String CREATE_KEY = "createdAt";

    public User() {
    }

    public String getUserName() {
        return getString(USER_NAME);
    }

    public String getTIME() {
        return getString(TIME);
    }

    public String getZIPCODE() {
        return getString(ZIPCODE);
    }

    public String getCreateKey() {
        return getString(CREATE_KEY);
    }

}
