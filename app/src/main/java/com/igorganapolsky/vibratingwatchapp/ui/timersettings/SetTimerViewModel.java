package com.igorganapolsky.vibratingwatchapp.ui.timersettings;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import com.igorganapolsky.vibratingwatchapp.ui.models.TimerValue;


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
