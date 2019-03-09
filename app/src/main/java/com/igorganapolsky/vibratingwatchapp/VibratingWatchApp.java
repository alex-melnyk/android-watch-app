package com.igorganapolsky.vibratingwatchapp;

import android.app.Application;
import android.arch.persistence.room.Room;
import com.igorganapolsky.vibratingwatchapp.domain.Repository;
import com.igorganapolsky.vibratingwatchapp.domain.WatchRepository;
import com.igorganapolsky.vibratingwatchapp.domain.local.TimersDatabase;
import com.igorganapolsky.vibratingwatchapp.manager.CountdownManager;
import com.igorganapolsky.vibratingwatchapp.manager.WatchCountdownManager;
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
        Repository repository = new WatchRepository(database);

        // step 3 - > create countdown manager;
        CountdownManager countdownManager = new WatchCountdownManager();

        // step 3 - > create view model factory;
        ViewModelFactory.initFactory(repository, countdownManager);
    }
}
