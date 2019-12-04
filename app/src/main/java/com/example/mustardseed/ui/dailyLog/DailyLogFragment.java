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
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;
import com.example.mustardseed.DailyLogLogic;
import com.example.mustardseed.Goal;
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
import java.util.Collections;
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
    private TextView _currentGoal;
    private int _currStreak;
    private int _maxStreak;
    private LocalDate _today;

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
        _maxStreak = preferences.getInt("maxStreak", 0);
        _currStreak = preferences.getInt("currStreak", 0);

        Gson gson = new Gson();
        String[] dateStrings = gson.fromJson(gDateString, String[].class);

        _today = LocalDate.now();

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

            //Make sure set days are Highlighted and current day checked if applicable.
            _CalendarView.setHighlightedDays(_daysRead);
            _readingSwitch.setChecked(_savedDates.contains(_today));

            //Get shared preferences of the goal and display
            setCurrentGoal(root);

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

        // Save streak data
        checkLongestStreak();
        editor.putInt("currStreak", _currStreak);
        editor.putInt("maxStreak", _maxStreak);
        editor.commit();
    }

    public void onDayClick(EventDay eventDay) {
                Log.i("DailyLogFragment", "Event Day clicked is: " + eventDay);
                Log.i("DailyLogFragment", "Before clicked _daysRead has: " + _daysRead.size());
                Log.i("DailyLogFragment", "Before clicked currCal is: " + currDate);
        currCal = eventDay.getCalendar();
        currDate = currCal.getTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        _readingSwitch.setChecked(_savedDates.contains(currDate));
                Log.i("DailyLogFragment", "After click event Day clicked is: " + eventDay);
                Log.i("DailyLogFragment", "After clicked _daysRead has: " + _daysRead.size());
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

        //Sort array and find currentStreak
        sortDaysArray();
        findCurrentStreak();
    }

    public void setCurrentGoal(View root){
        //Get shared preferences of the goal and display
        SharedPreferences goalPreferences = this.getActivity().getSharedPreferences("goal", Context.MODE_PRIVATE);
        String getGoal = goalPreferences.getString("goal", null);

        Gson gsonGoal = new Gson();
        _currentGoal = root.findViewById(R.id.currentGoal2);
        Goal goal = gsonGoal.fromJson(getGoal, Goal.class);

        if(goal.getNumDays() != null && goal != null){
            String showGoal = "Your Current Goal: " + goal.getNumDays() + " days a week from " +
                    goal.getStartGoal() + " to " + goal.getEndGoal();
            _currentGoal.setText(showGoal);
        }else {
            _currentGoal.setText("No Current Goal Set\n Please set up your goal");
        }
    }

    public void sortDaysArray() {
        //Log.i("DailyLogFragment", "_savedDates before sort = " + _savedDates);
        Collections.sort(_savedDates, Collections.reverseOrder());
        //Log.i("DailyLogFragment", "_savedDates after sort = " + _savedDates);
    }

    public void findCurrentStreak() {
        int streak = 1;
        Log.i("DailyLogFragment", "Today = " + _today);
        Log.i("DailyLogFragment", "_savedDates0 = " + _savedDates.get(0));
        if (_savedDates.get(0).compareTo(_today) == 0 || _savedDates.get(0).compareTo(_today.minusDays(1)) == 0) {
            for (int i = 0; i < _savedDates.size() - 1; i++) {
                Log.i("DailyLogFragment", "_savedDates" + i + " = " + _savedDates.get(i));
                LocalDate prevDay = _savedDates.get(i).minusDays(1);
                Log.i("DailyLogFragment", "Calculated prevDay = " + prevDay);
                Log.i("DailyLogFragment", "Calculated _savedDates i+1 = " + _savedDates.get(i + 1));
                if (prevDay.compareTo(_savedDates.get(i + 1)) == 0) {
                    streak++;
                } else {
                    break;
                }
            }
            Log.i("DailyLogFragment", "currStreak = " + streak);
            _currStreak = streak;
            if (_currStreak > _maxStreak){
                _maxStreak = _currStreak;
            }
        } else {
            _currStreak = 0;
            Log.i("DailyLogFragment", "No streak");
        }
    }

    public void checkLongestStreak(){
        Log.i("DailyLogFragment", "Max Streak Before check = " + _maxStreak);
        int max = _maxStreak;
        int streak = 1;
        for (int i = 0; i < _savedDates.size() - 1; i++) {
            LocalDate prevDay = _savedDates.get(i).minusDays(1);
            LocalDate nextDay = _savedDates.get(i+1);
            Log.i("DailyLogFragment", "prevDay = " + prevDay);
            Log.i("DailyLogFragment", "nextDay = " + nextDay);
            if (prevDay.compareTo(nextDay) == 0){
                streak++;
            } else {
                if (max < streak){
                    max = streak;
                }
                streak = 1;
            }
            Log.i("DailyLogFragment", "Looping Streak for max = " + streak);
        }
        if (max < streak){
            max = streak;
        }
        _maxStreak = max;
        Log.i("DailyLogFragment", "Max Streak after check = " + _maxStreak);
    }

}