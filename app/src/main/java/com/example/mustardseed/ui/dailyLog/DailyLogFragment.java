package com.example.mustardseed.ui.dailyLog;

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

        // setup Calendar click listener
        _CalendarView.setOnDayClickListener(new OnDayClickListener() {
            @Override
            public void onDayClick(EventDay eventDay) {
                Log.i("DailyLogFragment", "Event Day clicked is: " + eventDay);
                Log.i("DailyLogFragment", "Before clicked _loggedDays has: " + _daysRead.size());
                Log.i("DailyLogFragment", "Before clicked currDay is: " + currDay);
                currDay = eventDay.getCalendar();
                _readingSwitch.setChecked(_daysRead.contains(currDay));
                Log.i("DailyLogFragment", "After click event Day clicked is: " + eventDay);
                Log.i("DailyLogFragment", "After clicked _loggedDays has: " + _daysRead.size());
                Log.i("DailyLogFragment", "After clicked currDay is: " + currDay);
            }
        });

        //TODO: I need to get the toggle state saved per day.
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
}