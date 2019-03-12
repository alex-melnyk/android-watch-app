package com.igorganapolsky.vibratingwatchapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.igorganapolsky.vibratingwatchapp.data.models.Timer;
import com.igorganapolsky.vibratingwatchapp.ui.fragments.TimerDeleteFragment;
import com.igorganapolsky.vibratingwatchapp.util.TimerTransform;

public class TimerDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    private final int SETTING_REQUEST_CODE = 100;
    private final int SETTING_SUCCESS_CODE = 101;

    private long timeLeft;
    private Timer model;
    private CountDownTimer currentTimer;

    private ProgressBar pbTime;
    private TextView tvTime;
    private ImageView ivStart;
    private ImageView ivPause;
    private ImageView ivStop;
    private ImageView ivRestart;

    private ImageView ivTimerSettings;
    private ImageView ivTimerRemove;

    private Animation blinking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer_details);

        ivTimerSettings = findViewById(R.id.ivTimerSettings);
        ivTimerSettings.setOnClickListener(this);
        ivTimerRemove = findViewById(R.id.ivTimerRemove);
        ivTimerRemove.setOnClickListener(this);

        pbTime = findViewById(R.id.pbTime);
        tvTime = findViewById(R.id.tvTime);
        // CONTROLS
        ivStart = findViewById(R.id.ivStart);
        ivStart.setOnClickListener(this);
        ivPause = findViewById(R.id.ivPause);
        ivPause.setOnClickListener(this);
        ivStop = findViewById(R.id.ivStop);
        ivStop.setOnClickListener(this);
        ivRestart = findViewById(R.id.ivRestart);
        ivRestart.setOnClickListener(this);

        blinking = new AlphaAnimation(1f, .25f);
        blinking.setDuration(500);
        blinking.setRepeatMode(Animation.REVERSE);
        blinking.setRepeatCount(Animation.INFINITE);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (model == null) {
            model = (Timer) getIntent().getSerializableExtra("TIMER_MODEL");
        }

        renderTime(model.getMilliseconds(), true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case SETTING_REQUEST_CODE:
                if (resultCode == SETTING_SUCCESS_CODE) {
                    model = (Timer) data.getSerializableExtra("TIMER_MODEL");
                    renderTime(model.getMilliseconds(), true);
                }
                break;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivStart:
                currentTimer = prepareCountDown();

                showPlayOrPause(true);
                break;
            case R.id.ivPause:
                currentTimer.cancel();

                showPlayOrPause(false);
                break;
            case R.id.ivStop:
                clearCountDown();

                showPlayOrPause(false);

                renderTime(model.getMilliseconds(), true);

                finish();
                break;
            case R.id.ivRestart:
                clearCountDown();

                currentTimer = prepareCountDown();

                showPlayOrPause(true);
                break;
            case R.id.ivTimerSettings:
                Intent settingIntent = new Intent(getApplicationContext(), SetTimerActivity.class);
                settingIntent.putExtra("TIMER_MODEL", model);

                startActivityForResult(settingIntent, SETTING_REQUEST_CODE);
                break;
            case R.id.ivTimerRemove:
                Bundle deleteBundle = new Bundle();
                deleteBundle.putSerializable("TIMER_MODEL", model);

                Fragment delete_timer = new TimerDeleteFragment();
                delete_timer.setArguments(deleteBundle);

                getSupportFragmentManager()
                        .beginTransaction()
                        .addToBackStack(null)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .replace(R.id.timer_details_fragment, delete_timer)
                        .commit();
                break;
        }
    }

    private CountDownTimer prepareCountDown() {
        long millisecondsLeft = timeLeft > 0 ? timeLeft : model.getMilliseconds();

        renderTime(millisecondsLeft, false);

        return new CountDownTimer(millisecondsLeft, 50) {
            @Override
            public void onTick(long millis) {
                timeLeft = millis;

                renderTime(millis, true);
            }

            @Override
            public void onFinish() {
                clearCountDown();

                showPlayOrPause(false);

                renderTime(0, true);

                blinking.cancel();
            }
        }.start();
    }

    private void clearCountDown() {
        timeLeft = 0;

        if (currentTimer != null) {
            currentTimer.cancel();
            currentTimer = null;
        }
    }

    private void renderTime(long timeLeft, boolean animateProgress) {
        int progress = (int) ((double) timeLeft / (double) model.getMilliseconds() * 100.);
        pbTime.setProgress(100 - progress, animateProgress);

        tvTime.setText(TimerTransform.millisToTimeString(timeLeft));
    }

    private void showPlayOrPause(boolean isPause) {
        if (!isPause) {
            ivStart.setVisibility(View.VISIBLE);
            ivPause.setVisibility(View.GONE);

            disableAdditionalButtons(false);

            tvTime.startAnimation(blinking);
        } else {
            ivStart.setVisibility(View.GONE);
            ivPause.setVisibility(View.VISIBLE);

            disableAdditionalButtons(true);

            blinking.cancel();
        }
    }

    private void disableAdditionalButtons(boolean disable) {
        ivTimerSettings.setClickable(!disable);
        ivTimerRemove.setClickable(!disable);

        ivTimerSettings.setAlpha(disable ? .5f : 1f);
        ivTimerRemove.setAlpha(disable ? .5f : 1f);
    }
}
