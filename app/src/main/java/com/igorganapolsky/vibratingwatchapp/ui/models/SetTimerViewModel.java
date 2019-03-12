package com.igorganapolsky.vibratingwatchapp.ui.models;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;


public class SetTimerViewModel extends ViewModel {
    private MutableLiveData<TimerValue> timerValue;

    public MutableLiveData<TimerValue> getTimerValue() {
        if (timerValue == null) {
            timerValue = new MutableLiveData<TimerValue>();
            timerValue.setValue(new TimerValue());
        }

        return timerValue;
    }
}
