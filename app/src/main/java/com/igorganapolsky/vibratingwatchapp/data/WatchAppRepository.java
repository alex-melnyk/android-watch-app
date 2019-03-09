package com.igorganapolsky.vibratingwatchapp.data;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Transformations;
import com.igorganapolsky.vibratingwatchapp.data.db.TimersDatabase;
import com.igorganapolsky.vibratingwatchapp.data.models.Timer;
import com.igorganapolsky.vibratingwatchapp.model.TimeHighlightState;
import com.igorganapolsky.vibratingwatchapp.model.TimerValue;
import com.igorganapolsky.vibratingwatchapp.util.TimerTransform;

import java.util.ArrayList;
import java.util.List;

public class WatchAppRepository implements Repository {

    private TimersDatabase timerDb;

    public WatchAppRepository(TimersDatabase timerDb) {
        this.timerDb = timerDb;
    }

    @Override
    public TimerValue getTimerById(int id) {
        Timer timer = timerDb.timersDao().getById(id);
        return mapToTimerValue(timer);
    }

    @Override
    public LiveData<List<TimerValue>> getAll() {
        return Transformations.map(timerDb.timersDao().getAll(), (list -> {
            List<TimerValue> mappedList = new ArrayList<>(list.size());
            for (Timer timer : list) {
                mappedList.add(mapToTimerValue(timer));
            }
            return mappedList;
        }));
    }

    private TimerValue mapToTimerValue(Timer entity) {
        TimerValue value = TimerTransform.timerModelFromMillis(entity.getMilliseconds());
        value.setRepeat(entity.getRepeat());
        value.setBuzz(entity.getBuzzMode());
        value.setState(TimeHighlightState.WHOLE);
        return value;
    }
}
