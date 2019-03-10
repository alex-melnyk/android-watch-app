package com.igorganapolsky.vibratingwatchapp.manager;

import android.os.CountDownTimer;

import com.igorganapolsky.vibratingwatchapp.domain.model.TimerModel;
import com.igorganapolsky.vibratingwatchapp.util.TimerTransform;

public class WatchCountdownManager implements CountdownManager {

    private TickListener tickListener;
    private CountDownTimer currentCountdownTimer;
    private TimerModel currentTimerModel;

    private long totalTime;
    private long timeLeft;

    @Override
    public void setTickListener(TickListener listener) {
        this.tickListener = listener;
    }

    @Override
    public int getActiveTimerId() {
        return currentTimerModel == null ? -1 : currentTimerModel.getId();
    }

    @Override
    public long getActiveTimerTimeLeft() {
        return timeLeft;
    }

    @Override
    public void setupTimer(TimerModel timerModel) {
        currentTimerModel = timerModel;

        long currentTimeTotal = TimerTransform.timeToMillis(timerModel.getHoursTotal(), timerModel.getMinutesTotal(), timerModel.getSecondsTotal());
        long currentTimeLeft = TimerTransform.timeToMillis(timerModel.getHoursLeft(), timerModel.getMinutesLeft(), timerModel.getSecondsLeft());

        totalTime = currentTimeTotal * currentTimerModel.getRepeat();
        timeLeft = currentTimeLeft;
    }

    @Override
    public void onStart() {
        currentCountdownTimer = prepareCountdownTimer();
        currentCountdownTimer.start();
    }

    @Override
    public void onCancel() {
        if (currentCountdownTimer != null) {
            currentCountdownTimer.cancel();
        }
    }

    @Override
    public void onStop() {
        clearCountDown();
    }

    @Override
    public void onRestart() {
        clearCountDown();
        currentCountdownTimer = prepareCountdownTimer();
        currentCountdownTimer.start();
    }

    private CountDownTimer prepareCountdownTimer() {
        long millisecondsLeft = timeLeft > 0 ? timeLeft : totalTime;

        return new CountDownTimer(millisecondsLeft, 50) {
            @Override
            public void onTick(long millis) {
                timeLeft = millis;
                int progress = calculateProgress();
                String time = TimerTransform.millisToString(timeLeft);
                if (tickListener != null) {
                    tickListener.onTick(time, progress);
                }
            }

            @Override
            public void onFinish() {
                if (tickListener != null) {
                    timeLeft = 0;
                    String time = TimerTransform.millisToString(totalTime);
                    tickListener.onFinish(time, 100);
                }
            }
        };
    }

    private void clearCountDown() {
        timeLeft = 0L;
        if (currentCountdownTimer != null) {
            currentCountdownTimer.cancel();
            currentCountdownTimer = null;
        }
    }

    private int calculateProgress() {
        return (int) ((double) timeLeft / (double) (totalTime / currentTimerModel.getRepeat()) * 100.);
    }
}
