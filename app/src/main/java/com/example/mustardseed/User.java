package com.example.mustardseed;

import android.content.SharedPreferences;

public class User {
    private String name;
    private String favScripture;
    private boolean NotificationEnabled;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFavScripture() {
        return favScripture;
    }

    public void setFavScripture(String favScripture) {
        this.favScripture = favScripture;
    }

    public boolean isNotificationEnabled() { return NotificationEnabled; }

    public void setNotificationEnabled(boolean notificationEnabled) { NotificationEnabled = notificationEnabled; }

    public User(String name, String favScripture, boolean notificationEnabled) {
        this.name = name;
        this.favScripture = favScripture;
        this.NotificationEnabled = notificationEnabled;
    }

}
