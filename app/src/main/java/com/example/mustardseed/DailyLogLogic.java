package com.example.mustardseed;

import android.util.Log;

import com.applandeo.materialcalendarview.EventDay;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class DailyLogLogic {


    //Checks day and updates data in CalendarView to match day.
    public static boolean checkDay(EventDay eventDay, List loggedDays, List daysRead){
        Log.i("DailyLogLogic", "This is the event day: " + "eventDay");
        if (loggedDays.contains(eventDay)) {
            System.out.println(eventDay);
        }
        if (daysRead.contains(eventDay.getCalendar())){
            return true;
        } else {
            return false;
        }
    }

    //Creates dailyLog object for date if none exists.
    public DailyLog createLog(Date calDate){
        return new DailyLog(calDate, false);
    }

    //todo
    //Updates a dailyLog object when when switch is hit
    public static void updateLog(EventDay eventDay, List dailyLogs) {
        if(dailyLogs.contains(eventDay)){
            dailyLogs.remove(eventDay);
        } else {
            dailyLogs.add(eventDay);
        }
    }

//    //todo
//    //link to changeGoal screen... prob doesn't need method
//    changeGoal(){
//
//    }

    //Updates list/map of dailyLogs with new or updated dailyLog.
//    updateLogMap(){
//
//    }
//
    //todo figure out where this is needed
//    EventDay l = new EventDay(_CalendarView.getCurrentPageDate());
//    Calendar k = l.getCalendar();
//    List cals = new ArrayList<>();
//        cals.add(k);
//        _CalendarView.setHighlightedDays(cals);

}

