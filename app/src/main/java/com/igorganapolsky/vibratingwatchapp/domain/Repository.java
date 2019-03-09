package com.igorganapolsky.vibratingwatchapp.domain;

import android.arch.lifecycle.LiveData;
import com.igorganapolsky.vibratingwatchapp.domain.model.TimerModel;

import java.util.List;

public interface Repository {

    TimerModel getTimerById(int id);

    LiveData<List<TimerModel>> getAll();

    void updateTimer(TimerModel timer);

    void saveTimer(TimerModel timer);

    void deleteTimer(int id);
}
