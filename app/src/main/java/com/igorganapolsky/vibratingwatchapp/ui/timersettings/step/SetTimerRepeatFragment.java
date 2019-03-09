package com.igorganapolsky.vibratingwatchapp.ui.timersettings.step;

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
import com.igorganapolsky.vibratingwatchapp.ui.RecyclerViewSnapLayoutManager;
import com.igorganapolsky.vibratingwatchapp.ui.timersettings.SetTimerViewModel;
import com.igorganapolsky.vibratingwatchapp.ui.timersettings.adapter.RepeatsAdapter;
import com.igorganapolsky.vibratingwatchapp.util.ViewModelFactory;

public class SetTimerRepeatFragment extends Fragment {

    private WearableRecyclerView wrvRepeats;
    private RecyclerViewSnapLayoutManager layoutManager;
    private SetTimerViewModel mViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = ViewModelProviders.of(getActivity(), ViewModelFactory.getInstance()).get(SetTimerViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.set_timer_repeat_fragment, container, false);
        setupView(rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupObservers();
    }

    private void setupView(View rootView) {
        wrvRepeats = rootView.findViewById(R.id.wrvRepeats);
        wrvRepeats.setAdapter(new RepeatsAdapter());

        layoutManager = new RecyclerViewSnapLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL);
        layoutManager.setItemSelectListener((int pos) -> mViewModel.setTimerRepeat(pos));

        wrvRepeats.setLayoutManager(layoutManager);
    }

    private void setupObservers() {
        mViewModel.getTimerData().observe(this, (timerValue) -> {
            layoutManager.selectFirst();
        });
    }
}
