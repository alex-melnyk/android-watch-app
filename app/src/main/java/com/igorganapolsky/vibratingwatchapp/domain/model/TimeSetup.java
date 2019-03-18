package com.igorganapolsky.vibratingwatchapp.domain.model;

/**
 * Represent time settings of {@link com.igorganapolsky.vibratingwatchapp.presentation.settings.step.SetTimerTimeFragment}
 */

public enum TimeSetup {
    HOURS("H", 12.), MINUTES("M", 59.), SECONDS("S", 59.);

    private final String shortcut;
    private final double measure;

    TimeSetup(String shortcut, double measure) {
        this.shortcut = shortcut;
        this.measure = measure;
    }

    public String getShortcut() {
        return shortcut;
    }

    public double getMeasure() {
        return measure;
    }
}
