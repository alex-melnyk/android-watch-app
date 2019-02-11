package com.igorganapolsky.vibratingwatchapp;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import com.igorganapolsky.vibratingwatchapp.data.DatabaseClient;
import com.igorganapolsky.vibratingwatchapp.data.models.Timer;
import com.igorganapolsky.vibratingwatchapp.ui.adapters.SetTimerAdapter;
import com.igorganapolsky.vibratingwatchapp.ui.models.SetTimerViewModel;
import com.igorganapolsky.vibratingwatchapp.ui.models.TimerValue;
import com.igorganapolsky.vibratingwatchapp.util.TimerTransform;

public class SetTimerActivity extends AppCompatActivity implements View.OnClickListener {

    private ViewPager vpWizard;
    private TabLayout tlDots;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_timer_activity);

        vpWizard = findViewById(R.id.vpWizard);
        vpWizard.setAdapter(new SetTimerAdapter(getSupportFragmentManager()));

        tlDots = findViewById(R.id.tlDots);
        tlDots.setupWithViewPager(vpWizard, true);

        findViewById(R.id.ivNextPage).setOnClickListener(this);
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("TIMER_MODEL", getIntent().getSerializableExtra("TIMER_MODEL"));

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

            Timer timer = new Timer();
            timer.setMilliseconds(TimerTransform.timeToMillis(timerValue.getHours(), timerValue.getMinutes(), timerValue.getSeconds()));
            timer.setBuzzMode(timerValue.getBuzz());
            timer.setRepeat(timerValue.getRepeat());

            DatabaseClient.getInstance(getApplicationContext()).getTimersDatabase().timersDao().insert(timer);

            finish();
        }
    }
}
