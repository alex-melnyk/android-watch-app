package com.igorganapolsky.vibratingwatchapp.ui.timerlist;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import com.igorganapolsky.vibratingwatchapp.model.TimerModel;

import java.util.ArrayList;
import java.util.List;

public class TimerListViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    private List<TimerModel> timerModelList;
    private MutableLiveData<List<TimerModel>> liveData;

    public TimerListViewModel() {
        this.timerModelList = new ArrayList<TimerModel>();
        this.liveData = new MutableLiveData<>();
//        this.liveData.setValue(timerModelList);
    }

    public MutableLiveData<List<TimerModel>> getLiveData() {
        return liveData;
    }
}
