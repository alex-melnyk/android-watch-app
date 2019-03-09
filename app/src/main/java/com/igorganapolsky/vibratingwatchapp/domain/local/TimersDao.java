package com.igorganapolsky.vibratingwatchapp.domain.local;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.*;
import com.igorganapolsky.vibratingwatchapp.domain.local.entity.TimerEntity;

import java.util.List;

@Dao
public interface TimersDao {

    @Query("SELECT * FROM TimerEntity")
    LiveData<List<TimerEntity>> getAll();

    @Query("SELECT * FROM TimerEntity WHERE id = :id")
    TimerEntity getById(int id);

    @Query("DELETE FROM TimerEntity WHERE id = :id")
    void deleteById(int id);

    @Insert
    void insert(TimerEntity task);

    @Delete
    void delete(TimerEntity task);

    @Update
    void update(TimerEntity task);
}
