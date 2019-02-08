package com.igorganapolsky.vibratingwatchapp.model;

public class TimerModel {

    private int hours;
    private int minutes;
    private int seconds;
    private int buzzMode;
    private int repeatMode;

    public TimerModel(int hours, int minutes, int seconds, int buzzMode, int repeatMode) {
        this.hours = hours;
        this.minutes = minutes;
        this.seconds = seconds;
        this.buzzMode = buzzMode;
        this.repeatMode = repeatMode;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public int getSeconds() {
        return seconds;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }

    public int getBuzzMode() {
        return buzzMode;
    }

    public void setBuzzMode(int buzzMode) {
        this.buzzMode = buzzMode;
    }

    public int getRepeatMode() {
        return repeatMode;
    }

    public void setRepeatMode(int repeatMode) {
        this.repeatMode = repeatMode;
    }
}
