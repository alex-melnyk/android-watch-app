package com.igorganapolsky.vibratingwatchapp.domain;

import android.arch.lifecycle.LiveData;
import com.igorganapolsky.vibratingwatchapp.domain.model.TimerValue;

import java.util.List;

public interface Repository {

    TimerValue getTimerById(int id);

    LiveData<List<TimerValue>> getAll();
}
