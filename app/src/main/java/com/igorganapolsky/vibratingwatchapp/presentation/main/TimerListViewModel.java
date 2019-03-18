package com.igorganapolsky.vibratingwatchapp.presentation.main;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import com.igorganapolsky.vibratingwatchapp.domain.Repository;
import com.igorganapolsky.vibratingwatchapp.domain.manager.timer.CountdownManager;
import com.igorganapolsky.vibratingwatchapp.domain.model.TimerModel;

import java.util.List;

public class TimerListViewModel extends ViewModel {

    private Repository repository;
    private final CountdownManager countdownManager;

    public TimerListViewModel(Repository repository, CountdownManager countdownManager) {
        this.repository = repository;
        this.countdownManager = countdownManager;
        countdownManager.observeActiveModel().observeForever(activeObserver);
    }

    private Observer<TimerModel> activeObserver = model -> {
        if (model == null) return;
        if (model.getState() == TimerModel.State.RUN || model.getState() == TimerModel.State.FINISH) {
            repository.updateTimerState(model.getId(), model.getState());
        }
    };

    LiveData<List<TimerModel>> getAllTimers() {
        return repository.getAll();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        countdownManager.observeActiveModel().removeObserver(activeObserver);
    }
}
