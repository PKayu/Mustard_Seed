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
import com.google.gson.reflect.TypeToken;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class DailyLogFragment extends Fragment {

    private DailyLogViewModel dailyLogViewModel;
    private CalendarView _CalendarView;
    private Switch _readingSwitch;
    private List<EventDay> _savedEventDays = new ArrayList<>();
    private List<Calendar> _daysRead = new ArrayList<>();
    private Calendar currCal;
    private LocalDate currDate;
    private List<LocalDate> _savedDates = new ArrayList<>();

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
//        preferences.edit().clear();
        String gDateString = preferences.getString("dailyLogDateArray", null);

        Gson gson = new Gson();
        String[] dateStrings = gson.fromJson(gDateString, String[].class);

        if(dateStrings != null) {
            for (int i = 0; i<dateStrings.length; i++){
                LocalDate ld = LocalDate.parse(dateStrings[i]);
                Date date = Date.from(ld.atStartOfDay(ZoneId.systemDefault()).toInstant());
                Calendar cal = Calendar.getInstance();
                cal.setTime(date);
                _daysRead.add(cal);
                _savedDates.add(ld);
            }
            Log.i("DailyLogFragment", "_daysRead = "+ _daysRead);
            Log.i("DailyLogFragment", "_savedDates = "+ _savedDates);

            //Make sure set days are Highlighted.
            _CalendarView.setHighlightedDays(_daysRead);

            // setup Calendar click listener
            _CalendarView.setOnDayClickListener(this::onDayClick);

            // setup toggle for listener
            _readingSwitch.setOnCheckedChangeListener(this::onCheckedChanged);
        }

        return root;
    }

    public void onPause() {
        super.onPause();
        SharedPreferences.Editor editor = this.getActivity().getSharedPreferences("prefs", Context.MODE_PRIVATE).edit();
        Gson gson = new Gson();

        //Change each LocalDate in the savedDates to a String[] then serialize and save
        String[] dateStrings = new String[_savedDates.size()];
        for (int i = 0; i< _savedDates.size(); i++){
            dateStrings[i] = _savedDates.get(i).toString();
        }
        String gDateString = gson.toJson(dateStrings);
        Log.i("DailyLogFragment", "String[] dateStrings in JSON =" + gDateString);
        editor.putString("dailyLogDateArray", gDateString);
        editor.commit();
    }

    public void onDayClick(EventDay eventDay) {
//                Log.i("DailyLogFragment", "Event Day clicked is: " + eventDay);
//                Log.i("DailyLogFragment", "Before clicked _daysRead has: " + _daysRead.size());
//                Log.i("DailyLogFragment", "Before clicked currDay is: " + currDay);
        currCal = eventDay.getCalendar();
        currDate = currCal.getTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        _readingSwitch.setChecked(_savedDates.contains(currDate));
//                Log.i("DailyLogFragment", "After click event Day clicked is: " + eventDay);
//                Log.i("DailyLogFragment", "After clicked _daysRead has: " + _daysRead.size());
        Log.i("DailyLogFragment", "After clicked currDay is: " + currDate);
        Log.i("DailyLogFragment", "_daysRead = " + _daysRead);
        Log.i("DailyLogFragment", "_savedDates = " + _savedDates);
    }

    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        Calendar selectedCal = currCal;
        LocalDate selectedDate = currDate;
        System.out.println(currDate);
        if (isChecked) {
            if(_savedDates.contains(currDate)){

            }else {
                _daysRead.add(selectedCal);
                _savedDates.add(selectedDate);
            }
        }
        else {
            _daysRead.remove(selectedCal);
            _savedDates.remove(selectedDate);
        }
        Log.i("DailyLogFragment", "_dailyLog after toggle = " + _daysRead);
        Log.i("DailyLogFragment", "_savedDates after toggle = " + _savedDates);
        _CalendarView.setHighlightedDays(_daysRead);
    }
}