package com.example.notification;

import android.annotation.SuppressLint;
import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.media.AudioAttributes;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

public class CreateChannelId extends Application {

    public static final String CHANNEL_ID_1 = "CHANNEL_1";
    public static final String CHANNEL_ID_2 = "CHANNEL_2";


    @Override
    public void onCreate() {
        super.onCreate();

        createNotificationChannel_1();
        createNotificationChannel_2();
    }

    private void createNotificationChannel_1() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Uri uri = RingtoneManager.getDefaultUri( RingtoneManager.TYPE_NOTIFICATION );
            Uri sound = Uri.parse( "android:resource://"+ getPackageName() + "/" + R.raw.sound_notification_custom );
            AudioAttributes attributes = new AudioAttributes.Builder()
                    .setUsage( AudioAttributes.USAGE_NOTIFICATION )
                    .build();


            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance_1 = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel_1 = new NotificationChannel(CHANNEL_ID_1, name, importance_1);
            channel_1.setDescription(description);
            channel_1.setSound( uri,attributes );
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel_1);
        }
    }

    private void createNotificationChannel_2() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Uri uri = RingtoneManager.getDefaultUri( RingtoneManager.TYPE_NOTIFICATION );
            Uri sound = Uri.parse( "android:resource://"+ getPackageName() + "/" + R.raw.sound_notification_custom );
            AudioAttributes attributes = new AudioAttributes.Builder()
                    .setUsage( AudioAttributes.USAGE_NOTIFICATION )
                    .build();


            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_MAX;
            @SuppressLint("WrongConstant") NotificationChannel channel_2 = new NotificationChannel(CHANNEL_ID_2, name, importance);
            channel_2.setDescription(description);
            channel_2.setSound( uri,attributes );
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel_2);
        }
    }
}
