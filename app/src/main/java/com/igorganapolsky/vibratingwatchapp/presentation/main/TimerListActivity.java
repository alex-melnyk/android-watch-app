package com.igorganapolsky.vibratingwatchapp.presentation.main;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.igorganapolsky.vibratingwatchapp.R;
import com.igorganapolsky.vibratingwatchapp.core.util.ViewModelFactory;
import com.igorganapolsky.vibratingwatchapp.presentation.details.TimerDetailsActivity;
import com.igorganapolsky.vibratingwatchapp.presentation.main.adapter.TimerListAdapter;
import com.igorganapolsky.vibratingwatchapp.presentation.settings.SetTimerActivity;

public class TimerListActivity extends AppCompatActivity implements View.OnClickListener, TimerListAdapter.OnItemClickListener {

    private TimerListViewModel mViewModel;

    private TimerListAdapter timerListAdapter;
    private ImageView ivTimerListImage;
    private TextView addTimerButtonImageLabel;
    private RecyclerView wrvTimerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mViewModel = ViewModelProviders.of(this, ViewModelFactory.getInstance()).get(TimerListViewModel.class);

        setupView();
        setupObservers();
    }

    private void setupView() {
        View addTimerBtn = findViewById(R.id.addTimerButtonImage);
        addTimerBtn.setOnClickListener(this);

        ivTimerListImage = findViewById(R.id.ivTimerListImage);
        addTimerButtonImageLabel = findViewById(R.id.addTimerButtonImageLabel);
        wrvTimerList = findViewById(R.id.wrvTimerList);

        wrvTimerList.setLayoutManager(new LinearLayoutManager(this));
        timerListAdapter = new TimerListAdapter();
        timerListAdapter.setItemClickListener(this);
        wrvTimerList.setAdapter(timerListAdapter);
    }

    private void setupObservers() {
        mViewModel.getAllTimers().observe(this, (timerList) -> {
            timerListAdapter.setData(timerList);
            if (timerList != null && timerList.size() > 0) {
                ivTimerListImage.setVisibility(ImageView.GONE);
                addTimerButtonImageLabel.setVisibility(View.GONE);
                wrvTimerList.setVisibility(View.VISIBLE);
            } else {
                ivTimerListImage.setVisibility(ImageView.VISIBLE);
                addTimerButtonImageLabel.setVisibility(View.VISIBLE);
                wrvTimerList.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onClick(View view) {
        startActivity(new Intent(this, SetTimerActivity.class));
    }

    @Override
    public void onItemClick(int id) {
        startActivity(TimerDetailsActivity.createIntent(this, id));
    }
}
