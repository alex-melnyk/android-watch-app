package com.igorganapolsky.vibratingwatchapp.core.util;

import com.igorganapolsky.vibratingwatchapp.domain.model.TimerModel;

import java.util.Locale;

public class TimerTransform {

    public static long timeToMillis(int hours, int minutes, int seconds) {
        return (seconds * 1_000) + (minutes * 60_000) + (hours * 3_600_000);
    }

    public static String millisToString(long milliseconds) {
        int seconds = (int) (milliseconds / 1_000) % 60;
        int minutes = (int) (milliseconds / (1_000 * 60)) % 60;
        int hours = (int) (milliseconds / (1_000 * 60 * 60)) % 24;
        return String.format(Locale.ENGLISH, "%02d : %02d : %02d", hours, minutes, seconds);
    }

    public static String millisToString(TimerModel value) {
        return String.format(Locale.ENGLISH, "%02d : %02d : %02d",
            value.getHours(),
            value.getMinutes(),
            value.getSeconds());
    }

    public static long secondsToMillis(int seconds) {
        return seconds * 1_000;
    }

    static int getHours(long milliseconds) {
        return (int) (milliseconds / (1_000 * 60 * 60)) % 24;
    }

    static int getMinutes(long milliseconds) {
        return (int) (milliseconds / (1_000 * 60)) % 60;
    }

    static int getSeconds(long milliseconds) {
        return (int) (milliseconds / 1_000) % 60;
    }
}
