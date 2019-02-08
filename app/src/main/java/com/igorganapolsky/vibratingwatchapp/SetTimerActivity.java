package com.igorganapolsky.vibratingwatchapp;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import com.igorganapolsky.vibratingwatchapp.ui.settimer.SetTimerPagerAdapter;

public class SetTimerActivity extends AppCompatActivity implements View.OnClickListener {

    private View ivNextPage;
    private ViewPager vpWizard;
    private TabLayout tlDots;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_timer_activity);

        ivNextPage = findViewById(R.id.ivNextPage);
        ivNextPage.setOnClickListener(this);

        vpWizard = findViewById(R.id.vpWizard);
        vpWizard.setAdapter(new SetTimerPagerAdapter(getSupportFragmentManager()));

        tlDots = findViewById(R.id.tlDots);
        tlDots.setupWithViewPager(vpWizard, true);
    }

    @Override
    public void onClick(View view) {
        int currentPage = vpWizard.getCurrentItem();

        if (currentPage < 2) {
            vpWizard.setCurrentItem(currentPage + 1);
        } else {
            finish();
        }
    }
}
