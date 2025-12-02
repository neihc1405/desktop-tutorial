package com.example.alarm_miniproject.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Vibrator;
import android.util.Log;

import com.example.alarm_miniproject.service.AlarmService;
import com.example.alarm_miniproject.Database.AlarmData;

import java.util.ArrayList;
import java.util.List;

public class BaothucReceiver extends BroadcastReceiver {

    private static final String Tag = "BaothucReceiver";
    Vibrator vibrator;
    MediaPlayer mediaPlayer;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e( Tag, "Bao thuc den" );

        long pos = intent.getLongExtra( "pos", 0 );
        Log.d( Tag, "id " + pos );

        Intent intent1 = new Intent( context, AlarmService.class );
        intent1.putExtra( "name", intent.getStringExtra( "name" ) );
        intent1.putExtra( "pos", pos );
        context.startForegroundService( intent1 );
    }


}
