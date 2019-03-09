package com.igorganapolsky.vibratingwatchapp.presentation.settings.step;

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
import com.igorganapolsky.vibratingwatchapp.custom.StepActionListener;
import com.igorganapolsky.vibratingwatchapp.domain.model.TimerSetup;
import com.igorganapolsky.vibratingwatchapp.presentation.settings.SetTimerActivity;
import com.igorganapolsky.vibratingwatchapp.presentation.settings.SetTimerViewModel;
import com.igorganapolsky.vibratingwatchapp.util.ViewModelFactory;
import com.triggertrap.seekarc.SeekArc;

import java.util.Locale;
import java.util.Objects;

public class SetTimerTimeFragment extends Fragment implements View.OnClickListener, SeekArc.OnSeekArcChangeListener {

    private SetTimerViewModel mViewModel;
    private SeekArc seekArc;

    private int ACTIVE_COLOR;
    private int INACTIVE_COLOR;

    private TextView tvLabel;
    private TextView tvLabelMeasure;
    private TextView tvHours;
    private TextView tvMinutes;
    private TextView tvSeconds;

    private StepActionListener actionListener;


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        actionListener = (SetTimerActivity) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.set_timer_time_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity()), ViewModelFactory.getInstance()).get(SetTimerViewModel.class);

        setupView(view);
        setupObservers();
    }

    private void setupView(View view) {

        ACTIVE_COLOR = ContextCompat.getColor(requireContext(), R.color.white_active);
        INACTIVE_COLOR = ContextCompat.getColor(requireContext(), R.color.white_inactive);

        tvLabel = view.findViewById(R.id.tvLabel);
        tvLabelMeasure = view.findViewById(R.id.tvLabelMeasure);

        tvHours = view.findViewById(R.id.tvHours);
        tvHours.setOnClickListener(this);
        tvMinutes = view.findViewById(R.id.tvMinutes);
        tvMinutes.setOnClickListener(this);
        tvSeconds = view.findViewById(R.id.tvSeconds);
        tvSeconds.setOnClickListener(this);

        seekArc = view.findViewById(R.id.seekArc);
        seekArc.setOnSeekArcChangeListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvHours:
                mViewModel.setSelection(TimerSetup.HOURS);
                break;
            case R.id.tvMinutes:
                mViewModel.setSelection(TimerSetup.MINUTES);
                break;
            case R.id.tvSeconds:
                mViewModel.setSelection(TimerSetup.SECONDS);
                break;
        }
    }

    @Override
    public void onProgressChanged(SeekArc seekArc, int progress, boolean byUser) {
        if (byUser) {
            mViewModel.setTimeSelection(progress);
        }
    }

    @Override
    public void onStartTrackingTouch(SeekArc seekArc) {
        actionListener.onActionStart();
        mViewModel.setHighlightState(false);
    }

    @Override
    public void onStopTrackingTouch(SeekArc seekArc) {
        actionListener.onActionEnd();
        mViewModel.setHighlightState(true);
    }

    private void setupObservers() {
        mViewModel.getSetupData().observe(Objects.requireNonNull(getActivity()), this::setSelection);
        mViewModel.getTimerData().observe(Objects.requireNonNull(getActivity()), (timerValue) -> {
            if (timerValue != null) {
                setSelection(TimerSetup.HOURS);
            }
        });
    }

    private void setSelection(TimerSetup selection) {
        seekArc.setProgress(mViewModel.calculateProgress());

        tvLabel.setText(String.format(Locale.ENGLISH, "%d", mViewModel.getCurrentTimeValue()));
        tvLabelMeasure.setText(selection.getShortcut());

        switch (selection) {
            case HOURS:
                tvHours.setTextColor(ACTIVE_COLOR);
                tvMinutes.setTextColor(INACTIVE_COLOR);
                tvSeconds.setTextColor(INACTIVE_COLOR);
                break;
            case MINUTES:
                tvMinutes.setTextColor(ACTIVE_COLOR);
                tvHours.setTextColor(INACTIVE_COLOR);
                tvSeconds.setTextColor(INACTIVE_COLOR);
                break;
            case SECONDS:
                tvSeconds.setTextColor(ACTIVE_COLOR);
                tvHours.setTextColor(INACTIVE_COLOR);
                tvMinutes.setTextColor(INACTIVE_COLOR);
                break;
        }
    }
}

