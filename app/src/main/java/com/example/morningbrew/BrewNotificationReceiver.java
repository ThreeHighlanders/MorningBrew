package com.example.morningbrew;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BrewNotificationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            BrewNotificationBuilder notificationHelper = new BrewNotificationBuilder(context);
            notificationHelper.createNotification();
        }
    }
}
