package com.igorganapolsky.vibratingwatchapp.ui.timerlist;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import com.igorganapolsky.vibratingwatchapp.data.Repository;
import com.igorganapolsky.vibratingwatchapp.model.TimerValue;

import java.util.List;

public class TimerListViewModel extends ViewModel {

    private Repository repository;

    public TimerListViewModel(Repository repository) {
        this.repository = repository;
    }

    LiveData<List<TimerValue>> getLiveData() {
        return repository.getAll();
    }
}
