package com.example.mustardseed;

public class DailyLog {

    private long _logDate;
    private boolean _isCompleted;
    private Scripture _lastRead;

    public DailyLog(long date, boolean isComplete){
        this._logDate = date;
        this._isCompleted = isComplete;
    }

    public long getLogDate() {
        return _logDate;
    }

    public void setLogDate(long date) {
        this._logDate = date;
    }

    public boolean getIsCompleted() {
        return _isCompleted;
    }

    public void setIsCompleted(boolean isCompleted) {
        this._isCompleted = isCompleted;
    }

    public Scripture getLastRead() {
        return _lastRead;
    }

    public void setLastRead(Scripture lastRead) {
        this._lastRead = lastRead;
    }

}
