package com.igorganapolsky.vibratingwatchapp.ui.settimer;

import android.arch.lifecycle.MutableLiveData;
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
import com.igorganapolsky.vibratingwatchapp.data.models.Timer;
import com.igorganapolsky.vibratingwatchapp.ui.adapters.RepeatsAdapter;
import com.igorganapolsky.vibratingwatchapp.ui.RecyclerViewSnapLayoutManager;
import com.igorganapolsky.vibratingwatchapp.ui.models.SetTimerViewModel;
import com.igorganapolsky.vibratingwatchapp.ui.models.TimerValue;

public class SetRepeatFragment extends Fragment {

    private Timer model;
    private WearableRecyclerView wrvRepeats;

    private SetTimerViewModel mViewModel;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this.getActivity()).get(SetTimerViewModel.class);
        // TODO: Use the ViewModel
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.set_repeat_fragment, container, false);

        wrvRepeats = rootView.findViewById(R.id.wrvRepeats);
        wrvRepeats.setAdapter(new RepeatsAdapter());

        RecyclerViewSnapLayoutManager layoutManager = new RecyclerViewSnapLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL);
        layoutManager.setItemSelectListener((int pos) -> mViewModel.getTimerValue().getValue().setRepeat(pos));

        wrvRepeats.setLayoutManager(layoutManager);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        model = (Timer) getArguments().getSerializable("TIMER_MODEL");

        if (model != null) {
            int repeats = model.getRepeat();

            MutableLiveData<TimerValue> liveData = mViewModel.getTimerValue();

            TimerValue value = liveData.getValue();
            value.setRepeat(repeats);

            wrvRepeats.smoothScrollToPosition(repeats);

            liveData.setValue(value);
        } else {
            wrvRepeats.smoothScrollToPosition(0);
        }
    }
}
