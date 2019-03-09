package com.igorganapolsky.vibratingwatchapp;

import android.app.Application;
import android.arch.persistence.room.Room;
import com.igorganapolsky.vibratingwatchapp.data.Repository;
import com.igorganapolsky.vibratingwatchapp.data.WatchAppRepository;
import com.igorganapolsky.vibratingwatchapp.data.db.TimersDatabase;
import com.igorganapolsky.vibratingwatchapp.util.ViewModelFactory;

public class VibratingWatchApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        /* simple self implemented DI */

        // step 1 - > create database instance;
        TimersDatabase database = Room.databaseBuilder(this, TimersDatabase.class, "TimersDatabase")
            .allowMainThreadQueries()
            .build();

        // step 2 - > create repository;
        Repository repository = new WatchAppRepository(database);

        // step 3 - > create view model factory;
        ViewModelFactory.initFactory(repository);
    }
}
