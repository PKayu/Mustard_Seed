package com.example.mustardseed;

import android.content.SharedPreferences;

public class User {
    private String name;
    private String favScripture;

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

    public User(String name, String favScripture) {
        this.name = name;
        this.favScripture = favScripture;
    }

}
