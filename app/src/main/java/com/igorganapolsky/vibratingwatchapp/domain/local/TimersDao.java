package com.igorganapolsky.vibratingwatchapp.domain.local;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.*;
import com.igorganapolsky.vibratingwatchapp.domain.local.entity.TimerEntity;

import java.util.List;

@Dao
public interface TimersDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(TimerEntity entity);

    @Update
    void update(TimerEntity entity);

    @Query("SELECT * FROM timers")
    LiveData<List<TimerEntity>> getAll();

    @Query("SELECT * FROM timers WHERE id = :id")
    TimerEntity getById(int id);

    @Query("DELETE FROM timers WHERE id = :id")
    void deleteById(int id);

    @Query("UPDATE timers SET state =:timerState")
    void disableAll(String timerState);

    @Query("UPDATE timers SET state =:timerState WHERE id =:timerId")
    void updateTimerState(int timerId, String timerState);
}
