package com.igorganapolsky.vibratingwatchapp.data;

import android.arch.lifecycle.LiveData;
import com.igorganapolsky.vibratingwatchapp.model.TimerValue;

import java.util.List;

public interface Repository {

    TimerValue getTimerById(int id);

    LiveData<List<TimerValue>> getAll();
}
