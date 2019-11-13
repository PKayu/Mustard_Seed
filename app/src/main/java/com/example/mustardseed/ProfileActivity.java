package com.example.mustardseed;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        loadUser();
    }

    String _fullName;

    //Saves data to sharedPreferences
    private void saveUser(){
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("savedUser",0);
        SharedPreferences.Editor editor = sharedPref.edit();
        EditText uName = findViewById(R.id.etFullName);
        String sName = uName.toString();
        editor.putString("FullName" ,sName);
        editor.commit();
    }

    private void loadUser() {
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("savedUser",0);
        _fullName = sharedPref.getString("FullName", "");
        EditText fullName = (EditText) findViewById(R.id.etFullName);
        fullName.setText(_fullName);
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.saveUser();
    }
}
