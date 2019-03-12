package com.igorganapolsky.vibratingwatchapp.util;

import com.igorganapolsky.vibratingwatchapp.ui.models.TimerValue;

import java.util.Locale;

public class TimerTransform {

    public static long timeToMillis(int hours, int minutes, int seconds) {
        return (seconds * 1_000) + (minutes * 60_000) + (hours * 3_600_000);
    }

    public static String millisToTimeString(long milliseconds) {
        int seconds = (int) (milliseconds / 1_000) % 60;
        int minutes = (int) (milliseconds / (1_000 * 60)) % 60;
        int hours = (int) (milliseconds / (1_000 * 60 * 60)) % 24;

        return String.format(String.format(Locale.ENGLISH, "%02d : %02d : %02d", hours, minutes, seconds));
    }

    public static TimerValue timerModelFromMillis(long milliseconds) {
        int seconds = (int) (milliseconds / 1_000) % 60;
        int minutes = (int) (milliseconds / (1_000 * 60)) % 60;
        int hours = (int) (milliseconds / (1_000 * 60 * 60)) % 24;

        return new TimerValue(hours, minutes, seconds);
    }
}
