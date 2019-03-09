package com.igorganapolsky.vibratingwatchapp.presentation.details.dialog;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.igorganapolsky.vibratingwatchapp.R;
import com.igorganapolsky.vibratingwatchapp.domain.local.DatabaseClient;
import com.igorganapolsky.vibratingwatchapp.domain.local.entity.TimerEntity;

public class TimerDeleteDialogFragment extends Fragment implements View.OnClickListener {

    private TimerEntity model;
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
        model = (TimerEntity) getArguments().getSerializable("TIMER_MODEL");
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
