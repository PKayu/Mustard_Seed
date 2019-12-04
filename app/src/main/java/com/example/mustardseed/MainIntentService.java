package com.example.mustardseed;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationManagerCompat;

class MainIntentService extends IntentService {
    private static final int NOTIFICATION_ID = 3;

    public MainIntentService() {
        super("MainIntentService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Notification.Builder builder = new Notification.Builder(this);
        builder.setContentTitle("Daily Reading");
        builder.setContentText("Did you think to read? Mark your progress!");
        builder.setSmallIcon(R.drawable.ic_access_alarm);

        Intent notifyIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 2, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        builder.setContentIntent(pendingIntent);
        Notification notificationCompat = builder.build();
        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
        managerCompat.notify(NOTIFICATION_ID,notificationCompat);
    }
}
