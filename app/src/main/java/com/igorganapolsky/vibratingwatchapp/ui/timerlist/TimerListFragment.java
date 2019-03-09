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
import android.widget.ImageView;
import android.widget.TextView;
import com.igorganapolsky.vibratingwatchapp.R;
import com.igorganapolsky.vibratingwatchapp.data.models.Timer;
import com.igorganapolsky.vibratingwatchapp.ui.RecyclerViewSnapLayoutManager;
import com.igorganapolsky.vibratingwatchapp.ui.details.TimerDetailsActivity;
import com.igorganapolsky.vibratingwatchapp.ui.timerlist.adapter.TimerListAdapter;
import com.igorganapolsky.vibratingwatchapp.util.ViewModelFactory;

public class TimerListFragment extends Fragment implements TimerListAdapter.OnItemClickListener {

    private TimerListViewModel mViewModel;
    private TimerListAdapter timerListAdapter;

    private ImageView ivTimerListImage;
    private TextView addTimerButtonImageLabel;
    private View rootView;
    private WearableRecyclerView wrvTimerList;

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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = ViewModelProviders.of(getActivity(), ViewModelFactory.getInstance()).get(TimerListViewModel.class);

        setupView();
        setupObservers();
    }


    private void setupView() {
        ivTimerListImage = getActivity().findViewById(R.id.ivTimerListImage);
        addTimerButtonImageLabel = getActivity().findViewById(R.id.addTimerButtonImageLabel);
    }

    private void setupObservers() {
        mViewModel.getLiveData().observe(this, (timerList) -> {
            timerListAdapter.setData(timerList);

            if (timerList != null && timerList.size() > 0) {
                ivTimerListImage.setVisibility(ImageView.GONE);
                addTimerButtonImageLabel.setVisibility(View.GONE);
            } else {
                ivTimerListImage.setVisibility(ImageView.VISIBLE);
                addTimerButtonImageLabel.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void onItemClick(int position) {
        // TODO: MAKE TRANSITION TO DETAILS
        Intent timerDetailsIntent = new Intent(getContext(), TimerDetailsActivity.class);
        timerDetailsIntent.putExtra(Timer.TIMER_ID, position);
        startActivity(timerDetailsIntent);
    }
}
