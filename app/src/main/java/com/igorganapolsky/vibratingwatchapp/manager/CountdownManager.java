package com.igorganapolsky.vibratingwatchapp.manager;

import com.igorganapolsky.vibratingwatchapp.domain.model.TimerModel;

public interface CountdownManager {

    void setupTime(long time);

    void setupTimer(TimerModel model);

    void setTickListener(TickListener listener);

    void onStart();

    void onCancel();

    void onStop();

    void onRestart();
}
