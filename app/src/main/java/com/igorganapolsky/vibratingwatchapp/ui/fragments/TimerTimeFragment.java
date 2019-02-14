package com.igorganapolsky.vibratingwatchapp.ui.fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.content.res.TypedArray;
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
import com.igorganapolsky.vibratingwatchapp.ui.models.SetTimerViewModel;

import java.util.Locale;

import static android.support.v4.content.ContextCompat.getColor;

public class TimerTimeFragment extends Fragment {

    private TextView tvTimeHours;
    private TextView tvTimeMinutes;
    private TextView tvTimeSeconds;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final int ACTIVE_COLOR = getResources().getColor(R.color.white_active);
        final int INACTIVE_COLOR = getResources().getColor(R.color.white_inactive);

        SetTimerViewModel mViewModel = ViewModelProviders.of(getActivity()).get(SetTimerViewModel.class);

        mViewModel.getTimerValue().observe(this.getActivity(), (timerValue) -> {
            tvTimeHours.setText(String.format(Locale.ENGLISH, "%02d", timerValue.getHours()));
            tvTimeMinutes.setText(String.format(Locale.ENGLISH, "%02d", timerValue.getMinutes()));
            tvTimeSeconds.setText(String.format(Locale.ENGLISH, "%02d", timerValue.getSeconds()));

            switch (timerValue.getState()) {
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
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.timer_time_fragment, container, false);

        tvTimeHours = rootView.findViewById(R.id.tvTimeHours);
        tvTimeMinutes = rootView.findViewById(R.id.tvTimeMinutes);
        tvTimeSeconds = rootView.findViewById(R.id.tvTimeSeconds);

        return rootView;
    }
}

