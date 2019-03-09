package com.igorganapolsky.vibratingwatchapp.presentation.main;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import com.igorganapolsky.vibratingwatchapp.domain.Repository;
import com.igorganapolsky.vibratingwatchapp.manager.CountdownManager;
import com.igorganapolsky.vibratingwatchapp.domain.model.TimerValue;

import java.util.List;

public class TimerListViewModel extends ViewModel {

    private Repository repository;
    private CountdownManager countdownManager;

    public TimerListViewModel(Repository repository, CountdownManager countdownManager) {
        this.repository = repository;
        this.countdownManager = countdownManager;
    }

    LiveData<List<TimerValue>> getLiveData() {
        return repository.getAll();
    }
}
