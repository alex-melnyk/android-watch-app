package com.igorganapolsky.vibratingwatchapp.domain.model;

import java.util.Locale;

public class TimerValue {

    private TimeHighlightState state;

    private int id;
    private int hours;
    private int minutes;
    private int seconds;
    private int buzz;
    private int repeat;

    public TimerValue() {
        this(0, 0, 0);
    }

    public TimerValue(int hours, int minutes, int seconds) {
        this(hours, minutes, seconds, TimeHighlightState.WHOLE);
    }

    public TimerValue(int hours, int minutes, int seconds, TimeHighlightState state) {
        this.hours = hours;
        this.minutes = minutes;
        this.seconds = seconds;
        this.state = state;
    }

    public TimeHighlightState getState() {
        return state;
    }

    public void setState(TimeHighlightState state) {
        this.state = state;
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

    public int getBuzz() {
        return buzz;
    }

    public void setBuzz(int buzz) {
        this.buzz = buzz;
    }

    public int getRepeat() {
        return repeat;
    }

    public void setRepeat(int repeat) {
        this.repeat = repeat;
    }

    public int getValue(TimerSetup timerSetup) {
        switch (timerSetup) {
            case HOURS:
                return getHours();
            case MINUTES:
                return getMinutes();
            case SECONDS:
                return getSeconds();
        }

        return -1;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isDefaultTime() {
        return hours == 0 && minutes == 0 && seconds == 0;
    }

    @Override
    public String toString() {
        return String.format(Locale.ENGLISH, "%02d : %02d : %02d", hours, minutes, seconds);
    }
}
