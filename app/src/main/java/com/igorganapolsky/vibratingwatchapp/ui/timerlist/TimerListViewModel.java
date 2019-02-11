package com.igorganapolsky.vibratingwatchapp.ui.timerlist;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import com.igorganapolsky.vibratingwatchapp.data.models.Timer;

import java.util.List;

public class TimerListViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    private MutableLiveData<List<Timer>> liveData;

    public TimerListViewModel() {
        this.liveData = new MutableLiveData<>();
    }

    public MutableLiveData<List<Timer>> getLiveData() {
        return liveData;
    }
}
