package com.example.mustardseed.ui.dailyLog;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;
import com.example.mustardseed.DailyLogLogic;
import com.example.mustardseed.R;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DailyLogFragment extends Fragment {

    private DailyLogViewModel dailyLogViewModel;
    private CalendarView _CalendarView;
    private Switch _readingSwitch;
    private List<EventDay> _loggedDays = new ArrayList<>();
    private List<Calendar> _daysRead = new ArrayList<>();
    private Calendar currDay;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dailyLogViewModel =
                ViewModelProviders.of(this).get(DailyLogViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dailylog, container, false);

        // Get views for manipulation
        _CalendarView = (CalendarView) root.findViewById(R.id.calendarView);
        _readingSwitch = (Switch) root.findViewById(R.id.readingSwitch);

        //Load sharedPref
        SharedPreferences preferences = this.getActivity().getSharedPreferences("prefs", Context.MODE_PRIVATE);
        String gDaysRead = preferences.getString("dailyLogDays", null);
        Log.i("DailyLogFragment", "Read Preference = " + gDaysRead);
        if (gDaysRead != null) {
            Gson gson = new Gson();
            _daysRead = (List<Calendar>) gson.fromJson(gDaysRead, List.class);
            Log.i("DailyLogFragment", "_daysRead after gson = " + _daysRead);
        }

        // setup Calendar click listener
        _CalendarView.setOnDayClickListener(new OnDayClickListener() {
            @Override
            public void onDayClick(EventDay eventDay) {
//                Log.i("DailyLogFragment", "Event Day clicked is: " + eventDay);
//                Log.i("DailyLogFragment", "Before clicked _daysRead has: " + _daysRead.size());
//                Log.i("DailyLogFragment", "Before clicked currDay is: " + currDay);
                currDay = eventDay.getCalendar();
                _readingSwitch.setChecked(_daysRead.contains(currDay));
//                Log.i("DailyLogFragment", "After click event Day clicked is: " + eventDay);
//                Log.i("DailyLogFragment", "After clicked _daysRead has: " + _daysRead.size());
//                Log.i("DailyLogFragment", "After clicked currDay is: " + currDay);
            }
        });

        // setup toggle for listener
        _readingSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Calendar selectedDate = currDay;
                System.out.println(currDay);
                if (isChecked) {
                    _daysRead.add(selectedDate);
                }
                else {
                   _daysRead.remove(selectedDate);
                }
                _CalendarView.setHighlightedDays(_daysRead);
            }
        });
        return root;
    }

    public void onPause() {
        super.onPause();
        SharedPreferences.Editor editor = this.getActivity().getSharedPreferences("prefs", Context.MODE_PRIVATE).edit();
        Gson gson = new Gson();

        String daysRead = gson.toJson(_daysRead);
        Log.i("DailyLogFragment", "Converted Gson = " + daysRead);
        editor.putString("dailyLogDays", daysRead);
        editor.commit();
    }
}