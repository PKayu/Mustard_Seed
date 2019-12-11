package com.example.mustardseed.ui.dailyLog;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.example.mustardseed.Goal;
import com.example.mustardseed.R;
import com.example.mustardseed.ui.goal.GoalViewModel;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private Button _saveButton;
    private GoalViewModel _goalViewModel;
    private Map<String, String> _dailyNotes = new HashMap<>();
    private TextInputEditText _currentNote;
    private String currNote;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dailyLogViewModel =
                ViewModelProviders.of(this).get(DailyLogViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dailylog, container, false);

        // Get views for manipulation
        _CalendarView = (CalendarView) root.findViewById(R.id.calendarView);
        _readingSwitch = (Switch) root.findViewById(R.id.readingSwitch);
        _saveButton = (Button) root.findViewById(R.id.saveButton);
        _currentNote = (TextInputEditText) root.findViewById(R.id.noteText);

        //Load sharedPref
//        SharedPreferences preferences2 = this.getActivity().getSharedPreferences("prefs", Context.MODE_PRIVATE);
//        preferences2.edit().clear().commit();
        SharedPreferences preferences = this.getActivity().getSharedPreferences("prefs", Context.MODE_PRIVATE);
        String gDateString = preferences.getString("dailyLogDateArray", null);
        _maxStreak = preferences.getInt("maxStreak", 0);
        _currStreak = preferences.getInt("currStreak", 0);
        String gDailyNotes = preferences.getString("dailyNotesMap", null);

        Gson gson = new Gson();
        String[] dateStrings = gson.fromJson(gDateString, String[].class);
        //gets exact type for hashmap
        java.lang.reflect.Type type = new TypeToken<HashMap<String, String>>(){}.getType();
        Log.i("DailyLogFragment", "Daily Notes Gson String = " + gDailyNotes);
        if (gDailyNotes != null) {
            _dailyNotes = gson.fromJson(gDailyNotes, type);
        }
        _today = LocalDate.now();
        currDate = _today;

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
            _currentNote.setText(_dailyNotes.get(_today.toString()));
            currNote = _dailyNotes.get(_today.toString());

            //Get shared preferences of the goal and display
            setCurrentGoal(root);
        }
        // setup Calendar click listener
        _CalendarView.setOnDayClickListener(this::onDayClick);

        // setup toggle for listener
        _readingSwitch.setOnCheckedChangeListener(this::onCheckedChanged);

        //Setup button listener
        _saveButton.setOnClickListener(saveButtonListener);

        return root;
    }

    public void onPause() {
        super.onPause();
        saveData();
    }

    public void onDayClick(EventDay eventDay) {
        Log.i("DailyLogFragment", "Event Day clicked is: " + eventDay);
        Log.i("DailyLogFragment", "Before clicked _daysRead has: " + _daysRead.size());
        Log.i("DailyLogFragment", "Before clicked currCal is: " + currDate);
        Log.i("DailyLogFragment", "Before clicked curent Note is: " + currNote);

        //Save note to map before getting new data
        _dailyNotes.put(currDate.toString(), _currentNote.getText().toString());

        //update current data
        currCal = eventDay.getCalendar();
        currDate = currCal.getTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        _readingSwitch.setChecked(_savedDates.contains(currDate));
        _currentNote.setText(_dailyNotes.get(currDate.toString()));
        currNote = _dailyNotes.get(currDate.toString());

        Log.i("DailyLogFragment", "After click event Day clicked is: " + eventDay);
        Log.i("DailyLogFragment", "After clicked _daysRead has: " + _daysRead.size());
        Log.i("DailyLogFragment", "After clicked currDay is: " + currDate);
        Log.i("DailyLogFragment", "_daysRead = " + _daysRead);
        Log.i("DailyLogFragment", "_savedDates = " + _savedDates);
        Log.i("DailyLogFragment", "after current Note is: " + currNote);
    }

    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        Calendar selectedCal = currCal;
        LocalDate selectedDate = currDate;
        System.out.println(currDate);
        if (isChecked) {
            if (currDate.compareTo(_today) <= 0) {
                if (_savedDates.contains(currDate)) {

                } else {
                    _daysRead.add(selectedCal);
                    _savedDates.add(selectedDate);
                }
            } else {
                buttonView.setChecked(false);
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
        saveData();
    }

    public void setCurrentGoal(View root){
        //Get shared preferences of the goal and display
        SharedPreferences goalPreferences = this.getActivity().getSharedPreferences("goal", Context.MODE_PRIVATE);
        String getGoal = goalPreferences.getString("goal", null);

        Gson gsonGoal = new Gson();
        _currentGoal = root.findViewById(R.id.currentGoal2);
        Goal goal = gsonGoal.fromJson(getGoal, Goal.class);

        if(goal != null) {
            if (goal.getNumDays() != null) {
                String showGoal = "Your Current Goal: " + goal.getNumDays() + " days a week from " +
                        goal.getStartGoal() + " to " + goal.getEndGoal();
                _currentGoal.setText(showGoal);
            }
        } else {
            _currentGoal.setText("No Current Goal Set\n Please set up your goal");
        }
    }

    public void sortDaysArray() {
        //Log.i("DailyLogFragment", "_savedDates before sort = " + _savedDates);
        if (_savedDates.size() > 0) {
            Collections.sort(_savedDates, Collections.reverseOrder());
        }
        //Log.i("DailyLogFragment", "_savedDates after sort = " + _savedDates);
    }

    public void findCurrentStreak() {
        int streak = 1;
        if (_savedDates.size() > 0) {
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
                if (_currStreak > _maxStreak) {
                    _maxStreak = _currStreak;
                }
            } else {
                _currStreak = 0;
                Log.i("DailyLogFragment", "No streak");
            }
        }
        else {
            _currStreak = 0;
        }
    }

    public void checkLongestStreak(){
        Log.i("DailyLogFragment", "Max Streak Before check = " + _maxStreak);
        int max = _maxStreak;
        int streak = 1;
        if (_savedDates.size() > 1) {
            for (int i = 0; i < _savedDates.size() - 1; i++) {
                LocalDate prevDay = _savedDates.get(i).minusDays(1);
                LocalDate nextDay = _savedDates.get(i + 1);
                Log.i("DailyLogFragment", "prevDay = " + prevDay);
                Log.i("DailyLogFragment", "nextDay = " + nextDay);
                if (prevDay.compareTo(nextDay) == 0) {
                    streak++;
                } else {
                    if (max < streak) {
                        max = streak;
                    }
                    streak = 1;
                }
                Log.i("DailyLogFragment", "Looping Streak for max = " + streak);
            }
        }
        if (max < streak){
            max = streak;
        }
        _maxStreak = max;
        Log.i("DailyLogFragment", "Max Streak after check = " + _maxStreak);
    }

    private View.OnClickListener saveButtonListener = new View.OnClickListener() {
        public void onClick(View v) {
            saveData();
        }
    };

    public void saveData(){
        _currentNote.clearFocus();
        if (currNote != null) {
            if (!currNote.equals("")) {
                _dailyNotes.put(currDate.toString(), currNote);
            }
        }
        SharedPreferences.Editor editor = this.getActivity().getSharedPreferences("prefs", Context.MODE_PRIVATE).edit();
        Gson gson = new Gson();

        //Change each LocalDate in the savedDates to a String[] then serialize and save
        String[] dateStrings = new String[_savedDates.size()];
        for (int i = 0; i< _savedDates.size(); i++){
            dateStrings[i] = _savedDates.get(i).toString();
        }
        String gNoteString = gson.toJson(_dailyNotes);
        Log.i("DailyLogFragment", "_dailyNotes map =" + gNoteString);
        String gDateString = gson.toJson(dateStrings);
        //Log.i("DailyLogFragment", "String[] dateStrings in JSON =" + gDateString);
        editor.putString("dailyLogDateArray", gDateString);
        editor.putString("dailyNotesMap", gNoteString);

        // Save streak data
        checkLongestStreak();
        editor.putInt("currStreak", _currStreak);
        editor.putInt("maxStreak", _maxStreak);
        editor.commit();
    }

}