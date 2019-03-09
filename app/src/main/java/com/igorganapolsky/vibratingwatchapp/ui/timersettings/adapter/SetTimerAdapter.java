package com.igorganapolsky.vibratingwatchapp.ui.timersettings.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import com.igorganapolsky.vibratingwatchapp.ui.timersettings.step.SetTimerRepeatFragment;
import com.igorganapolsky.vibratingwatchapp.ui.timersettings.step.SetTimerTimeFragment;
import com.igorganapolsky.vibratingwatchapp.ui.timersettings.step.SetTimerVibrationFragment;

public class SetTimerAdapter extends FragmentStatePagerAdapter {
    private Fragment[] fragments = {
            new SetTimerTimeFragment(),
            new SetTimerVibrationFragment(),
            new SetTimerRepeatFragment()
    };

    public SetTimerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        return fragments[i];
    }

    @Override
    public int getCount() {
        return fragments.length;
    }
}
