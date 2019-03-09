package com.igorganapolsky.vibratingwatchapp.presentation.settings.step;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.wear.widget.WearableRecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.igorganapolsky.vibratingwatchapp.R;
import com.igorganapolsky.vibratingwatchapp.custom.RecyclerViewSnapLayoutManager;
import com.igorganapolsky.vibratingwatchapp.presentation.settings.SetTimerViewModel;
import com.igorganapolsky.vibratingwatchapp.presentation.settings.adapter.VibrationsAdapter;
import com.igorganapolsky.vibratingwatchapp.util.ViewModelFactory;

import java.util.Objects;

public class SetTimerVibrationFragment extends Fragment {

    private SetTimerViewModel mViewModel;
    private WearableRecyclerView wrvVibrations;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.set_timer_vibration_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity()), ViewModelFactory.getInstance()).get(SetTimerViewModel.class);

        setupView(view);
        setupObservers();
    }

    private void setupView(View view) {
        wrvVibrations = view.findViewById(R.id.wrvVibrations);

        VibrationsAdapter vibrationsAdapter = new VibrationsAdapter();
        wrvVibrations.setAdapter(vibrationsAdapter);

        RecyclerViewSnapLayoutManager layoutManager = new RecyclerViewSnapLayoutManager(getActivity());
        wrvVibrations.setLayoutManager(layoutManager);

        layoutManager.setItemSelectListener((int pos) -> mViewModel.setBuzz(pos));
    }

    private void setupObservers() {
        mViewModel.getTimerData().observe(this, (timerValue) -> {
            if (timerValue == null) return;
            wrvVibrations.smoothScrollToPosition(timerValue.getBuzz());
        });
    }
}
