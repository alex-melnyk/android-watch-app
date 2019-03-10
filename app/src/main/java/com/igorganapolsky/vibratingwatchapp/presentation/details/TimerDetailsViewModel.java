package com.igorganapolsky.vibratingwatchapp.presentation.details;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.igorganapolsky.vibratingwatchapp.domain.Repository;
import com.igorganapolsky.vibratingwatchapp.domain.model.CountData;
import com.igorganapolsky.vibratingwatchapp.domain.model.TimerModel;
import com.igorganapolsky.vibratingwatchapp.manager.CountdownManager;
import com.igorganapolsky.vibratingwatchapp.manager.TickListener;
import com.igorganapolsky.vibratingwatchapp.util.TimerTransform;

public class TimerDetailsViewModel extends ViewModel implements TickListener {

    private Repository repository;
    private CountdownManager countdownManager;
    private TimerModel timerModel;

    private MutableLiveData<CountData> activeTimerData = new MutableLiveData<>();
    private MutableLiveData<TimerModel.State> viewStateData = new MutableLiveData<>();

    public TimerDetailsViewModel(Repository repository, CountdownManager countdownManager) {
        this.repository = repository;
        this.countdownManager = countdownManager;
    }

    LiveData<CountData> getActiveTimerData() {
        return activeTimerData;
    }

    LiveData<TimerModel.State> getViewStateData() {
        return viewStateData;
    }

    void prepareData(int currentId) {
        countdownManager.setTickListener(this);
        int activeId = countdownManager.getActiveId();
        boolean isActive = currentId == activeId;

        if (isActive) {
            timerModel = countdownManager.getActive();
        } else {
            timerModel = repository.getTimerById(currentId);
        }

        long timeToSetup = prepareTime(isActive);
        viewStateData.setValue(timerModel.getState());
        activeTimerData.postValue(new CountData(
            TimerTransform.millisToString(timeToSetup),
            100,
            false));
    }

    private long prepareTime(boolean isActive) {
        if (isActive) {
            return countdownManager.getActiveTimeLeft();
        } else {
            long currentTimeTotal = TimerTransform.timeToMillis(timerModel.getHoursTotal(), timerModel.getMinutesTotal(), timerModel.getSecondsTotal());
            long currentTimeLeft = TimerTransform.timeToMillis(timerModel.getHoursLeft(), timerModel.getMinutesLeft(), timerModel.getSecondsLeft());
            return currentTimeLeft > 0 ? currentTimeLeft : currentTimeTotal;
        }
    }

    void onStart() {
        viewStateData.setValue(TimerModel.State.RUN);

        int currentActiveTimerId = countdownManager.getActiveId();

        if (timerModel.getId() == currentActiveTimerId) {
            countdownManager.onStart();
        } else {
            if (currentActiveTimerId != -1) {
                repository.updateTimerTimeLeft(currentActiveTimerId, countdownManager.getActiveTimeLeft());
                countdownManager.onStop();
            }
            countdownManager.setupTimer(timerModel);
            countdownManager.onStart();
        }
    }

    void onPause() {
        viewStateData.setValue(TimerModel.State.PAUSE);
        if (countdownManager != null) {
            countdownManager.onPause();
        }
    }

    void onStop() {
        viewStateData.setValue(TimerModel.State.FINISH);
        if (!timerModel.isDefaultTime()) {
            repository.updateTimerTimeLeft(timerModel.getId(), countdownManager.getActiveTimeLeft());
        }
        if (countdownManager != null) {
            countdownManager.onStop();
        }
    }

    void onRestart() {
        viewStateData.setValue(TimerModel.State.RUN);
        countdownManager.onRestart();
    }

    public void deleteTimer() {
        repository.deleteTimer(timerModel.getId());
    }

    @Override
    public void onTick(String newValue, int progress) {
        if (timerModel.getId() == countdownManager.getActiveId()) {
            activeTimerData.setValue(new CountData(newValue, progress, true));
        }
    }

    @Override
    public void onFinish(String newValue, int progress) {
        if (timerModel.getId() == countdownManager.getActiveId()) {
            viewStateData.setValue(TimerModel.State.FINISH);
            activeTimerData.setValue(new CountData(newValue, progress, true));
        }
    }
}
