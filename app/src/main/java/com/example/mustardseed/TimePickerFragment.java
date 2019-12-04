package com.example.mustardseed;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.EditText;
import android.widget.TimePicker;
import androidx.fragment.app.DialogFragment;

import com.google.gson.Gson;

import java.util.Calendar;

public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

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
}
