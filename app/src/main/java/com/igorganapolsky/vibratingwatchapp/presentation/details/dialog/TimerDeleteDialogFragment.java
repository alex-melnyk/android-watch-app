package com.igorganapolsky.vibratingwatchapp.presentation.details.dialog;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.igorganapolsky.vibratingwatchapp.R;
import com.igorganapolsky.vibratingwatchapp.presentation.details.TimerDetailsViewModel;
import com.igorganapolsky.vibratingwatchapp.core.util.ViewModelFactory;

import java.util.Objects;

public class TimerDeleteDialogFragment extends Fragment implements View.OnClickListener {

    private TimerDetailsViewModel mViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_timer_delete, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity()), ViewModelFactory.getInstance()).get(TimerDetailsViewModel.class);

        view.findViewById(R.id.ivCancel).setOnClickListener(this);
        view.findViewById(R.id.ivApprove).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivApprove:
                mViewModel.deleteTimer();
                getActivity().finish();
                break;
            case R.id.ivCancel:
                getFragmentManager().popBackStack();
                break;
        }
    }
}
