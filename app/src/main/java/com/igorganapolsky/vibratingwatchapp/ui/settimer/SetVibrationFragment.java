package com.igorganapolsky.vibratingwatchapp.ui.settimer;

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
import com.igorganapolsky.vibratingwatchapp.ui.util.RecyclerViewSnapLayoutManager;

public class SetVibrationFragment extends Fragment {

    private View rootView;
    private WearableRecyclerView wrvVibrations;

    private SetTimerViewModel mViewModel;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(SetTimerViewModel.class);
        // TODO: Use the ViewModel
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.set_vibration_fragment, container, false);

        wrvVibrations = rootView.findViewById(R.id.wrvVibrations);
        wrvVibrations.setAdapter(new VibrationsRecyclerViewAdapter());

        RecyclerViewSnapLayoutManager layoutManager = new RecyclerViewSnapLayoutManager(getActivity());
        layoutManager.setItemSelectListener((int pos) -> {
            TimerValue timerValue = mViewModel.getTimerValue().getValue();
            timerValue.setBuzz(pos);
        });
        wrvVibrations.setLayoutManager(layoutManager);

        return rootView;
    }

    @Override
    public void onAttachFragment(Fragment childFragment) {
        super.onAttachFragment(childFragment);
    }
}
