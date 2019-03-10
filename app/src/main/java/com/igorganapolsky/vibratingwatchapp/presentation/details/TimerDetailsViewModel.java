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
    private MutableLiveData<CountData.State> viewStateData = new MutableLiveData<>();

    public TimerDetailsViewModel(Repository repository, CountdownManager countdownManager) {
        this.repository = repository;
        this.countdownManager = countdownManager;
    }

    LiveData<CountData> getActiveTimerData() {
        return activeTimerData;
    }

    LiveData<CountData.State> getViewStateData() {
        return viewStateData;
    }

    void prepareData(int currentId) {
        timerModel = repository.getTimerById(currentId);
        countdownManager.setTickListener(this);

        if (timerModel.getId() != countdownManager.getActiveTimerId()) {
            prepareNonActiveData();
        }
    }

    private void prepareNonActiveData() {
        long currentTimeTotal = TimerTransform.timeToMillis(timerModel.getHoursTotal(), timerModel.getMinutesTotal(), timerModel.getSecondsTotal());
        long currentTimeLeft = TimerTransform.timeToMillis(timerModel.getHoursLeft(), timerModel.getMinutesLeft(), timerModel.getSecondsLeft());
        long preparedTime = currentTimeLeft == currentTimeTotal ? currentTimeLeft : currentTimeLeft;

        viewStateData.setValue(CountData.State.PREPARE);
        activeTimerData.setValue(new CountData(
            TimerTransform.millisToString(preparedTime),
            100,
            false));
    }

    void onStart() {
        viewStateData.setValue(CountData.State.PLAY);

        int currentActiveTimerId = countdownManager.getActiveTimerId();

        if (timerModel.getId() == currentActiveTimerId) {
            countdownManager.onStart();
        } else {
            if (currentActiveTimerId != -1) {
                repository.updateTimerTimeLeft(currentActiveTimerId, countdownManager.getActiveTimerTimeLeft());
                countdownManager.onStop();
            }
            countdownManager.setupTimer(timerModel);
            countdownManager.onStart();
        }
    }

    void onPause() {
        viewStateData.setValue(CountData.State.PAUSE);
        if (countdownManager != null) {
            countdownManager.onCancel();
        }
    }

    void onStop() {
        viewStateData.setValue(CountData.State.FINISH);
        if (countdownManager != null) {
            countdownManager.onStop();
        }
    }

    void onRestart() {
        viewStateData.setValue(CountData.State.PLAY);
        countdownManager.onRestart();
    }

    public void deleteTimer() {
        repository.deleteTimer(timerModel.getId());
    }

    @Override
    public void onTick(String newValue, int progress) {
        if (timerModel.getId() == countdownManager.getActiveTimerId()) {
            activeTimerData.setValue(new CountData(newValue, progress, true));
        }
    }

    @Override
    public void onPrepared(String newValue, int progress) {
        if (timerModel.getId() == countdownManager.getActiveTimerId()) {
            activeTimerData.setValue(new CountData(newValue, progress, false));
        }
    }

    @Override
    public void onFinish(String newValue, int progress) {
        if (timerModel.getId() == countdownManager.getActiveTimerId()) {
            viewStateData.setValue(CountData.State.FINISH);
            activeTimerData.setValue(new CountData(newValue, progress, false));
        }
    }
}
