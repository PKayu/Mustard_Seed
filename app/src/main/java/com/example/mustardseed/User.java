package com.example.mustardseed;

public class User {
    private String name;
    private Scripture favScripture;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Scripture getFavScripture() {
        return favScripture;
    }

    public void setFavScripture(Scripture favScripture) {
        this.favScripture = favScripture;
    }

    public User(String name, Scripture favScripture) {
        this.name = name;
        this.favScripture = favScripture;
    }
}
