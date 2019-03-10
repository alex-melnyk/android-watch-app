package com.igorganapolsky.vibratingwatchapp.manager;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.os.CountDownTimer;
import android.util.Log;

import com.igorganapolsky.vibratingwatchapp.domain.model.TimerModel;
import com.igorganapolsky.vibratingwatchapp.util.TimerTransform;

public class WatchCountdownManager implements CountdownManager {

    private MutableLiveData<TimerModel> activeModelData = new MutableLiveData<>();

    private TickListener tickListener;
    private CountDownTimer currentCountdownTimer;
    private TimerModel activeModel;

    private long totalTime;
    private long lapTime;
    private long timeLeft;

    @Override
    public LiveData<TimerModel> observeActiveModel() {
        return activeModelData;
    }

    @Override
    public void setTickListener(TickListener listener) {
        this.tickListener = listener;
    }

    @Override
    public int getActiveId() {
        return activeModel == null ? -1 : activeModel.getId();
    }

    @Override
    public long getActiveTimeLeft() {
        return activeModel.getState() == TimerModel.State.FINISH ? lapTime : timeLeft % lapTime;
    }

    @Override
    public int getActiveProgress() {
        return activeModel.getState() == TimerModel.State.FINISH ? 100 : calculateProgress();
    }

    @Override
    public TimerModel getActive() {
        return activeModel;
    }

    @Override
    public void setupTimer(TimerModel timerModel) {
        activeModel = timerModel;

        long currentTimeTotal = TimerTransform.timeToMillis(timerModel.getHoursTotal(), timerModel.getMinutesTotal(), timerModel.getSecondsTotal());
        long currentTimeLeft = TimerTransform.timeToMillis(timerModel.getHoursLeft(), timerModel.getMinutesLeft(), timerModel.getSecondsLeft());

        lapTime = currentTimeTotal;
        totalTime = currentTimeTotal * activeModel.getRepeat();
        timeLeft = currentTimeLeft * activeModel.getRepeat();
    }

    @Override
    public void onStart() {
        activeModel.setState(TimerModel.State.RUN);
        currentCountdownTimer = prepareCountdownTimer();
        currentCountdownTimer.start();

    }

    @Override
    public void onPause() {
        activeModel.setState(TimerModel.State.PAUSE);
        if (currentCountdownTimer != null) {
            currentCountdownTimer.cancel();
        }
    }

    @Override
    public void onStop() {
        clearCountDown();
        notifyOnFinish(true);
    }

    @Override
    public void onRestart() {
        clearCountDown();
        onStart();
    }

    private CountDownTimer prepareCountdownTimer() {
        long millisecondsLeft = timeLeft > 0 ? timeLeft : totalTime;

        return new CountDownTimer(millisecondsLeft, 100) {
            @Override
            public void onTick(long millis) {
                timeLeft = millis;
                notifyOnTick();
            }

            @Override
            public void onFinish() {
                timeLeft = 0;
                notifyOnFinish(false);
            }
        };
    }

    private void notifyOnTick() {
        if (tickListener != null) {
            int progress = calculateProgress();
            String time = TimerTransform.millisToString(timeLeft % lapTime);
            tickListener.onTick(time, progress);

//            Log.d("Laktionov", " tick -> active id: " + activeModel.getId() + " time left : " + timeLeft + " total time : " + totalTime);
        }
    }

    private void notifyOnFinish(boolean isStop) {
        activeModel.setState(TimerModel.State.FINISH);
        if (tickListener != null) {
            String time = TimerTransform.millisToString(lapTime);
            tickListener.onFinish(time, 100, isStop);

//            Log.d("Laktionov", "finish -> active id: " + activeModel.getId() + " time left : " + timeLeft + " total time : " + totalTime);
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
        int percent = (int) ((float) (timeLeft % lapTime) / (float) (lapTime) * 100.);
        Log.d("Laktionov", "percent : " + percent);
        return percent;
    }
}
