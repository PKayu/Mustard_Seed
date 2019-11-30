package com.example.mustardseed.ui.home;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.mustardseed.R;
import com.example.mustardseed.User;
import com.example.mustardseed.Goal;
import com.google.gson.Gson;

public class HomeFragment extends Fragment {

    private TextView _userComment;
    private HomeViewModel homeViewModel;
    private TextView _currentGoal;

    String _startDate;
    String _endDate;
    String _numDays;

    private static final String TAG = "Received intent with ";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        _userComment = root.findViewById(R.id.userComment);

        // Get shared preferences of the user and display
        SharedPreferences preferences = this.getActivity().getSharedPreferences("prefs", Context.MODE_PRIVATE);
        //preferences.edit().clear().commit();

        String gUser = preferences.getString("user", null);

        Gson gson = new Gson();
        if(gUser != null){
            User user = gson.fromJson(gUser,User.class);

            String sGreeting = "Welcome " + user.getName();
            _userComment.setText(sGreeting);
        } else {
            _userComment.setText("Welcome user\n Please setup your user profile!");
        }

        //Get shared preferences of the goal and display
        SharedPreferences goalPreferences = this.getActivity().getSharedPreferences("goal", Context.MODE_PRIVATE);
        String getGoal = goalPreferences.getString("goal", null);

        Gson gsonGoal = new Gson();
        _currentGoal = root.findViewById(R.id.currentGoal);
        Goal goal = gsonGoal.fromJson(getGoal, Goal.class);

        if(getGoal != null && goal != null){
            String _currentNumGoal = goal.getNumDays();
            String showGoal = "Your Current Goal: " + goal.getNumDays() + " days a week from " +
                    goal.getStartGoal() + " to " + goal.getEndGoal();
            _currentGoal.setText(showGoal);
        }else {
            _currentGoal.setText("No Current Goal Set\n Please set up your goal");
        }

        return root;
    }
}