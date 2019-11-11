package com.example.mustardseed;

public class Goal {
    private int numDays;
    private long startGoal;
    private long endGoal;
    private boolean isComplete;
    private int goalID;

    public int getNumDays() {
        return numDays;
    }

    public void setNumDays(int numDays) {
        this.numDays = numDays;
    }

    public long getStartGoal() {
        return startGoal;
    }

    public void setStartGoal(long startGoal) {
        this.startGoal = startGoal;
    }

    public long getEndGoal() {
        return endGoal;
    }

    public void setEndGoal(long endGoal) {
        this.endGoal = endGoal;
    }

    public boolean isComplete() {
        return isComplete;
    }

    public void setComplete(boolean complete) {
        isComplete = complete;
    }

    public int getGoalID() {
        return goalID;
    }

    public void setGoalID(int goalID) {
        this.goalID = goalID;
    }

    // Functions

    public Goal(int numDays, long startGoal, long endGoal) {
        this.numDays = numDays;
        this.startGoal = startGoal;
        this.endGoal = endGoal;
        this.isComplete = false;
    }

    public void endGoal(){
        this.isComplete = true;
    }
}
