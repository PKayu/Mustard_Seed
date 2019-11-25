package com.example.mustardseed.ui.profile;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.mustardseed.R;
import com.example.mustardseed.User;
import com.google.gson.Gson;


public class ProfileFragment extends Fragment {

    private Button _btnSave;
    private EditText _etFullName;
    private  EditText _etFavScripture;
    private EditText _etNotification;
    private Switch _notifEnabled;

    private static final String TAG = "ProfileFreagment";
    private ProfileViewModel profileViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        Log.e(TAG, "profileFragment was initialized");
        profileViewModel =
                ViewModelProviders.of(this).get(ProfileViewModel.class);
        View root = inflater.inflate(R.layout.fragment_profile, container, false);

        //Get UI for manipulation
        _btnSave = root.findViewById(R.id.btnSave);
        _etFullName = root.findViewById(R.id.etFullName);
        _etFavScripture = root.findViewById(R.id.etFavScripture);
        _etNotification = root.findViewById(R.id.etNotifTime);
        _notifEnabled = root.findViewById(R.id.switchEnabled);

        // setup save btn click listener
        Log.i(TAG, "Create listener");
        _btnSave.setOnClickListener(this::onSaveClick);

        SharedPreferences preferences = this.getActivity().getSharedPreferences("prefs", Context.MODE_PRIVATE);
        String gUser = preferences.getString("user", null);

        Gson gson = new Gson();
        User user = gson.fromJson(gUser,User.class);

        Log.i(TAG, "Load stored preferences");
        _etFullName.setText(user.getName());
        _etFavScripture.setText(user.getFavScripture());
        Log.i(TAG, "Finish loading stored preferences");

        return root;
    }

    public void onSaveClick(View view){
        Log.i("ProfileFragment", "Save clicked");
        Toast.makeText(view.getContext().getApplicationContext(),"Button Clicked!", Toast.LENGTH_LONG).show();

        String sFullName = _etFullName.getText().toString();
        String sFavScripture = _etFavScripture.getText().toString();

        User user = new User(sFullName, sFavScripture);
        Gson gson = new Gson();

        String gUser = gson.toJson(user);

        SharedPreferences sharedPref = view.getContext().getSharedPreferences("prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = sharedPref.edit();
        prefEditor.putString("user", gUser);
        prefEditor.commit();
        Toast.makeText(view.getContext().getApplicationContext(),"Your profile has been saved successfully", Toast.LENGTH_LONG).show();
    }
}