package com.igorganapolsky.vibratingwatchapp.ui.settimer;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.wear.widget.WearableRecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.igorganapolsky.vibratingwatchapp.R;
import com.igorganapolsky.vibratingwatchapp.ui.util.RecyclerViewSnapLayoutManager;

public class SetRepeatFragment extends Fragment {

    private WearableRecyclerView wrvRepeats;

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
        View rootView = inflater.inflate(R.layout.set_repeat_fragment, container, false);

        wrvRepeats = rootView.findViewById(R.id.wrvRepeats);
        wrvRepeats.setAdapter(new RepeatsRecyclerViewAdapter());

        RecyclerViewSnapLayoutManager layoutManager = new RecyclerViewSnapLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL);
        layoutManager.setItemSelectListener((int pos) -> {
            TimerValue timerValue = mViewModel.getTimerValue().getValue();
            timerValue.setRepeat(pos);
        });
        wrvRepeats.setLayoutManager(layoutManager);

        return rootView;
    }

}
