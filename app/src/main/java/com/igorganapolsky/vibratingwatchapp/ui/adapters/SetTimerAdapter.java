package com.igorganapolsky.vibratingwatchapp.ui.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import com.igorganapolsky.vibratingwatchapp.ui.fragments.SetTimerRepeatFragment;
import com.igorganapolsky.vibratingwatchapp.ui.fragments.SetTimerTimeFragment;
import com.igorganapolsky.vibratingwatchapp.ui.fragments.SetTimerVibrationFragment;

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
