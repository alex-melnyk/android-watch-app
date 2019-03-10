package com.igorganapolsky.vibratingwatchapp.presentation.settings;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.igorganapolsky.vibratingwatchapp.domain.Repository;
import com.igorganapolsky.vibratingwatchapp.domain.model.TimeHighlightState;
import com.igorganapolsky.vibratingwatchapp.domain.model.TimerModel;
import com.igorganapolsky.vibratingwatchapp.domain.model.TimerSetup;

public class SetTimerViewModel extends ViewModel {

    enum Type {NEW, EDIT}

    private Repository repository;

    private MutableLiveData<TimerModel> timerData = new MutableLiveData<>();
    private MutableLiveData<TimerSetup> setupData = new MutableLiveData<>();
    private MutableLiveData<Boolean> swipeState = new MutableLiveData<>();

    private Type currentType = Type.NEW;

    private TimerModel currentTimer;
    private TimerSetup setup;
    private TimeHighlightState highlightState;

    public SetTimerViewModel(Repository repository) {
        this.repository = repository;

        this.currentTimer = new TimerModel();
        this.setup = TimerSetup.HOURS;
        this.highlightState = TimeHighlightState.HOURS;

        setupData.setValue(setup);
    }

    void setCurrentModelId(int currentId) {
        if (currentId != 0) {
            currentType = Type.EDIT;
            currentTimer = repository.getTimerById(currentId);
        }
        timerData.setValue(currentTimer);
    }

    public LiveData<TimerModel> getTimerData() {
        return timerData;
    }

    public LiveData<TimerSetup> getSetupData() {
        return setupData;
    }

    LiveData<Boolean> getSwipeState() {
        return swipeState;
    }

    public int calculateProgress() {
        return (int) ((double) currentTimer.getValue(setup) / setup.getMeasure() * 100);
    }

    public void setTimerRepeat(int repeatValue) {
        currentTimer.setRepeat(repeatValue + 1);
    }

    public void setBuzz(int newBuzz) {
        int buzzCount;
        switch (newBuzz) {
            case 0:
                buzzCount = 1;
                break;
            case 1:
                buzzCount = 3;
                break;
            case 2:
                buzzCount = 5;
                break;
            default:
                buzzCount = 10;
                break;
        }
        currentTimer.setBuzz(buzzCount);
    }

    public void setSelection(TimerSetup newSelection) {
        this.setup = newSelection;
        setupData.setValue(setup);
    }

    public void setTimeSelection(int progress) {
        int calculatedValue = (int) (setup.getMeasure() / 100 * progress);

        switch (setup) {
            case HOURS:
                currentTimer.setHoursTotal(calculatedValue);
                currentTimer.setHoursLeft(calculatedValue);
                break;
            case MINUTES:
                currentTimer.setMinutesTotal(calculatedValue);
                currentTimer.setMinutesLeft(calculatedValue);
                break;
            case SECONDS:
                currentTimer.setSecondsTotal(calculatedValue);
                currentTimer.setSecondsLeft(calculatedValue);
                break;
        }

        setupData.setValue(setup);
        swipeState.setValue(!currentTimer.isDefaultTime());
    }

    public int getCurrentTimeValue() {
        return currentTimer.getValue(setup);
    }

    void saveTimer() {
        if (currentType == Type.NEW) {
            repository.saveTimer(currentTimer);
        } else {
            repository.updateTimer(currentTimer);
        }
    }
}
