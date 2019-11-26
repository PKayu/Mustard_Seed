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
import com.google.gson.Gson;

public class HomeFragment extends Fragment {

    private TextView _userComment;
    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        _userComment = root.findViewById(R.id.userComment);


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

        return root;
    }
}