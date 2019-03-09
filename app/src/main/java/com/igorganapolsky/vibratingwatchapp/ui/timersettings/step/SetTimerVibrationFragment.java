package com.igorganapolsky.vibratingwatchapp.ui.timersettings.step;

import android.arch.lifecycle.MutableLiveData;
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
import com.igorganapolsky.vibratingwatchapp.data.models.Timer;
import com.igorganapolsky.vibratingwatchapp.ui.RecyclerViewSnapLayoutManager;
import com.igorganapolsky.vibratingwatchapp.ui.models.TimerValue;
import com.igorganapolsky.vibratingwatchapp.ui.timersettings.SetTimerViewModel;
import com.igorganapolsky.vibratingwatchapp.ui.timersettings.adapter.VibrationsAdapter;

public class SetTimerVibrationFragment extends Fragment {

    private Timer model;
    private View rootView;
    private WearableRecyclerView wrvVibrations;
    private VibrationsAdapter vibrationsAdapter;

    private SetTimerViewModel mViewModel;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(getActivity()).get(SetTimerViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.set_timer_vibration_fragment, container, false);

        vibrationsAdapter = new VibrationsAdapter();

        wrvVibrations = rootView.findViewById(R.id.wrvVibrations);
        wrvVibrations.setAdapter(vibrationsAdapter);

        RecyclerViewSnapLayoutManager layoutManager = new RecyclerViewSnapLayoutManager(getActivity());
        layoutManager.setItemSelectListener((int pos) -> mViewModel.getTimerValue().getValue().setBuzz(pos));

        wrvVibrations.setLayoutManager(layoutManager);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        model = (Timer) getArguments().getSerializable("TIMER_MODEL");

        if (model != null) {
            int buzzMode = model.getBuzzMode();

            MutableLiveData<TimerValue> liveData = mViewModel.getTimerValue();

            TimerValue value = liveData.getValue();
            value.setBuzz(buzzMode);

            wrvVibrations.smoothScrollToPosition(buzzMode);

            liveData.setValue(value);
        } else {
            wrvVibrations.smoothScrollToPosition(0);
        }
    }
}
