package com.example.mustardseed;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import static com.example.mustardseed.MainActivity.NOTIFICATION_CHANNEL_ID;

public class MainReceiver extends BroadcastReceiver {

    public  static String NOTIFICATION_ID = "notification-id";
    public static String NOTIFICATION = "notification";
    public void onReceive(Context context, Intent intent) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = intent.getParcelableExtra(NOTIFICATION);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "NOTIFICATION_CHANNEL_NAME", importance);
            assert notificationManager != null;
            notificationManager.createNotificationChannel(notificationChannel);
        }
        int id = intent.getIntExtra(NOTIFICATION_ID, 0);
        assert notificationManager != null;
        notificationManager.notify(id, notification);
    }
//    @Override
//    public void onReceive(Context context, Intent intent) {
//        Intent intent1 = new Intent(context, MainIntentService.class);
//        context.startService(intent1);
//    }
}
