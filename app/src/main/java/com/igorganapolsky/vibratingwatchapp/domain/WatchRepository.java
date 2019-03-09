package com.igorganapolsky.vibratingwatchapp.domain;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Transformations;
import com.igorganapolsky.vibratingwatchapp.domain.local.TimersDatabase;
import com.igorganapolsky.vibratingwatchapp.domain.local.entity.TimerEntity;
import com.igorganapolsky.vibratingwatchapp.domain.model.TimeHighlightState;
import com.igorganapolsky.vibratingwatchapp.domain.model.TimerValue;
import com.igorganapolsky.vibratingwatchapp.util.TimerTransform;

import java.util.ArrayList;
import java.util.List;

public class WatchRepository implements Repository {

    private TimersDatabase timerDb;

    public WatchRepository(TimersDatabase timerDb) {
        this.timerDb = timerDb;
    }

    @Override
    public TimerValue getTimerById(int id) {
        TimerEntity timer = timerDb.timersDao().getById(id);
        return mapToTimerValue(timer);
    }

    @Override
    public LiveData<List<TimerValue>> getAll() {
        return Transformations.map(timerDb.timersDao().getAll(), (list -> {
            List<TimerValue> mappedList = new ArrayList<>(list.size());
            for (TimerEntity timer : list) {
                mappedList.add(mapToTimerValue(timer));
            }
            return mappedList;
        }));
    }

    private TimerValue mapToTimerValue(TimerEntity entity) {
        TimerValue value = TimerTransform.timerModelFromMillis(entity.getMilliseconds());
        value.setRepeat(entity.getRepeat());
        value.setBuzz(entity.getBuzzMode());
        value.setState(TimeHighlightState.WHOLE);
        return value;
    }
}
