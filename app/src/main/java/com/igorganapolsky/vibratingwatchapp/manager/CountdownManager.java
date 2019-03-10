package com.igorganapolsky.vibratingwatchapp.manager;

import com.igorganapolsky.vibratingwatchapp.domain.model.TimerModel;

public interface CountdownManager {

    void setTickListener(TickListener listener);

    TimerModel getActive();

    int getActiveId();

    long getActiveTimeLeft();

    int getActiveProgress();


    void setupTimer(TimerModel model);

    void onStart();

    void onPause();

    void onStop();

    void onRestart();
}
