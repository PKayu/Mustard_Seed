package com.example.mustardseed;

import java.util.Date;

public class DailyLog {

    private Date _logDate;
    private boolean _isCompleted;
    private Scripture _lastRead;

    public DailyLog(Date date, boolean isComplete){
        this._logDate = date;
        this._isCompleted = isComplete;
    }

    public Date getLogDate() {
        return _logDate;
    }

    public void setLogDate(Date date) {
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
