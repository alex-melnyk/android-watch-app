package com.igorganapolsky.vibratingwatchapp.manager.vibration;

import android.os.VibrationEffect;
import android.os.Vibrator;

public class WatchBeepManager implements BeepManager {

    private Vibrator vibrator;

    public WatchBeepManager(Vibrator vibrator) {
        this.vibrator = vibrator;
    }

    @Override
    public void onBeep() {
        if (vibrator.hasVibrator()) {
        }
    }
}
