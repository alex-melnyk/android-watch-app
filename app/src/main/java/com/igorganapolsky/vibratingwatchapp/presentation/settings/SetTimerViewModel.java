package com.igorganapolsky.vibratingwatchapp.presentation.settings;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import com.igorganapolsky.vibratingwatchapp.domain.Repository;
import com.igorganapolsky.vibratingwatchapp.domain.model.BuzzSetup;
import com.igorganapolsky.vibratingwatchapp.domain.model.TimerModel;
import com.igorganapolsky.vibratingwatchapp.domain.model.TimeSetup;

public class SetTimerViewModel extends ViewModel {

    enum Type {NEW, EDIT}

    private final Repository repository;

    private final MutableLiveData<TimerModel> timerData = new MutableLiveData<>();
    private final MutableLiveData<TimeSetup> setupData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> swipeState = new MutableLiveData<>();

    private Type currentType = Type.NEW;
    private TimerModel currentTimer;
    private TimeSetup setup;

    public SetTimerViewModel(Repository repository) {
        this.repository = repository;
        this.currentTimer = TimerModel.createDefault();
        this.setup = TimeSetup.HOURS;
    }

    void setCurrentModelId(int currentId) {
        if (currentId >= 0) {
            currentType = Type.EDIT;
            currentTimer = repository.getTimerById(currentId);
        }
        timerData.setValue(currentTimer);

        swipeState.setValue(!currentTimer.isDefaultTime());
        setupData.setValue(setup);
    }

    public LiveData<TimerModel> getTimerData() {
        return timerData;
    }

    public LiveData<TimeSetup> getSetupData() {
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

    public void setBuzz(BuzzSetup newBuzz) {
        currentTimer.setBuzzTime(newBuzz.getBuzzTime());
        currentTimer.setBuzzCount(newBuzz.getBuzzCount());
        currentTimer.setType(newBuzz.getBuzzType());
    }

    public void setSelection(TimeSetup newSelection) {
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
        swipeState.setValue(!currentTimer.isDefaultTime());
    }

    public int getCurrentTimeValue() {
        return currentTimer.getValue(setup);
    }

    public int getRepeatPosition() {
        return currentTimer.getRepeat() - 1 >= 0 ? currentTimer.getRepeat() - 1 : 0;
    }

    void saveTimer() {
        if (currentType == Type.NEW) {
            repository.saveTimer(currentTimer);
        } else {
            repository.updateTimer(currentTimer);
        }
    }
}
