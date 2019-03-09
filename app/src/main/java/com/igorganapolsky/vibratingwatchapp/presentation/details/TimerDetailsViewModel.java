package com.igorganapolsky.vibratingwatchapp.presentation.details;

import android.arch.lifecycle.ViewModel;
import com.igorganapolsky.vibratingwatchapp.domain.Repository;
import com.igorganapolsky.vibratingwatchapp.domain.model.TimerModel;
import com.igorganapolsky.vibratingwatchapp.manager.CountdownManager;

public class TimerDetailsViewModel extends ViewModel {

    private Repository repository;
    private CountdownManager countdownManager;
    private TimerModel timerModel;

    public TimerDetailsViewModel(Repository repository, CountdownManager countdownManager) {
        this.repository = repository;
        this.countdownManager = countdownManager;
    }

    void setCurrentModelId(int currentId) {
        timerModel = repository.getTimerById(currentId);
    }

    public void deleteTimer() {
        repository.deleteTimer(timerModel.getId());
    }
}
