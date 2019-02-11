package com.igorganapolsky.vibratingwatchapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.igorganapolsky.vibratingwatchapp.data.DatabaseClient;
import com.igorganapolsky.vibratingwatchapp.data.models.Timer;
import com.igorganapolsky.vibratingwatchapp.util.TimerTransform;

public class TimerDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    private final int SETTING_REQUEST_CODE = 100;
    private final int SETTING_SUCCESS_CODE = 101;

    private Timer model;
    private CountDownTimer currentTimer;

    private ProgressBar pbTime;
    private TextView tvTime;
    private ImageView ivStart;
    private ImageView ivPause;
    private ImageView ivStop;
    private ImageView ivRestart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer_details);

        findViewById(R.id.ivTimerSettings).setOnClickListener(this);
        findViewById(R.id.ivTimerRemove).setOnClickListener(this);

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
                clearCountDown();


                showPlayOrPause(false);
                break;
            case R.id.ivStop:
                clearCountDown();


                showPlayOrPause(false);
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
                DatabaseClient.getInstance(getApplicationContext())
                        .getTimersDatabase()
                        .timersDao()
                        .delete(model);

                finish();
                break;
        }
    }

    private CountDownTimer prepareCountDown() {
        renderTime(model.getMilliseconds(), false);

        return new CountDownTimer(model.getMilliseconds(), 50) {
            @Override
            public void onTick(long timeLeft) {
                renderTime(timeLeft, true);
            }

            @Override
            public void onFinish() {
                clearCountDown();

                renderTime(0, true);

                showPlayOrPause(false);
            }
        }.start();
    }

    private void clearCountDown() {
        if (currentTimer != null) {
            currentTimer.cancel();
            currentTimer = null;
        }
    }

    private void renderTime(long timeLeft, boolean animateProgress) {
        int progress = (int) ((double) timeLeft / (double) model.getMilliseconds() * 100.);
        pbTime.setProgress(100 - progress, animateProgress);

        tvTime.setText(TimerTransform.millisToTimeString(model.getMilliseconds()));
    }

    private void showPlayOrPause(boolean isPause) {
        if (!isPause) {
            ivStart.setVisibility(View.VISIBLE);
            ivPause.setVisibility(View.GONE);
        } else {
            ivStart.setVisibility(View.GONE);
            ivPause.setVisibility(View.VISIBLE);
        }
    }
}
