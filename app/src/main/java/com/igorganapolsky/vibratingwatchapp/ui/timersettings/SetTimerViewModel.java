package com.igorganapolsky.vibratingwatchapp.ui.timersettings;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import com.igorganapolsky.vibratingwatchapp.data.Repository;
import com.igorganapolsky.vibratingwatchapp.model.TimeHighlightState;
import com.igorganapolsky.vibratingwatchapp.model.TimerSetup;
import com.igorganapolsky.vibratingwatchapp.model.TimerValue;

public class SetTimerViewModel extends ViewModel {

    enum ActionType {NEW, EDIT}

    private Repository repository;
    private MutableLiveData<TimerValue> timerData;
    private MutableLiveData<TimerSetup> setupData;

    private TimerValue currentTimer;
    private TimerSetup setup;

    public SetTimerViewModel(Repository repository) {
        this.repository = repository;
        this.timerData = new MutableLiveData<>();
        this.currentTimer = new TimerValue();
        this.setup = TimerSetup.HOURS;
    }

    public void setCurrentModelId(int currentId) {
        if (currentId == 0) {
            timerData.setValue(currentTimer);
        } else {
            currentTimer = repository.getTimerById(currentId);
        }
        timerData.setValue(currentTimer);
    }

    public LiveData<TimerValue> getTimerData() {
        return timerData;
    }

    public LiveData<TimerSetup> getSetupData() {
        return setupData;
    }

    public int calculateProgress() {
        return (int) ((double) currentTimer.getValue(setup) / setup.getMeasure() * 100);
    }

    public void setTimerRepeat(int repeatValue) {
        currentTimer.setRepeat(repeatValue);
    }

    public void setSelection(TimerSetup newSelection) {
        this.setup = newSelection;
        setupData.setValue(setup);
    }

    public void setTimeSelection(int newValue) {
        switch (setup) {
            case HOURS:
                currentTimer.setHours(newValue);
                break;
            case MINUTES:
                currentTimer.setMinutes(newValue);
                break;
            case SECONDS:
                currentTimer.setSeconds(newValue);
                break;
        }
    }

    public void setHighlightState(boolean isWhole) {
        if (isWhole) {
            currentTimer.setState(TimeHighlightState.WHOLE);
        } else {
            switch (setup) {
                case HOURS:
                    currentTimer.setState(TimeHighlightState.HOURS);
                    break;
                case MINUTES:
                    currentTimer.setState(TimeHighlightState.MINUTES);
                    break;
                case SECONDS:
                    currentTimer.setState(TimeHighlightState.SECONDS);
                    break;
            }
        }
    }

    public void setBuzz(int newBuzz) {
        currentTimer.setBuzz(newBuzz);
    }
}
