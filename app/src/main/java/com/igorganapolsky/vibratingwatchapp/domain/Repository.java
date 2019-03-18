package com.igorganapolsky.vibratingwatchapp.domain;

import android.arch.lifecycle.LiveData;
import com.igorganapolsky.vibratingwatchapp.domain.model.TimerModel;

import java.util.List;

public interface Repository {

    /**
     * Gets particular timer from database by its id.
     *
     * @param timerId of timer in db;
     * @return timer or null, if no timer was found;
     */
    TimerModel getTimerById(int timerId);

    /**
     * Gets all timers from database.
     *
     * @return {@link LiveData} with list of timers;
     */
    LiveData<List<TimerModel>> getAll();

    /**
     * Updates particular timer in database.
     *
     * @param timer model, that represents timer entity;
     * @see com.igorganapolsky.vibratingwatchapp.domain.local.entity.TimerEntity
     */
    void updateTimer(TimerModel timer);

    /**
     * Stores new timer in database with replacing behaviour, if
     * any conflicts happens.
     *
     * @param timer model, that represents timer entity;
     * @see com.igorganapolsky.vibratingwatchapp.domain.local.entity.TimerEntity
     * @see android.arch.persistence.room.OnConflictStrategy#REPLACE
     */
    void saveTimer(TimerModel timer);

    /**
     * Deletes particular timer from database by its id.
     *
     * @param timerId of timer in db;
     */
    void deleteTimer(int timerId);

    /**
     * Updates particular timer in database by its id.
     *
     * @param timerId  of timer in db;
     * @param newState that need to update;
     */
    void updateTimerState(int timerId, TimerModel.State newState);
}
