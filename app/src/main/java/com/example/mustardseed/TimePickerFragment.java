package com.example.mustardseed;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TimePicker;

import androidx.core.app.NotificationCompat;
import androidx.fragment.app.DialogFragment;

import com.google.gson.Gson;

import java.util.Calendar;

public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {
    public static final String NOTIFICATION_CHANNEL_ID = "10001";
    private final static  String default_notification_channel_id = "default";

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

        EditText _etNotification = this.getActivity().findViewById(R.id.etNotifTime);
        // format minute with a prepended zero if single digit
        String sMinute = String.format("%02d", minute);
        _etNotification.setText(hourOfDay + ":" + sMinute);

        final Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        long millis = calendar.getTimeInMillis();

        Log.i("TimePickerFormat", "Time set in edit text view");
        Notification notification = new Notification(hourOfDay, minute, millis);

        Gson gson = new Gson();

        String gNotification = gson.toJson(notification);

        SharedPreferences sharedPref = view.getContext().getSharedPreferences("prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = sharedPref.edit();
        prefEditor.putString("notification", gNotification);
        prefEditor.commit();
        scheduleNotification(getNotification(),millis);

        Log.i("TimePickerFragment", "saved notification");
    }


//    private void setAlarm(long millis){
//        Intent notifyIntent = new Intent(this.getActivity(), MainReceiver.class);
//        PendingIntent pendingIntent = PendingIntent.getBroadcast
//                (this.getActivity(), 3, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//        AlarmManager alarmManager = (AlarmManager) this.getActivity().getSystemService(Context.ALARM_SERVICE);
//        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,  millis,
//                AlarmManager.INTERVAL_FIFTEEN_MINUTES, pendingIntent);
//    }
    private void scheduleNotification (android.app.Notification notification, long millis) {
        Intent notificationIntent = new Intent(this.getActivity(), MainReceiver.class);
        notificationIntent.putExtra(MainReceiver.NOTIFICATION_ID, 1);
        notificationIntent.putExtra(MainReceiver.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this.getActivity(), 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) this.getActivity().getSystemService(Context.ALARM_SERVICE);
        assert alarmManager != null;
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,  millis,
                AlarmManager.INTERVAL_DAY, pendingIntent);
        //alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, millis + 30000, pendingIntent);
    }

    private android.app.Notification getNotification () {
        Intent notificationIntent = new Intent(this.getActivity(), MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this.getActivity(), 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this.getActivity(), default_notification_channel_id);
        builder.setContentTitle("Scripture Time");
        builder.setContentText("Did you think to read? Mark your progress!");
        builder.setSmallIcon(R.drawable.ic_access_alarm);
        builder.setChannelId(NOTIFICATION_CHANNEL_ID);
        builder.setContentIntent(pendingIntent);
        return  builder.build();
    }
}
