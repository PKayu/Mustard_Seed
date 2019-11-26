package com.example.mustardseed;

public class Notification {


    private int hour;
    private int minute;
    private String message;

    public int getHour() { return hour; }

    public void setHour(int hour) { this.hour = hour; }

    public int getMinute() { return minute; }

    public void setMinute(int minute) { this.minute = minute; }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Notification(int hour, int minute) {
        this.hour = hour;
        this.minute = minute;
    }
}
