package com.igorganapolsky.vibratingwatchapp.ui.edittimer;

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
import com.igorganapolsky.vibratingwatchapp.ui.edittimer.adapter.SetTimerAdapter;
import com.igorganapolsky.vibratingwatchapp.ui.models.SetTimerViewModel;
import com.igorganapolsky.vibratingwatchapp.ui.models.TimerValue;
import com.igorganapolsky.vibratingwatchapp.util.TimerTransform;

public class SetTimerActivity extends AppCompatActivity implements View.OnClickListener, StepActionListener {

    private final int SUCCESS_CODE = 101;

    private Timer model;
    private SwipeRestrictViewPager vpWizard;
    private TabLayout tlDots;
    private FrameLayout ivNextPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_timer_activity);

        vpWizard = findViewById(R.id.vpWizard);
        tlDots = findViewById(R.id.tlDots);
        ivNextPage = findViewById(R.id.ivNextPage);

        vpWizard.setAdapter(new SetTimerAdapter(getSupportFragmentManager()));
        tlDots.setupWithViewPager(vpWizard, true);
        ivNextPage.setOnClickListener(this);

        disableTabs(tlDots);
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
        vpWizard.setIsSwipeAvailable(false);
        ivNextPage.setEnabled(false);
    }

    @Override
    public void onActionEnd() {
//        vpWizard.setIsSwipeAvailable(true);
//        ivNextPage.setEnabled(true);
    }

    private void disableTabs(TabLayout layout) {
        LinearLayout tabStrip = ((LinearLayout) layout.getChildAt(0));
        for (int i = 0; i < tabStrip.getChildCount(); i++) {
            tabStrip.getChildAt(i).setOnTouchListener((v, event) -> true);
        }
    }
}
