package com.igorganapolsky.vibratingwatchapp.ui.timersettings;

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
import com.igorganapolsky.vibratingwatchapp.data.DatabaseClient;
import com.igorganapolsky.vibratingwatchapp.data.dao.TimersDao;
import com.igorganapolsky.vibratingwatchapp.data.models.Timer;
import com.igorganapolsky.vibratingwatchapp.ui.models.TimerValue;
import com.igorganapolsky.vibratingwatchapp.ui.timersettings.adapter.SetTimerAdapter;
import com.igorganapolsky.vibratingwatchapp.util.TimerTransform;

public class SetTimerActivity extends AppCompatActivity implements View.OnClickListener, StepActionListener {

    private final int SUCCESS_CODE = 101;

    private SetTimerViewModel mViewModel;

    private Timer model;
    private SwipeRestrictViewPager vpWizard;
    private FrameLayout ivNextPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_timer_activity);

        mViewModel = ViewModelProviders.of(this).get(SetTimerViewModel.class);

        setupView();
        setupObservers();
    }

    private void setupView() {
        vpWizard = findViewById(R.id.vpWizard);
        ivNextPage = findViewById(R.id.ivNextPage);
        TabLayout tlDots = findViewById(R.id.tlDots);

        vpWizard.setAdapter(new SetTimerAdapter(getSupportFragmentManager()));
        ivNextPage.setOnClickListener(this);

        tlDots.setupWithViewPager(vpWizard, true);
        disableTabs(tlDots);
    }

    private void setupObservers() {
        mViewModel.getTimerValue().observe(this, (timerValue) -> {
            if (timerValue == null) return;
            boolean isSwipeRestrict = !timerValue.isDefaultTime();
            swapNextStepProceedActionState(isSwipeRestrict);
        });
    }


    @Override
    public void onAttachFragment(Fragment fragment) {
        model = (Timer) getIntent().getSerializableExtra("TIMER_MODEL");

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
            TimerValue timerValue = ViewModelProviders.of(this).get(SetTimerViewModel.class).getTimerValue().getValue();

            Timer timer = model;

            if (timer == null) {
                timer = new Timer();
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
