package com.example.mustardseed;

import android.view.View;

public class Goal {
    private String numDays;
    private String startGoal;
    private String endGoal;
    private boolean isComplete;
    private int goalID;
//    private int currentGoal = 0;

    public Goal(String numDays, String startGoal, String endGoal) {
        this.numDays = numDays;
        this.startGoal = startGoal;
        this.endGoal = endGoal;
        this.isComplete = false;
        this.goalID = this.getNewGoalID();
    }

    public String getNumDays() {
        return numDays;
    }

    public void setNumDays(String numDays) {
        this.numDays = numDays;
    }

    public String getStartGoal() {
        return startGoal;
    }

    public void setStartGoal(String startGoal) {
        this.startGoal = startGoal;
    }

    public String getEndGoal() {
        return endGoal;
    }

    public void setEndGoal(String endGoal) {
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

    public void endGoal(View view){
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


