package com.igorganapolsky.vibratingwatchapp.manager;

import android.os.CountDownTimer;

import com.igorganapolsky.vibratingwatchapp.domain.model.TimerModel;
import com.igorganapolsky.vibratingwatchapp.util.TimerTransform;

public class WatchCountdownManager implements CountdownManager {

    private TickListener tickListener;
    private long totalTime;
    private long timeLeft;
    private CountDownTimer currentTimer;

    public WatchCountdownManager() {
    }

    @Override
    public void setupTimer(TimerModel timerModel) {

    }

    @Override
    public void setupTime(long newTime) {

        totalTime = newTime;
        timeLeft = newTime;

        if (tickListener != null) {
            int progress = (int) ((double) timeLeft / (double) totalTime * 100.);
            String time = TimerTransform.millisToTimeString(timeLeft);
            tickListener.onPrepared(time, progress);
        }
    }

    @Override
    public void setTickListener(TickListener listener) {
        this.tickListener = listener;
    }

    @Override
    public void onStart() {
        currentTimer = prepareAndStart();
        currentTimer.start();
    }

    @Override
    public void onCancel() {
        currentTimer.cancel();
    }

    @Override
    public void onStop() {
        clearCountDown();
    }

    @Override
    public void onRestart() {
        clearCountDown();
        currentTimer = prepareAndStart();
        currentTimer.start();
    }

    private CountDownTimer prepareAndStart() {
        long millisecondsLeft = timeLeft > 0 ? timeLeft : totalTime;

        if (tickListener != null) {
            int progress = (int) ((double) timeLeft / (double) totalTime * 100.);
            String time = TimerTransform.millisToTimeString(timeLeft);
            tickListener.onPrepared(time, progress);
        }

        return new CountDownTimer(millisecondsLeft, 50) {
            @Override
            public void onTick(long millis) {
                timeLeft = millis;
                int progress = (int) ((double) timeLeft / (double) totalTime * 100.);
                String time = TimerTransform.millisToTimeString(timeLeft);
                if (tickListener != null) {
                    tickListener.onTick(time, progress);
                }
            }

            @Override
            public void onFinish() {
                if (tickListener != null) {
                    String time = TimerTransform.millisToTimeString(0);
                    tickListener.onFinish(time, 0);
                }
            }
        };
    }

    private void clearCountDown() {
        timeLeft = 0L;
        if (currentTimer != null) {
            currentTimer.cancel();
            currentTimer = null;
        }
    }
}
