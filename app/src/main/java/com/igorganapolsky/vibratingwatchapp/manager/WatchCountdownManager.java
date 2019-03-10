package com.igorganapolsky.vibratingwatchapp.manager;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.os.CountDownTimer;

import com.igorganapolsky.vibratingwatchapp.domain.model.TimerModel;
import com.igorganapolsky.vibratingwatchapp.util.TimerTransform;

public class WatchCountdownManager implements CountdownManager {

    private TickListener tickListener;
    private CountDownTimer currentCountdownTimer;

    private MutableLiveData<TimerModel> activeModel = new MutableLiveData<>();
    private TimerModel currentTimerModel;

    private long totalTime;
    private long timeLeft;

    @Override
    public LiveData<TimerModel> observeActiveModel() {
        return activeModel;
    }

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
        return currentTimerModel.getState() == TimerModel.State.FINISH ? totalTime : timeLeft;
    }

    @Override
    public int getActiveProgress() {
        return currentTimerModel.getState() == TimerModel.State.FINISH ? 100 : calculateProgress();
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

        totalTime = currentTimeTotal;
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
        String time = TimerTransform.millisToString(totalTime);
        tickListener.onFinish(time, 100);
    }

    @Override
    public void onRestart() {
        clearCountDown();
        onStart();
    }

    private CountDownTimer prepareCountdownTimer() {
        long millisecondsLeft = timeLeft > 0 ? timeLeft : totalTime;

        return new CountDownTimer(millisecondsLeft, 50) {
            @Override
            public void onTick(long millis) {
                timeLeft = millis;
                notifyOnTick();
            }

            @Override
            public void onFinish() {
                timeLeft = 0;
                notifyOnFinish();
            }
        };
    }

    private void notifyOnTick() {
        if (tickListener != null) {
            int progress = calculateProgress();
            String time = TimerTransform.millisToString(timeLeft);
            tickListener.onTick(time, progress);
        }
    }

    private void notifyOnFinish() {
        if (tickListener != null) {
            currentTimerModel.setState(TimerModel.State.FINISH);
            String time = TimerTransform.millisToString(totalTime);
            tickListener.onFinish(time, 100);
        }
    }

    private void clearCountDown() {
        timeLeft = 0L;
        if (currentCountdownTimer != null) {
            currentCountdownTimer.cancel();
            currentCountdownTimer = null;
        }
    }

    private int calculateProgress() {
        return (int) ((float) timeLeft / (float) (totalTime) * 100.);
    }
}
