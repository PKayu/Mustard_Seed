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
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.mustardseed.R;

import org.w3c.dom.Text;

import java.time.Month;
import java.util.Calendar;

public class GoalFragment extends Fragment {

    private static final String TAG = "GoalFragment";
    private TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;


    private GoalViewModel goalViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        goalViewModel =
                ViewModelProviders.of(this).get(GoalViewModel.class);
        View root = inflater.inflate(R.layout.fragment_goal, container, false);

//        //allows for the Date selector ( Will come back and work on this later)
//        mDisplayDate = (TextView) getView().findViewById(R.id.startDate);
//
//        mDisplayDate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Calendar cal = Calendar.getInstance();
//                int year = cal.get(Calendar.YEAR);
//                int month = cal.get(Calendar.MONTH);
//                int day = cal.get(Calendar.DAY_OF_MONTH);
//
//                DialogFragment dialog = new SelectDateFragment(,
//                        android.R.style.Theme_DeviceDefault_DayNight,
//                        mDateSetListener,
//                        year,month,day);)
//                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//                dialog.show();
//            }
//        });
//
//        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
//            @Override
//            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//                month = month + 1;
//                Log.d(TAG, "onDateSet: date: " +  month + "/" + dayOfMonth + "/" + year);
//
//                String date = month + "/" + dayOfMonth + "/" + year;
//                mDisplayDate.setText(date);
//            }
//        };


        return root;
    }
}