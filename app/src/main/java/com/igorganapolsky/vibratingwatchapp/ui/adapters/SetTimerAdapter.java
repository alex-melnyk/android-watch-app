package com.igorganapolsky.vibratingwatchapp.ui.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import com.igorganapolsky.vibratingwatchapp.ui.settimer.SetRepeatFragment;
import com.igorganapolsky.vibratingwatchapp.ui.settimer.SetTimerFragment;
import com.igorganapolsky.vibratingwatchapp.ui.settimer.SetVibrationFragment;

public class SetTimerAdapter extends FragmentStatePagerAdapter {
    private Fragment[] fragments = {
            new SetTimerFragment(),
            new SetVibrationFragment(),
            new SetRepeatFragment()
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
