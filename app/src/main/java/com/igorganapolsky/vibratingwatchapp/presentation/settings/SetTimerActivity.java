package com.igorganapolsky.vibratingwatchapp.presentation.settings;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import com.igorganapolsky.vibratingwatchapp.R;
import com.igorganapolsky.vibratingwatchapp.custom.StepActionListener;
import com.igorganapolsky.vibratingwatchapp.custom.SwipeRestrictViewPager;
import com.igorganapolsky.vibratingwatchapp.util.ViewModelFactory;

import static com.igorganapolsky.vibratingwatchapp.domain.local.entity.TimerEntity.TIMER_ID;

public class SetTimerActivity extends AppCompatActivity implements View.OnClickListener, StepActionListener {

    private SetTimerViewModel mViewModel;

    private SwipeRestrictViewPager vpWizard;
    private FrameLayout ivNextPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_timer_activity);
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
        vpWizard = findViewById(R.id.vpWizard);
        ivNextPage = findViewById(R.id.ivNextPage);
        TabLayout tlDots = findViewById(R.id.tlDots);

        vpWizard.setAdapter(new SetTimerPageAdapter(getSupportFragmentManager()));
        ivNextPage.setOnClickListener(this);

        tlDots.setupWithViewPager(vpWizard, true);
        disableTabs(tlDots);
    }

    private void setupObservers() {
        mViewModel.getTimerData().observe(this, (timerValue) -> {
            if (timerValue == null) return;
            boolean isSwipeRestrict = !timerValue.isDefaultTime();
            swapNextStepProceedActionState(isSwipeRestrict);
        });
    }

    @Override
    public void onClick(View view) {
        int currentPage = vpWizard.getCurrentItem();
        if (currentPage < 2) {
            vpWizard.setCurrentItem(currentPage + 1);
        } else {
            mViewModel.saveTimer();
            finish();
        }
    }

    @Override
    public void onActionStart() {
        swapNextStepProceedActionState(false);
    }

    @Override
    public void onActionEnd() {
        swapNextStepProceedActionState(true);
    }

    private void swapNextStepProceedActionState(boolean isEnable) {
        vpWizard.setIsSwipeAvailable(isEnable);
        ivNextPage.setEnabled(isEnable);
    }

    private void disableTabs(TabLayout layout) {
        LinearLayout tabStrip = ((LinearLayout) layout.getChildAt(0));
        for (int i = 0; i < tabStrip.getChildCount(); i++) {
            tabStrip.getChildAt(i).setOnTouchListener((v, event) -> true);
        }
    }
}