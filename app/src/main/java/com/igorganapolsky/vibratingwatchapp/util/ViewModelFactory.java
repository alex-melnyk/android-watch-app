package com.igorganapolsky.vibratingwatchapp.util;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;
import com.igorganapolsky.vibratingwatchapp.data.Repository;
import com.igorganapolsky.vibratingwatchapp.ui.timerlist.TimerListViewModel;
import com.igorganapolsky.vibratingwatchapp.ui.timersettings.SetTimerViewModel;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private Repository repository;
    private static ViewModelFactory factory;

    private ViewModelFactory(Repository repository) {
        this.repository = repository;
    }

    public static void initFactory(Repository repository) {
        if (factory == null) {
            factory = new ViewModelFactory(repository);
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
            viewModel = new TimerListViewModel(repository);
        } else {
            throw new IllegalStateException("No associated view model with " + modelClass.getName());
        }
        return (T) viewModel;
    }
}
