package com.example.mustardseed.ui.profile;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.mustardseed.MainActivity;
import com.example.mustardseed.MainReceiver;
import com.example.mustardseed.Notification;
import com.example.mustardseed.R;
import com.example.mustardseed.TimePickerFragment;
import com.example.mustardseed.User;
import com.google.gson.Gson;


public class ProfileFragment extends Fragment {

    public static final String NOTIFICATION_CHANNEL_ID = "10001";
    private final static  String default_notification_channel_id = "default";

    private Button _btnSave;
    private EditText _etFullName;
    private  EditText _etFavScripture;
    private EditText _etNotification;
    private Switch _notifEnabled;

    private static final String TAG = "ProfileFragment";
    private ProfileViewModel profileViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        Log.e(TAG, "profileFragment was initialized");
        profileViewModel =
                ViewModelProviders.of(this).get(ProfileViewModel.class);
        View root = inflater.inflate(R.layout.fragment_profile, container, false);

        //Get UI for manipulation
        _btnSave = root.findViewById(R.id.btnSave);
        _etFullName = root.findViewById(R.id.etFullName);
        _etFavScripture = root.findViewById(R.id.etFavScripture);
        _etNotification = root.findViewById(R.id.etNotifTime);
        _notifEnabled = root.findViewById(R.id.switchEnabled);

        // setup save btn click listener
        Log.i(TAG, "Create listener");
        _btnSave.setOnClickListener(this::onSaveClick);
        _etNotification.setOnClickListener(this::showTimePickerDialog);


        SharedPreferences preferences = this.getActivity().getSharedPreferences("prefs", Context.MODE_PRIVATE);
        String gUser = preferences.getString("user", null);
        String gNotification = preferences.getString("notification", null);

        Gson gson = new Gson();
        User user = gson.fromJson(gUser,User.class);
        Notification notification = gson.fromJson(gNotification, Notification.class);

        if(user != null) {
            Log.i(TAG, "Load stored preferences");
            _etFullName.setText(user.getName());
            _etFavScripture.setText(user.getFavScripture());
            _notifEnabled.setChecked(user.isNotificationEnabled());

            Log.i(TAG, "Finish loading stored preferences");
        }
        if(notification != null){
            _etNotification.setText(notification.getHour() + ":" + String.format("%02d", notification.getMinute()));
        }


        return root;
    }

    public void onSaveClick(View view){
        Log.i("ProfileFragment", "Save clicked");

        String sFullName = _etFullName.getText().toString();
        String sFavScripture = _etFavScripture.getText().toString();
        boolean isEnabled = _notifEnabled.isChecked();

        User user = new User(sFullName, sFavScripture, isEnabled);
        Gson gson = new Gson();

        String gUser = gson.toJson(user);

        SharedPreferences sharedPref = view.getContext().getSharedPreferences("prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = sharedPref.edit();
        prefEditor.putString("user", gUser);
        prefEditor.commit();

        SharedPreferences preferences = this.getActivity().getSharedPreferences("prefs", Context.MODE_PRIVATE);
        String gNotification = preferences.getString("notification", null);

        if(_notifEnabled.isChecked()){
            Gson gsonLoad = new Gson();
            Notification notification = gsonLoad.fromJson(gNotification, Notification.class);

            scheduleNotification(getNotification(),notification.getTimeInMillis());
        } else {
            AlarmManager alarmManager = (AlarmManager) this.getActivity().getSystemService(Context.ALARM_SERVICE);
            Intent notificationIntent = new Intent(this.getActivity(), MainReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this.getActivity(), 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            if(alarmManager != null){
                alarmManager.cancel(pendingIntent);
            }
        }

        Toast.makeText(view.getContext().getApplicationContext(),"Your profile has been saved successfully", Toast.LENGTH_LONG).show();
    }

    public void showTimePickerDialog(View view) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(this.getActivity().getSupportFragmentManager(), "timePicker");

        Log.i(TAG, "TimerPicker Clicked!");
    }

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