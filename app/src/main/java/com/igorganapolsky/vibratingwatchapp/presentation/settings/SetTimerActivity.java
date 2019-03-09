package com.igorganapolsky.vibratingwatchapp.presentation.settings;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.igorganapolsky.vibratingwatchapp.R;
import com.igorganapolsky.vibratingwatchapp.custom.StepActionListener;
import com.igorganapolsky.vibratingwatchapp.custom.SwipeRestrictViewPager;
import com.igorganapolsky.vibratingwatchapp.domain.model.TimeHighlightState;
import com.igorganapolsky.vibratingwatchapp.domain.model.TimerModel;
import com.igorganapolsky.vibratingwatchapp.util.ViewModelFactory;

import java.util.Locale;

import static com.igorganapolsky.vibratingwatchapp.domain.local.entity.TimerEntity.TIMER_ID;

public class SetTimerActivity extends AppCompatActivity implements View.OnClickListener, StepActionListener {

    private SetTimerViewModel mViewModel;

    private SwipeRestrictViewPager vpWizard;
    private AppCompatImageView ivNextPage;

    private TextView tvTimeHours;
    private TextView tvTimeMinutes;
    private TextView tvTimeSeconds;

    private int inactiveColor = 0;
    private int activeColor = 0;

    private boolean isSwipeGranted = false;
    private boolean isProgressChanged = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_timer);
        mViewModel = ViewModelProviders.of(this, ViewModelFactory.getInstance()).get(SetTimerViewModel.class);

        setupViewModel();
        setupView();
        setupObservers();
    }

    private void setupViewModel() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            int currentId = bundle.getInt(TIMER_ID);
            mViewModel.setCurrentModelId(currentId);
        }
    }

    private void setupView() {
        activeColor = ContextCompat.getColor(this, R.color.white_active);
        inactiveColor = ContextCompat.getColor(this, R.color.white_inactive);

        vpWizard = findViewById(R.id.vpWizard);
        ivNextPage = findViewById(R.id.ivNextPage);

        tvTimeHours = findViewById(R.id.tvTimeHours);
        tvTimeMinutes = findViewById(R.id.tvTimeMinutes);
        tvTimeSeconds = findViewById(R.id.tvTimeSeconds);

        TabLayout tlDots = findViewById(R.id.tlDots);

        vpWizard.setAdapter(new SetTimerPageAdapter(getSupportFragmentManager()));
        vpWizard.setOffscreenPageLimit(2);
        ivNextPage.setOnClickListener(this);

        tlDots.setupWithViewPager(vpWizard, true);
        disableTabs(tlDots);
    }

    private void setupObservers() {
        mViewModel.getHighligtData().observe(this, this::setHighlight);
        mViewModel.getTimerData().observe(this, (timer) -> {
            if (timer == null) return;
            isSwipeGranted = !timer.isDefaultTime();
            updateTimerData(timer);
        });
    }

    private void updateTimerData(TimerModel timer) {
        tvTimeHours.setText(String.format(Locale.ENGLISH, "%02d", timer.getHours()));
        tvTimeMinutes.setText(String.format(Locale.ENGLISH, "%02d", timer.getMinutes()));
        tvTimeSeconds.setText(String.format(Locale.ENGLISH, "%02d", timer.getSeconds()));
    }

    @Override
    public void onClick(View view) {
        if (isProgressChanged && isSwipeGranted) {
            int currentPage = vpWizard.getCurrentItem();
            if (currentPage < 2) {
                vpWizard.setCurrentItem(currentPage + 1);
            } else {
                mViewModel.saveTimer();
                finish();
            }
        }
    }

    @Override
    public void onActionStart() {
        isProgressChanged = false;
        vpWizard.setIsSwipeAvailable(false);
    }

    @Override
    public void onActionEnd() {
        isProgressChanged = true;
        vpWizard.setIsSwipeAvailable(isSwipeGranted);
    }

    private void disableTabs(TabLayout layout) {
        LinearLayout tabStrip = ((LinearLayout) layout.getChildAt(0));
        for (int i = 0; i < tabStrip.getChildCount(); i++) {
            tabStrip.getChildAt(i).setOnTouchListener((v, event) -> true);
        }
    }

    private void setHighlight(TimeHighlightState state) {
        switch (state) {
            case WHOLE:
                tvTimeHours.setTextColor(activeColor);
                tvTimeMinutes.setTextColor(activeColor);
                tvTimeSeconds.setTextColor(activeColor);
                break;
            case HOURS:
                tvTimeHours.setTextColor(activeColor);
                tvTimeMinutes.setTextColor(inactiveColor);
                tvTimeSeconds.setTextColor(inactiveColor);
                break;
            case MINUTES:
                tvTimeHours.setTextColor(inactiveColor);
                tvTimeMinutes.setTextColor(activeColor);
                tvTimeSeconds.setTextColor(inactiveColor);
                break;
            case SECONDS:
                tvTimeHours.setTextColor(inactiveColor);
                tvTimeMinutes.setTextColor(inactiveColor);
                tvTimeSeconds.setTextColor(activeColor);
                break;
        }
    }
}
