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
    private MutableLiveData<TimeHighlightState> highlightStateData = new MutableLiveData<>();

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
        timerData.setValue(currentTimer);
        highlightStateData.setValue(highlightState);
    }

    void setCurrentModelId(int currentId) {
        if (currentId != 0) {
            currentType = Type.EDIT;
            currentTimer = repository.getTimerById(currentId);
            timerData.setValue(currentTimer);
        }
    }

    public LiveData<TimerModel> getTimerData() {
        return timerData;
    }

    public LiveData<TimerSetup> getSetupData() {
        return setupData;
    }

    public LiveData<TimeHighlightState> getHighliteData() {
        return highlightStateData;
    }

    public int calculateProgress() {
        return (int) ((double) currentTimer.getValue(setup) / setup.getMeasure() * 100);
    }

    public void setTimerRepeat(int repeatValue) {
        currentTimer.setRepeat(repeatValue);
    }

    public void setBuzz(int newBuzz) {
        currentTimer.setBuzz(newBuzz);
    }

    public void setSelection(TimerSetup newSelection) {
        this.setup = newSelection;
        setupData.setValue(setup);
    }

    public void setTimeSelection(int progress) {
        int calculatedValue = (int) (setup.getMeasure() / 100 * progress);

        switch (setup) {
            case HOURS:
                currentTimer.setHours(calculatedValue);
                break;
            case MINUTES:
                currentTimer.setMinutes(calculatedValue);
                break;
            case SECONDS:
                currentTimer.setSeconds(calculatedValue);
                break;
        }
        setupData.setValue(setup);
    }

    public int getCurrentTimeValue() {
        return currentTimer.getValue(setup);
    }

    public void setHighlightState(boolean isWhole) {
        if (isWhole) {
            highlightState = TimeHighlightState.WHOLE;
        } else {
            switch (setup) {
                case HOURS:
                    highlightState = TimeHighlightState.HOURS;
                    break;
                case MINUTES:
                    highlightState = TimeHighlightState.MINUTES;
                    break;
                case SECONDS:
                    highlightState = TimeHighlightState.SECONDS;
                    break;
            }
        }
        highlightStateData.setValue(highlightState);
    }

    void saveTimer() {
        if (currentType == Type.NEW) {
            repository.saveTimer(currentTimer);
        } else {
            repository.updateTimer(currentTimer);
        }
    }
}
