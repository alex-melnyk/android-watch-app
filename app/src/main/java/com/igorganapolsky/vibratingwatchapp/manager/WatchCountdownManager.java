package com.igorganapolsky.vibratingwatchapp.manager;

import android.os.CountDownTimer;
import android.util.Log;

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
    public int getActiveId() {
        return currentTimerModel == null ? -1 : currentTimerModel.getId();
    }

    @Override
    public long getActiveTimeLeft() {
        return timeLeft;
    }

    @Override
    public TimerModel getActive() {
        return currentTimerModel;
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
        currentTimerModel.setState(TimerModel.State.RUN);
    }

    @Override
    public void onPause() {
        if (currentCountdownTimer != null) {
            currentCountdownTimer.cancel();
        }
        currentTimerModel.setState(TimerModel.State.PAUSE);
    }

    @Override
    public void onStop() {
        clearCountDown();
    }

    @Override
    public void onRestart() {
        clearCountDown();
        onStart();
    }

    private CountDownTimer prepareCountdownTimer() {
        long millisecondsLeft = timeLeft > 0 ? timeLeft : totalTime * currentTimerModel.getRepeat();

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
                    currentTimerModel.setState(TimerModel.State.FINISH);
                    String time = TimerTransform.millisToString(totalTime / currentTimerModel.getRepeat());
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
        int currentProgress = (int) ((float) timeLeft / (float) (totalTime) * 100.);
        Log.d("Larkionov", "Current progress " + currentProgress);
        return currentProgress;
    }
}
