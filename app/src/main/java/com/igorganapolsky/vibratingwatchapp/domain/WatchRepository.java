package com.igorganapolsky.vibratingwatchapp.domain;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Transformations;
import com.igorganapolsky.vibratingwatchapp.core.util.Mappers;
import com.igorganapolsky.vibratingwatchapp.domain.local.TimersDatabase;
import com.igorganapolsky.vibratingwatchapp.domain.local.entity.TimerEntity;
import com.igorganapolsky.vibratingwatchapp.domain.model.TimerModel;

import java.util.ArrayList;
import java.util.List;

public class WatchRepository implements Repository {

    private final TimersDatabase timerDb;

    public WatchRepository(TimersDatabase timerDb) {
        this.timerDb = timerDb;
        disableAll();
    }

    @Override
    public TimerModel getTimerById(int id) {
        TimerEntity timer = timerDb.timersDao().getById(id);
        return Mappers.mapToTimerModel(timer);
    }

    @Override
    public LiveData<List<TimerModel>> getAll() {
        return Transformations.map(timerDb.timersDao().getAll(), (list -> {
            List<TimerModel> mappedList = new ArrayList<>(list.size());
            for (TimerEntity timer : list) {
                mappedList.add(Mappers.mapToTimerModel(timer));
            }
            return mappedList;
        }));
    }

    @Override
    public void updateTimer(TimerModel model) {
        TimerEntity timerEntity = Mappers.mapToTimerEntity(model);
        timerEntity.setId(model.getId());
        timerDb.timersDao().update(timerEntity);
    }

    @Override
    public void saveTimer(TimerModel model) {
        timerDb.timersDao().insert(Mappers.mapToTimerEntity(model));
    }

    @Override
    public void deleteTimer(int id) {
        timerDb.timersDao().deleteById(id);
    }

    @Override
    public void updateTimerState(int timerId, TimerModel.State newState) {
        timerDb.timersDao().updateTimerState(timerId, newState.name());
    }

    private void disableAll() {
        timerDb.timersDao().disableAll(TimerModel.State.FINISH.name());
    }
}
