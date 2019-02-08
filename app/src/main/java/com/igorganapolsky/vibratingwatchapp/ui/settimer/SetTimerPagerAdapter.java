package com.igorganapolsky.vibratingwatchapp.ui.settimer;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class SetTimerPagerAdapter extends FragmentStatePagerAdapter {
    private Fragment[] fragments = {
            new SetTimerFragment(),
            new SetVibrationFragment(),
            new SetRepeatFragment()
    };

    public SetTimerPagerAdapter(FragmentManager fm) {
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
