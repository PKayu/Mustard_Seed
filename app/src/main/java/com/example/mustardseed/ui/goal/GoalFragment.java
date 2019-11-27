package com.example.mustardseed.ui.goal;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.nfc.Tag;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.mustardseed.Goal;
import com.example.mustardseed.Notification;
import com.example.mustardseed.StartDatePickerFragment;
import com.example.mustardseed.EndDatePickerFragment;
import com.example.mustardseed.MainActivity;
import com.example.mustardseed.R;
import com.example.mustardseed.User;
import com.google.gson.Gson;

import org.w3c.dom.Text;

import java.time.Month;
import java.util.Calendar;

/**
 * @author Ashley and Dan and Jacob
 * This class will create the goal, call to date picker and save the info to the user preferences.
 */
public class GoalFragment extends Fragment {

    private static final String TAG = "GoalFragment";
    private EditText mStartDisplayDate;
    private EditText mEndDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private Button _setGoal;
    private EditText _startDate;
    private EditText _endDate;
    private EditText _NumDaysWeek;
    private Button _completedGoal;


    private GoalViewModel goalViewModel;

    /**
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        goalViewModel =
                ViewModelProviders.of(this).get(GoalViewModel.class);
        View root = inflater.inflate(R.layout.fragment_goal, container, false);

        //allows for the Date selector
        mStartDisplayDate = root.findViewById(R.id.startDate);
        mEndDisplayDate = root.findViewById(R.id.endDate);

        mStartDisplayDate.setOnClickListener(this::showStartDatePickerDialog);
        mEndDisplayDate.setOnClickListener(this::showEndDatePickerDialog);

        //Get UI for manipulation
        _setGoal = root.findViewById(R.id.setGoal);
        _startDate = root.findViewById(R.id.startDate);
        _endDate = root.findViewById(R.id.endDate);
        _NumDaysWeek = root.findViewById(R.id.numDays);

        //only allow 1-7 in the num days section
        _NumDaysWeek.setFilters(new InputFilter[] { new InputFilterMinMax("1", "7")});

        // setup save btn click listener
        Log.i(TAG, "Create listener");
        _setGoal.setOnClickListener(this::onSaveClick);

        SharedPreferences preferences = this.getActivity().getSharedPreferences("goal", Context.MODE_PRIVATE);
        String gGoal = preferences.getString("goal", null);

        Gson gson = new Gson();
        Goal goal = gson.fromJson(gGoal,Goal.class);

        if(goal != null) {
            Log.i(TAG, "Load stored preferences");
            _startDate.setText(goal.getStartGoal());
            _endDate.setText(goal.getEndGoal());
            _NumDaysWeek.setText(goal.getNumDays());
            Log.i(TAG, "Finish loading stored preferences");
        }

        // setup save btn click listener
        Log.i(TAG, "Create listener");
      //  _completedGoal.setOnClickListener(this::completeGoal);

        return root;
    }

    /**
     *
     * @param v
     */
    public void showStartDatePickerDialog(View v) {
        DialogFragment newFragment = new StartDatePickerFragment();

        newFragment.show(this.getActivity().getSupportFragmentManager(), "startPicker");
    }

    public void showEndDatePickerDialog(View v) {
        DialogFragment newFragment = new EndDatePickerFragment();
        newFragment.show(this.getActivity().getSupportFragmentManager(), "endPicker");
    }

    public void onSaveClick(View view){
        Log.i("GoalFragment", "Save clicked");

        String sNumDays = _NumDaysWeek.getText().toString();
        String sStartDate = _startDate.getText().toString();
        String sEndDate = _endDate.getText().toString();

        Goal goal = new Goal(sNumDays, sStartDate, sEndDate);
        Gson gson = new Gson();

        String gGoal = gson.toJson(goal);

        SharedPreferences sharedPref = view.getContext().getSharedPreferences("goal", Context.MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = sharedPref.edit();
        prefEditor.putString("goal", gGoal);
        prefEditor.commit();
        Toast.makeText(view.getContext().getApplicationContext(),"Your goal has been saved successfully", Toast.LENGTH_LONG).show();
    }

    /**
     * This will delete the prior goal and set it back to null
     * @param v
     */
//    public void completeGoal(View v){
//        Log.i("GoalFragment", "Force Complete clicked");
//        SharedPreferences preferences = this.getActivity().getSharedPreferences("goal", Context.MODE_PRIVATE);
//        String gGoal = preferences.getString("goal", null);
//
//        Gson gson = new Gson();
//        Goal goal = gson.fromJson(gGoal,Goal.class);
//
//        int startingLength = Goal.length();
//    }

};