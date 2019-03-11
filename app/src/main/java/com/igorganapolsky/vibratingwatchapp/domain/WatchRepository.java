package com.igorganapolsky.vibratingwatchapp.domain;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Transformations;
import com.igorganapolsky.vibratingwatchapp.domain.local.TimersDatabase;
import com.igorganapolsky.vibratingwatchapp.domain.local.entity.TimerEntity;
import com.igorganapolsky.vibratingwatchapp.domain.model.TimerModel;
import com.igorganapolsky.vibratingwatchapp.core.util.TimerTransform;

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
    public LiveData<TimerModel> getObservableTimerById(int id) {
        return Transformations.map(timerDb.timersDao().observeById(id), (entity -> {
            if (entity == null) {
                return null;
            } else {
                return mapToTimerModel(entity);
            }
        }));
    }

    @Override
    public void updateTimer(TimerModel model) {
        TimerEntity timerEntity = mapToTimerEntity(model);
        timerEntity.setId(model.getId());
        timerDb.timersDao().update(timerEntity);
    }

    @Override
    public void saveTimer(TimerModel model) {
        timerDb.timersDao().insert(mapToTimerEntity(model));
    }

    @Override
    public void updateTimerTimeLeft(int timerId, long timeLeft) {

    }

    @Override
    public void deleteTimer(int id) {
        timerDb.timersDao().deleteById(id);
    }

    @Override
    public void updateTimerState(int timerId, TimerModel.State newState) {
        timerDb.timersDao().updateTimerState(timerId, newState.name());
    }

    private TimerModel mapToTimerModel(TimerEntity entity) {
        TimerModel model = new TimerModel();
        model.setId(entity.getId());
        model.setRepeat(entity.getRepeat());
        model.setBuzzCount(entity.getBuzzMode());
        model.setState(TimerModel.State.valueOf(entity.getState()));

        model.setHoursTotal(TimerTransform.getHours(entity.getMillisecondsTotal()));
        model.setHoursLeft(TimerTransform.getHours(entity.getMillisecondsLeft()));

        model.setMinutesTotal(TimerTransform.getMinutes(entity.getMillisecondsTotal()));
        model.setMinutesLeft(TimerTransform.getMinutes(entity.getMillisecondsLeft()));

        model.setSecondsTotal(TimerTransform.getSeconds(entity.getMillisecondsTotal()));
        model.setSecondsLeft(TimerTransform.getSeconds(entity.getMillisecondsLeft()));

        return model;
    }

    private TimerEntity mapToTimerEntity(TimerModel model) {
        TimerEntity entity = new TimerEntity();
        entity.setBuzzMode(model.getBuzzCount());
        entity.setRepeat(model.getRepeat());
        entity.setState(TimerModel.State.FINISH.name());

        entity.setMillisecondsTotal(TimerTransform.timeToMillis(model.getHoursTotal(), model.getMinutesTotal(), model.getSecondsTotal()));
        entity.setMillisecondsLeft(TimerTransform.timeToMillis(model.getHoursLeft(), model.getMinutesLeft(), model.getSecondsLeft()));
        return entity;
    }
}
