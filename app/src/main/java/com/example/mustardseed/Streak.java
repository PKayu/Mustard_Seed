package com.example.mustardseed;

public class Streak {
    private int currentSteak;
    private int bestSteak;

    public int getCurrentSteak() {
        return currentSteak;
    }

    public void setCurrentSteak(int currentSteak) {
        this.currentSteak = currentSteak;
    }

    public int getBestSteak() {
        return bestSteak;
    }

    public void setBestSteak(int bestSteak) {
        this.bestSteak = bestSteak;
    }

    public void updateStreak(int addToSteak){
        this.currentSteak += addToSteak;
    }

    public void checkStreak(){

    }

    public boolean compareStreak(){
        return true;
    }

    public void killSteak(){

    }
}
