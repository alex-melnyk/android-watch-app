package com.igorganapolsky.vibratingwatchapp;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.Vibrator;
import com.igorganapolsky.vibratingwatchapp.core.util.ViewModelFactory;
import com.igorganapolsky.vibratingwatchapp.domain.Repository;
import com.igorganapolsky.vibratingwatchapp.domain.WatchRepository;
import com.igorganapolsky.vibratingwatchapp.domain.local.TimersDatabase;
import com.igorganapolsky.vibratingwatchapp.domain.manager.timer.CountdownManager;
import com.igorganapolsky.vibratingwatchapp.domain.manager.timer.WatchCountdownManager;
import com.igorganapolsky.vibratingwatchapp.domain.manager.vibration.BeepManager;
import com.igorganapolsky.vibratingwatchapp.domain.manager.vibration.WatchBeepManager;

import java.util.concurrent.Executors;

public class VibratingWatchApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        /* simple self implemented DI */

        // step 1 - > create database instance;
        TimersDatabase database = Room.databaseBuilder(this, TimersDatabase.class, "TimersDatabase")
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build();


        // step 2 - > create repository;
        Repository repository = new WatchRepository(database);

        // step 3 -> create os vibrator wrapper
        BeepManager beepManager = new WatchBeepManager((Vibrator) getSystemService(Context.VIBRATOR_SERVICE));

        // step 4- > create countdown manager;
        CountdownManager countdownManager = new WatchCountdownManager(beepManager);

        // step 5 > create view model factory;
        ViewModelFactory.initFactory(repository, countdownManager);
    }
}
