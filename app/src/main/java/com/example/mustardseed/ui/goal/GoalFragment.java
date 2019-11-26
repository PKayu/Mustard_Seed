package com.example.mustardseed.ui.goal;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.mustardseed.DatePickerFragment;
import com.example.mustardseed.MainActivity;
import com.example.mustardseed.R;

import org.w3c.dom.Text;

import java.time.Month;
import java.util.Calendar;

public class GoalFragment extends Fragment {

    private static final String TAG = "GoalFragment";
    private EditText mStartDisplayDate;
    private EditText mEndDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;


    private GoalViewModel goalViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        goalViewModel =
                ViewModelProviders.of(this).get(GoalViewModel.class);
        View root = inflater.inflate(R.layout.fragment_goal, container, false);

        //allows for the Date selector ( Will come back and work on this later)
        mStartDisplayDate = root.findViewById(R.id.startDate);
        mEndDisplayDate = root.findViewById(R.id.endDate);

        mStartDisplayDate.setOnClickListener(this::showStartDatePickerDialog);
        mEndDisplayDate.setOnClickListener(this::showEndDatePickerDialog);

        return root;
    }

    public void showStartDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();

        newFragment.show(this.getActivity().getSupportFragmentManager(), "startPicker");
    }

    public void showEndDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(this.getActivity().getSupportFragmentManager(), "endPicker");
    }
}