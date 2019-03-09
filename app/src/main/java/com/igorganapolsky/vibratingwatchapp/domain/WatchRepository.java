package com.igorganapolsky.vibratingwatchapp.domain;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Transformations;
import com.igorganapolsky.vibratingwatchapp.domain.local.TimersDatabase;
import com.igorganapolsky.vibratingwatchapp.domain.local.entity.TimerEntity;
import com.igorganapolsky.vibratingwatchapp.domain.model.TimeHighlightState;
import com.igorganapolsky.vibratingwatchapp.domain.model.TimerModel;
import com.igorganapolsky.vibratingwatchapp.util.TimerTransform;

import java.util.ArrayList;
import java.util.List;

public class WatchRepository implements Repository {

    private TimersDatabase timerDb;

    public WatchRepository(TimersDatabase timerDb) {
        this.timerDb = timerDb;
    }

    @Override
    public TimerModel getTimerById(int id) {
        TimerEntity timer = timerDb.timersDao().getById(id);
        return mapToTimerModel(timer);
    }

    @Override
    public LiveData<List<TimerModel>> getAll() {
        return Transformations.map(timerDb.timersDao().getAll(), (list -> {
            List<TimerModel> mappedList = new ArrayList<>(list.size());
            for (TimerEntity timer : list) {
                mappedList.add(mapToTimerModel(timer));
            }
            return mappedList;
        }));
    }

    @Override
    public void updateTimer(TimerModel model) {
        timerDb.timersDao().update(mapToTimerEntity(model));
    }

    @Override
    public void saveTimer(TimerModel model) {
        timerDb.timersDao().insert(mapToTimerEntity(model));
    }

    @Override
    public void deleteTimer(int id) {
        timerDb.timersDao().deleteById(id);
    }

    private TimerModel mapToTimerModel(TimerEntity entity) {
        TimerModel value = TimerTransform.timerModelFromMillis(entity.getMilliseconds());
        value.setId(entity.getId());
        value.setRepeat(entity.getRepeat());
        value.setBuzz(entity.getBuzzMode());
        value.setState(TimeHighlightState.WHOLE);
        return value;
    }

    private TimerEntity mapToTimerEntity(TimerModel model) {
        TimerEntity entity = new TimerEntity();
        entity.setBuzzMode(model.getBuzz());
        entity.setMilliseconds(TimerTransform.timeToMillis(model.getHours(), model.getMinutes(), model.getSeconds()));
        entity.setState(model.getState().name());
        entity.setRepeat(model.getRepeat());
        return entity;
    }
}
