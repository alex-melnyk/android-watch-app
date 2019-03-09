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
import com.igorganapolsky.vibratingwatchapp.data.models.Timer;
import com.igorganapolsky.vibratingwatchapp.ui.models.SetTimerViewModel;
import com.igorganapolsky.vibratingwatchapp.ui.models.TimeHighlightState;
import com.igorganapolsky.vibratingwatchapp.ui.models.TimerSetup;
import com.igorganapolsky.vibratingwatchapp.ui.models.TimerValue;
import com.igorganapolsky.vibratingwatchapp.ui.timersettings.SetTimerActivity;
import com.igorganapolsky.vibratingwatchapp.ui.timersettings.StepActionListener;
import com.igorganapolsky.vibratingwatchapp.util.TimerTransform;
import com.triggertrap.seekarc.SeekArc;

import java.util.Locale;

public class SetTimerTimeFragment extends Fragment implements View.OnClickListener, SeekArc.OnSeekArcChangeListener {

    private TimerSetup selection = TimerSetup.HOURS;

    private TextView tvLabel;
    private TextView tvLabelMeasure;

    private TextView tvHours;
    private TextView tvMinutes;
    private TextView tvSeconds;

    private SeekArc seekArc;

    private SetTimerViewModel mViewModel;
    private StepActionListener actionListener;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        actionListener = (SetTimerActivity) getActivity();

        mViewModel = ViewModelProviders.of(getActivity()).get(SetTimerViewModel.class);
        // TODO: Use the ViewModel

        mViewModel.getTimerValue().observe(getActivity(), (timerValue) -> {
            tvLabel.setText(String.format(Locale.ENGLISH, "%d", timerValue.getValue(selection)));
            tvLabelMeasure.setText(selection.getShortcut());
        });
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

        TextView tvTime = rootView.findViewById(R.id.tvTime);
        seekArc = rootView.findViewById(R.id.seekArc);

        seekArc.setOnSeekArcChangeListener(this);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        Timer model = (Timer) getArguments().getSerializable("TIMER_MODEL");

        if (model != null) {
            mViewModel.getTimerValue().setValue(TimerTransform.timerModelFromMillis(model.getMilliseconds()));
            setSelection(TimerSetup.HOURS);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvHours:
                setSelection(TimerSetup.HOURS);
                break;
            case R.id.tvMinutes:
                setSelection(TimerSetup.MINUTES);
                break;
            case R.id.tvSeconds:
                setSelection(TimerSetup.SECONDS);
                break;
        }
    }

    @Override
    public void onProgressChanged(SeekArc seekArc, int progress, boolean byUser) {
        if (byUser) {
            int calculatedValue = (int) (selection.getMeasure() / 100 * progress);

            TimerValue timerValue = mViewModel.getTimerValue().getValue();

            switch (selection) {
                case HOURS:
                    timerValue.setHours(calculatedValue);
                    break;
                case MINUTES:
                    timerValue.setMinutes(calculatedValue);
                    break;
                case SECONDS:
                    timerValue.setSeconds(calculatedValue);
                    break;
            }

            mViewModel.getTimerValue().setValue(timerValue);

//            tvLabel.setText(String.format(Locale.ENGLISH, "%d", (int) (selection.getMeasure() / 100 * progress)));
        }
    }

    @Override
    public void onStartTrackingTouch(SeekArc seekArc) {
        actionListener.onActionStart();

        TimerValue timerValue = mViewModel.getTimerValue().getValue();

        switch (selection) {
            case HOURS:
                timerValue.setState(TimeHighlightState.HOURS);
                break;
            case MINUTES:
                timerValue.setState(TimeHighlightState.MINUTES);
                break;
            case SECONDS:
                timerValue.setState(TimeHighlightState.SECONDS);
                break;
        }

        mViewModel.getTimerValue().setValue(timerValue);
    }

    @Override
    public void onStopTrackingTouch(SeekArc seekArc) {
        actionListener.onActionEnd();

        TimerValue timerValue = mViewModel.getTimerValue().getValue();
        timerValue.setState(TimeHighlightState.WHOLE);
        mViewModel.getTimerValue().setValue(timerValue);
    }

    private void setSelection(TimerSetup selection) {

        final int ACTIVE_COLOR = ContextCompat.getColor(requireContext(), R.color.white_active);
        final int INACTIVE_COLOR = ContextCompat.getColor(requireContext(), R.color.white_inactive);

        this.selection = selection;

        TimerValue timerValue = mViewModel.getTimerValue().getValue();
        seekArc.setProgress((int) ((double) timerValue.getValue(selection) / selection.getMeasure() * 100));

        tvHours.setTextColor(INACTIVE_COLOR);
        tvMinutes.setTextColor(INACTIVE_COLOR);
        tvSeconds.setTextColor(INACTIVE_COLOR);

        switch (selection) {
            case HOURS:
                tvHours.setTextColor(ACTIVE_COLOR);
                break;
            case MINUTES:
                tvMinutes.setTextColor(ACTIVE_COLOR);
                break;
            case SECONDS:
                tvSeconds.setTextColor(ACTIVE_COLOR);
                break;
        }

        mViewModel.getTimerValue().setValue(timerValue);
    }
}

