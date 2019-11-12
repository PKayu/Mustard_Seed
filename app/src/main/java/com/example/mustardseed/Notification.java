package com.example.mustardseed;

public class Notification {
    private long notfiTime;
    private String message;
    private boolean isEnabled;

    public long getNotfiTime() {
        return notfiTime;
    }

    public void setNotfiTime(long notfiTime) {
        this.notfiTime = notfiTime;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }
}
