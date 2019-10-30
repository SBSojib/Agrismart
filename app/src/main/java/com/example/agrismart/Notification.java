package com.example.agrismart;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class Notification extends Application {
    public static final String CHANNEL_1_ID = "Channel1";
    public static final String CHANNEL_2_ID = "Channel2";
    @Override
    public void onCreate() {
        super.onCreate();

        createNotificationsChannels();
    }

    private void createNotificationsChannels() {
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O) {
            NotificationChannel channel1 = new NotificationChannel(
                    CHANNEL_1_ID,
                    "channel 1",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel1.setDescription("This is channel1");

            NotificationChannel channel2 = new NotificationChannel(
                    CHANNEL_2_ID,
                    "channel 2",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel2.setDescription("This is channel2");

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel1);
            manager.createNotificationChannel(channel2);
        }
    }
}

