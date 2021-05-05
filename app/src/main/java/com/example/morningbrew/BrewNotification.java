package com.example.morningbrew;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.provider.Settings;

import androidx.core.app.NotificationCompat;

public class BrewNotification {
    private Context notifContext;
    public String content = "";
    private static final String NOTIFICATION_CHANNEL_ID = "10001" ;

    public BrewNotification(Context notifContext, String content) {
        this.notifContext = notifContext;
        this.content = content;
    }

    public BrewNotification (Context context) {
        this.notifContext = context;
    }

    public void createNotification() {
        Intent intent = new Intent(notifContext , MainActivity.class);

        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        PendingIntent resultPendingIntent = PendingIntent.getActivity(notifContext,0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder nBuilder = new NotificationCompat.Builder(notifContext, NOTIFICATION_CHANNEL_ID);
        nBuilder.setSmallIcon(R.drawable.ic_baseline_brew_24);
        nBuilder.setContentTitle("Morning Brew")
                .setContentText("High: 57, Low: 52, overcast clouds")
                .setAutoCancel(false)
                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(resultPendingIntent);

        NotificationManager mNotificationManager = (NotificationManager) notifContext.getSystemService(Context.NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "NOTIFICATION_CHANNEL_NAME", importance);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            assert mNotificationManager != null;
            nBuilder.setChannelId(NOTIFICATION_CHANNEL_ID);
            mNotificationManager.createNotificationChannel(notificationChannel);
        }
        assert mNotificationManager != null;
        mNotificationManager.notify(0, nBuilder.build());
    }
}
