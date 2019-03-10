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

public class TimerDetailsViewModel extends ViewModel implements CountdownManager, TickListener {

    private Repository repository;
    private CountdownManager countdownManager;
    private TimerModel timerModel;

    private MutableLiveData<CountData> activeTimerData = new MutableLiveData<>();

    public TimerDetailsViewModel(Repository repository, CountdownManager countdownManager) {
        this.repository = repository;
        this.countdownManager = countdownManager;
    }

    LiveData<CountData> getActiveTimerData() {
        return activeTimerData;
    }

    void prepareData(int currentId) {
        timerModel = repository.getTimerById(currentId);
        long totalTime = TimerTransform.millisFromTimeModel(timerModel);

        setTickListener(this);
        setupTime(totalTime);
    }

    public void deleteTimer() {
        repository.deleteTimer(timerModel.getId());
    }

    @Override
    public void setupTime(long time) {
        countdownManager.setupTime(time);
    }

    @Override
    public void setupTimer(TimerModel model) {
        countdownManager.setupTimer(model);
    }

    @Override
    public void setTickListener(TickListener listener) {
        countdownManager.setTickListener(listener);
    }

    @Override
    public void onStart() {
        countdownManager.onStart();
    }

    @Override
    public void onCancel() {
        countdownManager.onCancel();
    }

    @Override
    public void onStop() {
        countdownManager.onStop();
    }

    @Override
    public void onRestart() {
        countdownManager.onRestart();
    }

    @Override
    public void onTick(String newValue, int progress) {
        activeTimerData.setValue(new CountData(newValue, progress, CountData.State.TICK));
    }

    @Override
    public void onPrepared(String newValue, int progress) {
        activeTimerData.setValue(new CountData(newValue, progress, CountData.State.PREPARED));
    }

    @Override
    public void onFinish(String newValue, int progress) {
        activeTimerData.setValue(new CountData(newValue, progress, CountData.State.FINISH));
    }
}
