package com.igorganapolsky.vibratingwatchapp.core.util;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;
import com.igorganapolsky.vibratingwatchapp.domain.Repository;
import com.igorganapolsky.vibratingwatchapp.domain.manager.timer.CountdownManager;
import com.igorganapolsky.vibratingwatchapp.presentation.details.TimerDetailsViewModel;
import com.igorganapolsky.vibratingwatchapp.presentation.main.TimerListViewModel;
import com.igorganapolsky.vibratingwatchapp.presentation.settings.SetTimerViewModel;

/**
 * Common implementation of view model's factory, designed by Google.
 * Holds single instances of {@link com.igorganapolsky.vibratingwatchapp.domain.WatchRepository}
 * and {@link CountdownManager} and provides their in other vie models.
 */
public class ViewModelFactory implements ViewModelProvider.Factory {

    private static ViewModelFactory factory;

    private final Repository repository;
    private final CountdownManager countdownManager;

    private ViewModelFactory(Repository repository, CountdownManager countdownManager) {
        this.repository = repository;
        this.countdownManager = countdownManager;
    }

    /**
     * MUST CALL ONLY ONCE. Creates singleton of {@link ViewModelFactory} and
     * stores repository and countdown manager.
     *
     * @param repository with domain logic;
     * @param countdownManager with time's update logic;
     */
    public static void initFactory(Repository repository,
                                   CountdownManager countdownManager) {
        if (factory == null) {
            factory = new ViewModelFactory(repository, countdownManager);
        }
    }

    public static ViewModelProvider.Factory getInstance() {
        return factory;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        ViewModel viewModel;
        if (modelClass.isAssignableFrom(SetTimerViewModel.class)) {
            viewModel = new SetTimerViewModel(repository);
        } else if (modelClass.isAssignableFrom(TimerListViewModel.class)) {
            viewModel = new TimerListViewModel(repository, countdownManager);
        } else if (modelClass.isAssignableFrom(TimerDetailsViewModel.class)) {
            viewModel = new TimerDetailsViewModel(repository, countdownManager);
        } else {
            throw new IllegalStateException("No associated view model with " + modelClass.getName());
        }
        return (T) viewModel;
    }
}
