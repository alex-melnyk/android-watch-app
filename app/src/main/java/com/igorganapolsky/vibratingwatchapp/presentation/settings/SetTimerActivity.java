package com.igorganapolsky.vibratingwatchapp.presentation.settings;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import com.igorganapolsky.vibratingwatchapp.R;
import com.igorganapolsky.vibratingwatchapp.domain.local.DatabaseClient;
import com.igorganapolsky.vibratingwatchapp.domain.local.TimersDao;
import com.igorganapolsky.vibratingwatchapp.domain.local.entity.TimerEntity;
import com.igorganapolsky.vibratingwatchapp.domain.model.TimerValue;
import com.igorganapolsky.vibratingwatchapp.custom.StepActionListener;
import com.igorganapolsky.vibratingwatchapp.custom.SwipeRestrictViewPager;
import com.igorganapolsky.vibratingwatchapp.util.TimerTransform;
import com.igorganapolsky.vibratingwatchapp.util.ViewModelFactory;

public class SetTimerActivity extends AppCompatActivity implements View.OnClickListener, StepActionListener {

    private final int SUCCESS_CODE = 101;

    private SetTimerViewModel mViewModel;

    private TimerEntity model;
    private SwipeRestrictViewPager vpWizard;
    private FrameLayout ivNextPage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_timer_activity);

        mViewModel = ViewModelProviders.of(this, ViewModelFactory.getInstance()).get(SetTimerViewModel.class);

        setupView();
        setupObservers();
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
    public void onAttachFragment(Fragment fragment) {
        model = (TimerEntity) getIntent().getSerializableExtra("TIMER_MODEL");

        Bundle bundle = new Bundle();
        bundle.putSerializable("TIMER_MODEL", model);
        fragment.setArguments(bundle);

        super.onAttachFragment(fragment);
    }

    @Override
    public void onClick(View view) {
        int currentPage = vpWizard.getCurrentItem();

        if (currentPage < 2) {
            vpWizard.setCurrentItem(currentPage + 1);
        } else {
            TimerValue timerValue = ViewModelProviders.of(this).get(SetTimerViewModel.class).getTimerData().getValue();

            TimerEntity timer = model;

            if (timer == null) {
                timer = new TimerEntity();
            }

            long milliseconds = TimerTransform.timeToMillis(timerValue.getHours(), timerValue.getMinutes(), timerValue.getSeconds());
            timer.setMilliseconds(milliseconds);
            timer.setBuzzMode(timerValue.getBuzz());
            timer.setRepeat(timerValue.getRepeat());

            TimersDao timersDao = DatabaseClient.getInstance(getApplicationContext()).getTimersDatabase().timersDao();

            if (model == null) {
                timersDao.insert(timer);
            } else {
                timersDao.update(timer);

                Intent resultIntent = new Intent();
                resultIntent.putExtra("TIMER_MODEL", timer);
                setResult(SUCCESS_CODE, resultIntent);
            }

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
