package com.igorganapolsky.vibratingwatchapp;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.NotificationCompat;
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

        renderTime(timerTime, true);
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
        }
    }

    private CountDownTimer prepareCountDown() {
        renderTime(timerTime, false);

        return new CountDownTimer(timerTime, 50) {
            @Override
            public void onTick(long timeLeft) {
                renderTime(timeLeft, true);
            }

            @Override
            public void onFinish() {
                clearCountDown();

                renderTime(0, true);

                showPlayOrPause(false);

//                Notification notificationCompat = new NotificationCompat.Builder(getApplicationContext(), NotificationChannel.DEFAULT_CHANNEL_ID)
//                        .setSmallIcon(R.drawable.ic_launcher)
//                        .setLargeIcon(((BitmapDrawable)getResources().getDrawable(R.drawable.ic_launcher)).getBitmap())
//                        .setContentTitle("Timer end!")
//                        .setContentText(String.format("The timer for %02d is end", timerTime))
//                        .setPriority(NotificationCompat.PRIORITY_HIGH)
//                        .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
//                        .build();
//
//                NotificationManager notificationManager = getSystemService(NotificationManager.class);
//                notificationManager.notify(0, notificationCompat);
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
        long seconds = (timeLeft / 1000) % 60;
        long minutes = (timeLeft / (1000 * 60)) % 60;
        long hours = (timeLeft / (1000 * 60 * 60)) % 24;

        tvTime.setText(String.format(Locale.ENGLISH, "%02d : %02d : %02d", hours, minutes, seconds));

        int progress = (int) ((double) timeLeft / (double) timerTime * 100.);
        pbTime.setProgress(100 - progress, animateProgress);
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
