package com.igorganapolsky.vibratingwatchapp.ui.timerlist;

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
import android.widget.Toast;
import com.igorganapolsky.vibratingwatchapp.R;
import com.igorganapolsky.vibratingwatchapp.TimerDetailsActivity;
import com.igorganapolsky.vibratingwatchapp.model.TimerModel;
import com.igorganapolsky.vibratingwatchapp.ui.util.RecyclerViewSnapLayoutManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TimerListFragment extends Fragment implements TimerListAdapter.OnItemClickListener {

    private List<TimerModel> timerList;
    private TimerListViewModel mViewModel;
    private TimerListAdapter timerListAdapter;

    private View rootView;
    private WearableRecyclerView wrvTimerList;

    public static TimerListFragment newInstance() {
        return new TimerListFragment();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(TimerListViewModel.class);
        // TODO: Use the ViewModel

        timerList = new ArrayList<>(Arrays.asList(
                new TimerModel(1, 20, 0, 1, 1),
                new TimerModel(0, 45, 0, 2, 2),
                new TimerModel(0, 30, 0, 3, 7),
                new TimerModel(2, 10, 0, 4, 4),
                new TimerModel(0, 15, 0, 5, 9)
        ));

        mViewModel.getLiveData().observe(getActivity(), (liveData) -> timerListAdapter.setData(liveData));
        mViewModel.getLiveData().setValue(timerList);
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
    public void onItemClick(int position) {
        TimerModel timerModel = timerList.get(position);

        long timerTime = timerModel.getSeconds() * 1000 + timerModel.getMinutes() * 1000 * 60 + timerModel.getHours() * 1000 * 60 * 60;

        Toast.makeText(getContext(), position + " TIME: " + timerTime, Toast.LENGTH_SHORT).show();

        // TODO: MAKE TRANSITION TO DETAILS
        Intent timerDetailsIntent = new Intent(getContext(), TimerDetailsActivity.class);
        timerDetailsIntent.putExtra("TIMER_TIME", timerTime);
        startActivity(timerDetailsIntent);
    }
}
