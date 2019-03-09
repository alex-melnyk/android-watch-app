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

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity()), ViewModelFactory.getInstance()).get(SetTimerViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.set_timer_vibration_fragment, container, false);
        WearableRecyclerView wrvVibrations = rootView.findViewById(R.id.wrvVibrations);

        VibrationsAdapter vibrationsAdapter = new VibrationsAdapter();
        wrvVibrations.setAdapter(vibrationsAdapter);

        RecyclerViewSnapLayoutManager layoutManager = new RecyclerViewSnapLayoutManager(getActivity());
        wrvVibrations.setLayoutManager(layoutManager);

        layoutManager.setItemSelectListener((int pos) -> mViewModel.setBuzz(pos));

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

//        model = (TimerEntity) getArguments().getSerializable("TIMER_MODEL");
//
//        if (model != null) {
//            int buzzMode = model.getBuzzMode();
//
//            MutableLiveData<TimerValue> liveData = mViewModel.getTimerData();
//
//            TimerValue value = liveData.getValue();
//            value.setBuzz(buzzMode);
//
//            wrvVibrations.smoothScrollToPosition(buzzMode);
//
//            liveData.setValue(value);
//        } else {
//            wrvVibrations.smoothScrollToPosition(0);
//        }
    }
}
