package com.igorganapolsky.vibratingwatchapp.data.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import com.igorganapolsky.vibratingwatchapp.data.dao.TimersDao;
import com.igorganapolsky.vibratingwatchapp.data.models.Timer;

@Database(entities = {Timer.class}, version = 1, exportSchema = true)
public abstract class TimersDatabase extends RoomDatabase {

    public abstract TimersDao timersDao();
}
