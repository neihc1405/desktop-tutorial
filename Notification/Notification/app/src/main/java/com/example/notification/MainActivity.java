package com.example.notification;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private static final CharSequence CONTENT_TEXT_NOTIFICATION = "AHBFHBSKDJBAJHDA ABJDJAHDBAJHDBAJHDA AJAHDBAJDHBAHD ADHADKJBDJAKD AJKDAKJDBAJH HFGVSJDHSH AJHSAHJVSAJHSB JHSDJSHDJH  SJDHBJSHBDSD SJHDBJN JHSSHDBS SHDBJH";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        Button btnSendNotification_1 = findViewById( R.id.btn_send_notification_1 );
        btnSendNotification_1.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SendNotification_1();
            }
        } );
        Button btnSendNotification_2 = findViewById( R.id.btn_send_notification_2 );
        btnSendNotification_2.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SendNotification_2();
            }
        } );
    }

    private void SendNotification_2() {
        Bitmap bitmap= BitmapFactory.decodeResource( getResources(), R.drawable.ic_launcher_foreground );
        Uri uri = RingtoneManager.getDefaultUri( RingtoneManager.TYPE_NOTIFICATION );
        Uri sound = Uri.parse( "android:resource://"+ getPackageName() + "/" + R.raw.sound_notification_custom );
        Notification notification = new NotificationCompat.Builder( this ,CreateChannelId.CHANNEL_ID_2)
                .setContentTitle( "Title push notification" )
                .setContentText( CONTENT_TEXT_NOTIFICATION )
                .setSmallIcon( R.drawable.ic_launcher_foreground )
                .setLargeIcon( bitmap)
                .setSound( uri )
                .setVisibility( NotificationCompat.VISIBILITY_PRIVATE )
                .setStyle( new NotificationCompat.BigTextStyle().bigText( CONTENT_TEXT_NOTIFICATION ) )
                .setStyle( new NotificationCompat.BigPictureStyle().bigPicture( bitmap ).bigLargeIcon( null ) )
                .build();
        NotificationManager notificationManager = (NotificationManager) getSystemService( Context.NOTIFICATION_SERVICE );
        if (notificationManager != null) {
            notificationManager.notify( getNotificationId(),notification );
        }
    }
    private void SendNotification_1() {
        Bitmap bitmap= BitmapFactory.decodeResource( getResources(), R.drawable.ic_launcher_foreground );
        Uri uri = RingtoneManager.getDefaultUri( RingtoneManager.TYPE_NOTIFICATION );
        Uri sound = Uri.parse( "android:resource://"+ getPackageName() + "/" + R.raw.sound_notification_custom );
        Notification notification = new NotificationCompat.Builder( this ,CreateChannelId.CHANNEL_ID_1)
                .setContentTitle( "Title push notification" )
                .setContentText( CONTENT_TEXT_NOTIFICATION )
                .setSmallIcon( R.drawable.ic_launcher_foreground )
                .setLargeIcon( bitmap)
                .setSound( uri )
                .setVisibility( NotificationCompat.VISIBILITY_PUBLIC )
                .setStyle( new NotificationCompat.BigTextStyle().bigText( CONTENT_TEXT_NOTIFICATION ) )
                .setStyle( new NotificationCompat.BigPictureStyle().bigPicture( bitmap ).bigLargeIcon( null ) )
                .build();
        NotificationManager notificationManager = (NotificationManager) getSystemService( Context.NOTIFICATION_SERVICE );
        if (notificationManager != null) {
            notificationManager.notify( getNotificationId(),notification );
        }
    }

    private int getNotificationId(){
        return (int) new Date().getTime();
    }
}