package com.igorganapolsky.vibratingwatchapp.domain.model;

public class CountData {

   public enum State {PREPARED, TICK, FINISH}

    private int currentProgress;
    private String currentTime;
    private State state;

    public CountData(String currentTime, int currentProgress, State state) {
        this.currentTime = currentTime;
        this.currentProgress = currentProgress;
        this.state = state;
    }

    public int getCurrentProgress() {
        return currentProgress;
    }

    public String getCurrentTime() {
        return currentTime;
    }

    public State getState() {
        return state;
    }
}
