package com.igorganapolsky.vibratingwatchapp.domain.manager.timer;

/**
 * Simple listener , which provides information about time changes
 * from {@link CountdownManager} to API consumers.
 */
public interface TickListener {

    void onTick(Long timeLeft, int progress);

    void onLapEnd(Long timeLeft, int progress);

    void onFinish(Long timeLeft, int progress, boolean isStop);
}
