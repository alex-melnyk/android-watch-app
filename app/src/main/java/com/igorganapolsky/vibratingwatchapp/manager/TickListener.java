package com.igorganapolsky.vibratingwatchapp.manager;

public interface TickListener {

    void onTick(String newValue, int progress);

    void onFinish(String newValue, int progress, boolean isStop);
}