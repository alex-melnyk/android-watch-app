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
    private int currentId;

    private MutableLiveData<CountData> activeTimerData = new MutableLiveData<>();
    private MutableLiveData<TimerModel.State> viewStateData = new MutableLiveData<>();

    public TimerDetailsViewModel(Repository repository, CountdownManager countdownManager) {
        this.repository = repository;
        this.countdownManager = countdownManager;
        countdownManager.setTickListener(this);
    }

    LiveData<CountData> getActiveTimerData() {
        return activeTimerData;
    }

    LiveData<TimerModel.State> getViewStateData() {
        return viewStateData;
    }

    void prepareData(int currentId) {
        this.currentId = currentId;

        TimerModel model = repository.getTimerById(currentId);
        int activeId = countdownManager.getActiveId();
        boolean isActive = model.getId() == activeId;

        if (isActive) {
            timerModel = countdownManager.getActive();
        } else {
            timerModel = model;
        }

        long timeToSetup = prepareTime(isActive);
        int progress = prepareProgress(isActive);

        activeTimerData.setValue(new CountData(
            TimerTransform.millisToString(timeToSetup),
            progress,
            true));

        viewStateData.setValue(timerModel.getState());
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

    private int prepareProgress(boolean isActive) {
        if (isActive) {
            return countdownManager.getActiveProgress();
        } else {
            return 100;
        }
    }

    void onStart() {
        viewStateData.setValue(TimerModel.State.RUN);
        int currentActiveTimerId = countdownManager.getActiveId();

        if (timerModel.getId() == currentActiveTimerId) {
            countdownManager.onStart();
        } else {
            if (currentActiveTimerId != -1) {
//                repository.updateTimerTimeLeft(currentActiveTimerId, countdownManager.getActiveTimeLeft());
                countdownManager.onStop();
            }
            countdownManager.setupTimer(timerModel);
            countdownManager.onStart();
        }
    }

    void onPause() {
        viewStateData.setValue(TimerModel.State.PAUSE);
        countdownManager.onPause();
    }

    void onStop() {
        if (timerModel.getId() == countdownManager.getActiveId()) {
            viewStateData.setValue(TimerModel.State.FINISH);
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
        if (timerModel != null && timerModel.getId() == countdownManager.getActiveId()) {
            activeTimerData.setValue(new CountData(newValue, progress, true));
        }
    }

    @Override
    public void onFinish(String newValue, int progress, boolean isStop) {
        if (timerModel != null && timerModel.getId() == countdownManager.getActiveId()) {
            viewStateData.setValue(TimerModel.State.FINISH);
            activeTimerData.setValue(new CountData(newValue, progress, isStop));
        }
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        countdownManager.setTickListener(null);
    }
}
