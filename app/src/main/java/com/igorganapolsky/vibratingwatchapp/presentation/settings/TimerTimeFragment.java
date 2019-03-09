package com.igorganapolsky.vibratingwatchapp.presentation.settings;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.igorganapolsky.vibratingwatchapp.R;
import com.igorganapolsky.vibratingwatchapp.domain.model.TimeHighlightState;
import com.igorganapolsky.vibratingwatchapp.util.ViewModelFactory;

import java.util.Locale;
import java.util.Objects;

public class TimerTimeFragment extends Fragment {

    private SetTimerViewModel mViewModel;

    private int INACTIVE_COLOR;
    private int ACTIVE_COLOR;

    private TextView tvTimeHours;
    private TextView tvTimeMinutes;
    private TextView tvTimeSeconds;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.timer_time_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity()), ViewModelFactory.getInstance()).get(SetTimerViewModel.class);

        setupView(view);
        setupObservers();

    }

    private void setupObservers() {
        mViewModel.getTimerData().observe(Objects.requireNonNull(this.getActivity()), (timerValue) -> {
            tvTimeHours.setText(String.format(Locale.ENGLISH, "%02d", timerValue.getHours()));
            tvTimeMinutes.setText(String.format(Locale.ENGLISH, "%02d", timerValue.getMinutes()));
            tvTimeSeconds.setText(String.format(Locale.ENGLISH, "%02d", timerValue.getSeconds()));
        });

        mViewModel.getHighliteData().observe(Objects.requireNonNull(getActivity()), this::setSelection);
    }

    private void setSelection(TimeHighlightState state) {
        switch (state) {
            case WHOLE:
                tvTimeHours.setTextColor(ACTIVE_COLOR);
                tvTimeMinutes.setTextColor(ACTIVE_COLOR);
                tvTimeSeconds.setTextColor(ACTIVE_COLOR);
                break;
            case HOURS:
                tvTimeHours.setTextColor(ACTIVE_COLOR);
                tvTimeMinutes.setTextColor(INACTIVE_COLOR);
                tvTimeSeconds.setTextColor(INACTIVE_COLOR);
                break;
            case MINUTES:
                tvTimeHours.setTextColor(INACTIVE_COLOR);
                tvTimeMinutes.setTextColor(ACTIVE_COLOR);
                tvTimeSeconds.setTextColor(INACTIVE_COLOR);
                break;
            case SECONDS:
                tvTimeHours.setTextColor(INACTIVE_COLOR);
                tvTimeMinutes.setTextColor(INACTIVE_COLOR);
                tvTimeSeconds.setTextColor(ACTIVE_COLOR);
                break;
        }
    }

    private void setupView(View view) {
        ACTIVE_COLOR = ContextCompat.getColor(requireContext(), R.color.white_active);
        INACTIVE_COLOR = ContextCompat.getColor(requireContext(), R.color.white_inactive);

        tvTimeHours = view.findViewById(R.id.tvTimeHours);
        tvTimeMinutes = view.findViewById(R.id.tvTimeMinutes);
        tvTimeSeconds = view.findViewById(R.id.tvTimeSeconds);
    }
}

