package com.example.mustardseed;

import android.view.View;

public class Goal {
    private int numDays;
    private long startGoal;
    private long endGoal;
    private boolean isComplete;
    private int goalID;
//    private int currentGoal = 0;

    public Goal(int numDays, long startGoal, long endGoal) {
        this.numDays = numDays;
        this.startGoal = startGoal;
        this.endGoal = endGoal;
        this.isComplete = false;
        this.goalID = this.getNewGoalID();
    }

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

    public void setComplete(boolean complete) {
        isComplete = complete;
    }

    public int getGoalID() {
        return goalID;
    }

    public void setGoalID(int goalID) {
        this.goalID = goalID;
    }
//
//    public int getCurrentGoal() {
//        return currentGoal;
//    }
//
//    public void setCurrentGoal(int currentGoal) {
//        this.currentGoal = currentGoal;
//    }

    // Functions

    public boolean isComplete() {
        return isComplete;
    }

    public void createGoal(View view){

        Goal goal = new Goal(getNumDays(),getStartGoal(), getEndGoal());
        saveGoaltoJson();

    }

    public void endGoal(){
        this.isComplete = true;
    }

    public boolean saveGoaltoJson () {
        //this is the function that will save this goal json into the json file.
        return true;
    }

    private int getNewGoalID(){
        //this function will get the last goal Id to add 1 to it
        return 1;
    }
}


