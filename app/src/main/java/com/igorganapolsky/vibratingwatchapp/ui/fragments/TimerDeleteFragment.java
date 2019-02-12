package com.igorganapolsky.vibratingwatchapp.ui.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.igorganapolsky.vibratingwatchapp.R;
import com.igorganapolsky.vibratingwatchapp.data.DatabaseClient;
import com.igorganapolsky.vibratingwatchapp.data.models.Timer;

public class TimerDeleteFragment extends Fragment implements View.OnClickListener {

    private Timer model;
    private View rootView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.timer_delete_fragment, container, false);
        rootView.findViewById(R.id.ivCancel).setOnClickListener(this);
        rootView.findViewById(R.id.ivApprove).setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        model = (Timer) getArguments().getSerializable("TIMER_MODEL");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivApprove:

                DatabaseClient.getInstance(getContext())
                        .getTimersDatabase()
                        .timersDao()
                        .delete(model);

                getActivity().finish();
                break;
            case R.id.ivCancel:
                getFragmentManager().popBackStack();
                break;
        }
    }
}
