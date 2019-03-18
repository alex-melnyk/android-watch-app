package com.igorganapolsky.vibratingwatchapp.presentation.settings.step;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.igorganapolsky.vibratingwatchapp.R;
import com.igorganapolsky.vibratingwatchapp.core.custom.RecyclerViewSnapLayoutManager;
import com.igorganapolsky.vibratingwatchapp.core.util.ViewModelFactory;
import com.igorganapolsky.vibratingwatchapp.domain.model.BuzzSetup;
import com.igorganapolsky.vibratingwatchapp.presentation.settings.SetTimerViewModel;
import com.igorganapolsky.vibratingwatchapp.presentation.settings.adapter.HolderClickListener;
import com.igorganapolsky.vibratingwatchapp.presentation.settings.adapter.VibrationsAdapter;

import java.util.Objects;

public class SetTimerVibrationFragment extends Fragment implements HolderClickListener {

    private SetTimerViewModel mViewModel;
    private RecyclerView wrvVibrations;
    private VibrationsAdapter vibrationsAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_set_vibration, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity()),
            ViewModelFactory.getInstance()).get(SetTimerViewModel.class);

        setupView(view);
        setupObservers();
    }

    private void setupView(View view) {
        wrvVibrations = view.findViewById(R.id.wrvVibrations);

        RecyclerViewSnapLayoutManager layoutManager = new RecyclerViewSnapLayoutManager(getActivity());
        layoutManager.setItemSelectListener((int pos) -> mViewModel.setBuzz(vibrationsAdapter.getBuzzByPosition(pos)));

        vibrationsAdapter = new VibrationsAdapter(this,
            getResources().getStringArray(R.array.buzzes),
            getResources().getStringArray(R.array.times));

        wrvVibrations.setAdapter(vibrationsAdapter);
        wrvVibrations.setLayoutManager(layoutManager);
        wrvVibrations.setHasFixedSize(true);
    }

    private void setupObservers() {
        mViewModel.getTimerData().observe(this, (timer) -> {
            if (timer == null) return;
            BuzzSetup setup = timer.getBuzzSetup();
            vibrationsAdapter.selectBuzz(timer.getBuzzSetup());
            wrvVibrations.scrollToPosition(vibrationsAdapter.getPosition(setup));
        });
    }

    @Override
    public void onHolderClick(int position) {
        wrvVibrations.smoothScrollToPosition(position);
    }
}
