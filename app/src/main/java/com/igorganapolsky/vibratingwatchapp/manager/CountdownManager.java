package com.igorganapolsky.vibratingwatchapp.manager;

import com.igorganapolsky.vibratingwatchapp.domain.model.TimerModel;

public interface CountdownManager {

    void setTickListener(TickListener listener);

    int getActiveTimerId();

    long getActiveTimerTimeLeft();

    void setupTimer(TimerModel model);

    void onStart();

    void onCancel();

    void onStop();

    void onRestart();
}
