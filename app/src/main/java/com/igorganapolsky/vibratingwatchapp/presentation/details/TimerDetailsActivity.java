package com.igorganapolsky.vibratingwatchapp.presentation.details;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.igorganapolsky.vibratingwatchapp.R;
import com.igorganapolsky.vibratingwatchapp.domain.local.entity.TimerEntity;
import com.igorganapolsky.vibratingwatchapp.domain.model.CountData;
import com.igorganapolsky.vibratingwatchapp.domain.model.TimerModel;
import com.igorganapolsky.vibratingwatchapp.presentation.details.dialog.TimerDeleteDialogFragment;
import com.igorganapolsky.vibratingwatchapp.presentation.settings.SetTimerActivity;
import com.igorganapolsky.vibratingwatchapp.util.ViewModelFactory;

import static com.igorganapolsky.vibratingwatchapp.domain.local.entity.TimerEntity.TIMER_ID;

public class TimerDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    private TimerDetailsViewModel mViewModel;

    private ProgressBar pbTime;
    private TextView tvTime;
    private ImageView ivStart;
    private ImageView ivStop;
    private ImageView ivRestart;

    private ImageView ivTimerSettings;
    private ImageView ivTimerRemove;

    private Animation blinking;

    public static Intent createIntent(Context context, int timerId) {
        Intent intent = new Intent(context, TimerDetailsActivity.class);
        intent.putExtra(TimerEntity.TIMER_ID, timerId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer_details);
        mViewModel = ViewModelProviders.of(this, ViewModelFactory.getInstance()).get(TimerDetailsViewModel.class);

        setupViewModel();
        setupView();
        setupObservers();
    }

    private void setupViewModel() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            int currentId = bundle.getInt(TIMER_ID);
            mViewModel.prepareData(currentId);
        }
    }

    private void setupView() {
        ivTimerSettings = findViewById(R.id.ivTimerSettings);
        ivTimerSettings.setOnClickListener(this);
        ivTimerRemove = findViewById(R.id.ivTimerRemove);
        ivTimerRemove.setOnClickListener(this);

        pbTime = findViewById(R.id.pbTime);
        tvTime = findViewById(R.id.tvTime);
        pbTime.clearAnimation();

        // CONTROLS
        ivStart = findViewById(R.id.ivStart);
        ivStart.setOnClickListener(this);
        ivStop = findViewById(R.id.ivStop);
        ivStop.setOnClickListener(this);
        ivRestart = findViewById(R.id.ivRestart);
        ivRestart.setOnClickListener(this);

        blinking = new AlphaAnimation(1f, .25f);
        blinking.setDuration(500);
        blinking.setRepeatMode(Animation.REVERSE);
        blinking.setRepeatCount(Animation.INFINITE);
    }

    private void setupObservers() {
        mViewModel.getActiveTimerData().observe(this, (this::updateTimerData));
        mViewModel.getViewStateData().observe(this, (this::swapActionMenuState));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivStart:
                view.setSelected(!view.isSelected());
                if (view.isSelected()) {
                    mViewModel.onStart();
                } else {
                    mViewModel.onPause();
                }
                break;

            case R.id.ivStop:
                mViewModel.onStop();
                finish();
                break;

            case R.id.ivRestart:
                mViewModel.onRestart();
                break;

            case R.id.ivTimerSettings:
                // TODO(implement settings screen)
                Intent settingIntent = new Intent(getApplicationContext(), SetTimerActivity.class);
                break;

            case R.id.ivTimerRemove:
                getSupportFragmentManager()
                    .beginTransaction()
                    .addToBackStack(null)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .replace(R.id.timer_details_fragment, new TimerDeleteDialogFragment())
                    .commit();
                break;
        }
    }

    private void swapActionMenuState(TimerModel.State state) {
        switch (state) {
            case PAUSE:
                ivStart.setSelected(false);
                disableAdditionalButtons(true);
                tvTime.startAnimation(blinking);
                break;
            case RUN:
                ivStart.setSelected(true);
                disableAdditionalButtons(true);
                blinking.cancel();
                break;
            case FINISH:
                ivStart.setSelected(false);
                disableAdditionalButtons(false);
                break;
        }
    }

    private void updateTimerData(CountData data) {
        if (data == null) return;
        renderTime(data.getCurrentTime(), data.getCurrentProgress(), data.isAnimationNeeded());
    }

    private void renderTime(String newValue, int progress, boolean animateProgress) {
        pbTime.setProgress(100 - progress, animateProgress);
        tvTime.setText(newValue);
    }

    private void disableAdditionalButtons(boolean disable) {
        ivTimerSettings.setClickable(!disable);
        ivTimerRemove.setClickable(!disable);
        ivTimerSettings.setAlpha(disable ? .5f : 1f);
        ivTimerRemove.setAlpha(disable ? .5f : 1f);
    }
}
