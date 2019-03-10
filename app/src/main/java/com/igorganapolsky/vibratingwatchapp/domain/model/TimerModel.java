package com.igorganapolsky.vibratingwatchapp.domain.model;

import java.util.Locale;

public class TimerModel {

    private int id;
    private int buzz;
    private int repeat;

    private int hoursTotal;
    private int minutesTotal;
    private int secondsTotal;

    private int hoursLeft;
    private int minutesLeft;
    private int secondsLeft;


    public TimerModel() {
        this(0, 0, 0);
    }

    public TimerModel(int hoursTotal, int minutesTotal, int secondsTotal) {
        this.hoursTotal = hoursTotal;
        this.minutesTotal = minutesTotal;
        this.secondsTotal = secondsTotal;
    }

    public int getHoursTotal() {
        return hoursTotal;
    }

    public void setHoursTotal(int hoursTotal) {
        this.hoursTotal = hoursTotal;
    }

    public int getMinutesTotal() {
        return minutesTotal;
    }

    public void setMinutesTotal(int minutesTotal) {
        this.minutesTotal = minutesTotal;
    }

    public int getSecondsTotal() {
        return secondsTotal;
    }

    public void setSecondsTotal(int secondsTotal) {
        this.secondsTotal = secondsTotal;
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

    public int getHoursLeft() {
        return hoursLeft;
    }

    public void setHoursLeft(int hoursLeft) {
        this.hoursLeft = hoursLeft;
    }

    public int getMinutesLeft() {
        return minutesLeft;
    }

    public void setMinutesLeft(int minutesLeft) {
        this.minutesLeft = minutesLeft;
    }

    public int getSecondsLeft() {
        return secondsLeft;
    }

    public void setSecondsLeft(int secondsLeft) {
        this.secondsLeft = secondsLeft;
    }

    public int getValue(TimerSetup timerSetup) {
        switch (timerSetup) {
            case HOURS:
                return getHoursTotal();
            case MINUTES:
                return getMinutesTotal();
            case SECONDS:
                return getSecondsTotal();
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
        return hoursTotal == 0 && minutesTotal == 0 && secondsTotal == 0;
    }

    @Override
    public String toString() {
        return String.format(Locale.ENGLISH, "%02d : %02d : %02d", hoursTotal, minutesTotal, secondsTotal);
    }
}
