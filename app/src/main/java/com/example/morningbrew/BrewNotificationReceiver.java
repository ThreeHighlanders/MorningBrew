package com.example.morningbrew;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.morningbrew.fragments.SettingsFragment;

public class BrewNotificationReceiver extends BroadcastReceiver {
// listens when alarm happens and creates notification
    private final String TAG = "BrewNotificationReceive";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "broadcast received");
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            BrewNotification notificationHelper = new BrewNotification(context);
            notificationHelper.createNotification();
        }
    }
}
