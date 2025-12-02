package com.example.alarm_miniproject.service;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;
import android.os.Vibrator;
import android.util.Log;

import com.example.alarm_miniproject.Database.AlarmData;
import com.example.alarm_miniproject.Database.DataManager;
import com.example.alarm_miniproject.R;
import com.example.alarm_miniproject.adapters.MyAdapterBaothuc;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

public class AlarmService extends Service {
    MediaPlayer mediaPlayer;
    Vibrator vibrator;
    int idAlarm;
    long position;
    private static final String TAG = "AlarmService";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        String action = intent.getAction();
        if (action != null && action.equals( "action_stop" )) {
            DataManager dataManager=new DataManager( this );
            List <AlarmData> list = dataManager.getAlarmDao().getAll();
            AlarmData alarmData = new AlarmData();

            for (AlarmData al : list) {
                if (al.id == position) {
                    alarmData = al;
                }
            }
            Log.d( "pos stop", position + "" );
            alarmData.setBattat( false );
            dataManager.getAlarmDao().update( alarmData );
            Log.d( TAG, "Stop" );
            mediaPlayer.stop();
            stopForeground( flags );
            stopSelf();

        } else {
            // String getI= intent.getStringExtra("id");

            position = intent.getLongExtra( "pos", 0 );
            Log.d( "name", intent.getStringExtra( "name" ) + "" );
            Log.d( "pos", position + "" );
            Log.d( TAG, "Start" );
            mediaPlayer = MediaPlayer.create( this, R.raw.rengreng );
            mediaPlayer.setLooping( true );


            Intent notificationIntent = new Intent( this, AlarmService.class );
            notificationIntent.setAction( "action_stop" );
            PendingIntent pendingIntent = PendingIntent.getForegroundService( this, 0, notificationIntent, 0 );

            String alarmTitle = "Báo thức";
            createNotificationChannnel( "CHANNEL_ID", "channel_name" );
            Notification notification = new NotificationCompat.Builder( this, "CHANNEL_ID" )
                    .setContentTitle( alarmTitle )
                    .setContentText( "Ring Ring .. Ring Ring" )
                    .setSmallIcon( R.drawable.ic_bamgio )
                    .setContentIntent( pendingIntent )
                    .build();
            mediaPlayer.start();
            startForeground( 1, notification );
        }
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void createNotificationChannnel(String channelId, String channelName) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    channelId,
                    channelName,
                    NotificationManager.IMPORTANCE_HIGH
            );

            NotificationManager manager = getSystemService( NotificationManager.class );
            manager.createNotificationChannel( serviceChannel );
        }
    }
}
