package com.example.mustardseed.ui.dailyLog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.mustardseed.R;

public class DailyLogFragment extends Fragment {

    private DailyLogViewModel dailyLogViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dailyLogViewModel =
                ViewModelProviders.of(this).get(DailyLogViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dailylog, container, false);

        return root;
    }
}