package com.example.mustardseed;

import android.app.Dialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;


import com.example.mustardseed.R;

import java.util.Calendar;

public class EndDatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        int iMonth = month + 1;
        Log.d("DatePickerFragment", "onDateSet: date: " +  iMonth + "/" + dayOfMonth + "/" + year);

        String date = iMonth + "/" + dayOfMonth + "/" + year;
        EditText mDisplayDate = this.getActivity().findViewById(R.id.endDate);

        mDisplayDate.setText(date);
    }
}
