package com.example.morningbrew;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.util.Date;

@ParseClassName("Brew")
public class Brew extends ParseObject {

    public static final String DESCRIPTION = "description";
    public static final String HIGH = "high";
    public static final String LOW = "low";
    public static final String USER = "user";
    public static final String CREATE_KEY = "createdAt";

    public Brew() {
    }

    public String getDescription() {
        return getString(DESCRIPTION);
    }

    public void setDescription(String description) {
        put(DESCRIPTION, description);
    }

    public int getHigh() {
        return getInt(HIGH);
    }

    public void setHigh(int high) {
        put(HIGH, high);
    }

    public void setLow(int low) {
        put(LOW, low);
    }

    public int getLow() {
        return getInt(LOW);
    }

    public ParseUser getUser() {
        return getParseUser(USER);
    }

    public void setUser(ParseUser user) {
        put(USER, user);
    }

    public Date getCreatedKey() {
        return getDate(CREATE_KEY);
    }

}
