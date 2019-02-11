package com.igorganapolsky.vibratingwatchapp.ui.fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.wear.widget.WearableRecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.igorganapolsky.vibratingwatchapp.R;
import com.igorganapolsky.vibratingwatchapp.TimerDetailsActivity;
import com.igorganapolsky.vibratingwatchapp.data.DatabaseClient;
import com.igorganapolsky.vibratingwatchapp.data.models.Timer;
import com.igorganapolsky.vibratingwatchapp.ui.adapters.TimerListAdapter;
import com.igorganapolsky.vibratingwatchapp.ui.RecyclerViewSnapLayoutManager;
import com.igorganapolsky.vibratingwatchapp.ui.models.TimerListViewModel;

import java.util.List;

public class TimerListFragment extends Fragment implements TimerListAdapter.OnItemClickListener {

    private TimerListViewModel mViewModel;
    private TimerListAdapter timerListAdapter;

    private ImageView ivTimerListImage;
    private TextView addTimerButtonImageLabel;
    private View rootView;
    private WearableRecyclerView wrvTimerList;

    public static TimerListFragment newInstance() {
        return new TimerListFragment();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(getActivity()).get(TimerListViewModel.class);
        // TODO: Use the ViewModel

        ivTimerListImage = getActivity().findViewById(R.id.ivTimerListImage);
        addTimerButtonImageLabel = getActivity().findViewById(R.id.addTimerButtonImageLabel);

        mViewModel.getLiveData().observe(getActivity(), (liveData) -> {
            timerListAdapter.setData(liveData);

            if (liveData != null && liveData.size() > 0) {
                ivTimerListImage.setVisibility(ImageView.GONE);
                addTimerButtonImageLabel.setVisibility(View.GONE);
            } else {
                ivTimerListImage.setVisibility(ImageView.VISIBLE);
                addTimerButtonImageLabel.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.timer_list_fragment, container, false);

        wrvTimerList = rootView.findViewById(R.id.wrvTimerList);
        wrvTimerList.setLayoutManager(new RecyclerViewSnapLayoutManager(getContext()));

        timerListAdapter = new TimerListAdapter();
        timerListAdapter.setItemClickListener(this);
        wrvTimerList.setAdapter(timerListAdapter);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        List<Timer> timerList = DatabaseClient.getInstance(getContext())
                .getTimersDatabase()
                .timersDao()
                .getAll();

        mViewModel.getLiveData().setValue(timerList);
    }

    @Override
    public void onItemClick(int position) {
        Timer model = mViewModel.getLiveData().getValue().get(position);

        // TODO: MAKE TRANSITION TO DETAILS
        Intent timerDetailsIntent = new Intent(getContext(), TimerDetailsActivity.class);
        timerDetailsIntent.putExtra("TIMER_MODEL", model);
        startActivity(timerDetailsIntent);
    }
}
