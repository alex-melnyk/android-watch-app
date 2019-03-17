package com.igorganapolsky.vibratingwatchapp.domain.model;

public class CountData {

    private static final String DEFAULT_TIME = "00:00:00";

    private int currentProgress;
    private String currentTime;
    private boolean isAnimationNeeded;

    private CountData(String currentTime, int currentProgress, boolean isAnimationNeeded) {
        this.currentTime = currentTime;
        this.currentProgress = currentProgress;
        this.isAnimationNeeded = isAnimationNeeded;
    }

    public static CountData getDefault() {
        return new CountData(DEFAULT_TIME, 0, false);
    }

    public int getCurrentProgress() {
        return currentProgress;
    }

    public String getCurrentTime() {
        return currentTime;
    }

    public boolean isAnimationNeeded() {
        return isAnimationNeeded;
    }

    public void setCurrentProgress(int currentProgress) {
        this.currentProgress = currentProgress;
    }

    public void setCurrentTime(String currentTime) {
        this.currentTime = currentTime;
    }

    public void setAnimationNeeded(boolean animationNeeded) {
        isAnimationNeeded = animationNeeded;
    }
}
