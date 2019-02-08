package com.igorganapolsky.vibratingwatchapp;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Locale;

public class TimerDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    private long timerTime;
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

        timerTime = getIntent().getLongExtra("TIMER_TIME", 0);

        renderTime(timerTime);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivStart:
                currentTimer = prepareTimer();

                ivStart.setVisibility(View.GONE);
                ivPause.setVisibility(View.VISIBLE);
                break;
            case R.id.ivPause:
                currentTimer.cancel();
                currentTimer = null;

                ivStart.setVisibility(View.VISIBLE);
                ivPause.setVisibility(View.GONE);
                break;
            case R.id.ivStop:
                currentTimer.cancel();
                currentTimer = null;

                ivStart.setVisibility(View.VISIBLE);
                ivPause.setVisibility(View.GONE);
                break;
            case R.id.ivRestart:
                currentTimer.cancel();
                currentTimer = prepareTimer();

                ivStart.setVisibility(View.GONE);
                ivPause.setVisibility(View.VISIBLE);
                break;
        }
    }

    CountDownTimer prepareTimer() {
        return new CountDownTimer(timerTime, 100) {
            @Override
            public void onTick(long timeLeft) {
                renderTime(timeLeft);
            }

            @Override
            public void onFinish() {
                currentTimer = null;
                renderTime(0);
            }
        }.start();
    }

    void renderTime(long timeLeft) {
        long seconds = (timeLeft / 1000) % 60;
        long minutes = (timeLeft / (1000 * 60)) % 60;
        long hours = (timeLeft / (1000 * 60 * 60)) % 24;

        tvTime.setText(String.format(Locale.ENGLISH, "%02d : %02d : %02d", hours, minutes, seconds));

        int progress = (int) ((double)timeLeft / (double) timerTime * 100.0);
        pbTime.setProgress(100 - progress, false);
    }
}
