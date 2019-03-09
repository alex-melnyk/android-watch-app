package com.igorganapolsky.vibratingwatchapp.ui.timersettings.step;

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
import com.igorganapolsky.vibratingwatchapp.model.TimerSetup;
import com.igorganapolsky.vibratingwatchapp.ui.timersettings.SetTimerActivity;
import com.igorganapolsky.vibratingwatchapp.ui.timersettings.SetTimerViewModel;
import com.igorganapolsky.vibratingwatchapp.ui.timersettings.custom.StepActionListener;
import com.igorganapolsky.vibratingwatchapp.util.ViewModelFactory;
import com.triggertrap.seekarc.SeekArc;

import java.util.Locale;
import java.util.Objects;

import static com.igorganapolsky.vibratingwatchapp.data.models.Timer.TIMER_ID;

public class SetTimerTimeFragment extends Fragment implements View.OnClickListener, SeekArc.OnSeekArcChangeListener {

    private SetTimerViewModel mViewModel;
    private SeekArc seekArc;

    private TextView tvLabel;
    private TextView tvLabelMeasure;
    private TextView tvHours;
    private TextView tvMinutes;
    private TextView tvSeconds;

    private TimerSetup selection = TimerSetup.HOURS;
    private StepActionListener actionListener;

    final int ACTIVE_COLOR = ContextCompat.getColor(requireContext(), R.color.white_active);
    final int INACTIVE_COLOR = ContextCompat.getColor(requireContext(), R.color.white_inactive);

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        actionListener = (SetTimerActivity) getActivity();
        mViewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity()), ViewModelFactory.getInstance()).get(SetTimerViewModel.class);
        setupViewModel();

        setupObservers();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.set_timer_time_fragment, container, false);

        tvLabel = rootView.findViewById(R.id.tvLabel);
        tvLabelMeasure = rootView.findViewById(R.id.tvLabelMeasure);

        tvHours = rootView.findViewById(R.id.tvHours);
        tvHours.setOnClickListener(this);
        tvMinutes = rootView.findViewById(R.id.tvMinutes);
        tvMinutes.setOnClickListener(this);
        tvSeconds = rootView.findViewById(R.id.tvSeconds);
        tvSeconds.setOnClickListener(this);

        seekArc = rootView.findViewById(R.id.seekArc);
        seekArc.setOnSeekArcChangeListener(this);

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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
            int calculatedValue = (int) (selection.getMeasure() / 100 * progress);
            mViewModel.setTimeSelection(calculatedValue);
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
                tvLabel.setText(String.format(Locale.ENGLISH, "%d", timerValue.getValue(selection)));
                tvLabelMeasure.setText(selection.getShortcut());
            }
        });
    }

    private void setupViewModel() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            int currentId = bundle.getInt(TIMER_ID);
            mViewModel.setCurrentModelId(currentId);
        }
    }

    private void setSelection(TimerSetup selection) {
        seekArc.setProgress(mViewModel.calculateProgress());

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

