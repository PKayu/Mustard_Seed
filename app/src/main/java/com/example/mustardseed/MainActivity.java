package com.example.mustardseed;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.mustardseed.ui.Utils;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    public static final String NOTIFICATION_CHANNEL_ID = "10001";

    private AppBarConfiguration mAppBarConfiguration;
    private int previousGoalId = 0;
//    private CalendarView _CalendarView;
//    private List<EventDay> _loggedDays = new ArrayList<>();

    // App level variable to retain selected spinner value
    public static int currentPosition;

    //themes code
    private Spinner spThemes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        themes code
       // Utils.onActivityCreateSetTheme(this);

        setContentView(R.layout.activity_main);

        //themes code
        //setupSpinnerItemSelection();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_goal, R.id.nav_dailylog,
                R.id.nav_profile)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    //Commenting this out took away the three dots... might have other consequences.
    //@Override
    //public boolean onCreateOptionsMenu(Menu menu) {
    //    // Inflate the menu; this adds items to the action bar if it is present.
    //    getMenuInflater().inflate(R.menu.main, menu);
    //    return true;
    //}

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    //Themes code
//    private void setupSpinnerItemSelection() {
//        spThemes = (Spinner) findViewById(R.id.spThemes);
//        spThemes.setSelection(ThemesApplication.currentPosition);
//        ThemesApplication.currentPosition = spThemes.getSelectedItemPosition();
//
//        spThemes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view,
//                                       int position, long id) {
//                if (ThemesApplication.currentPosition != position) {
//                    Utils.changeToTheme(MainActivity.this, position);
//                }
//                ThemesApplication.currentPosition = position;
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });

//    }
}
