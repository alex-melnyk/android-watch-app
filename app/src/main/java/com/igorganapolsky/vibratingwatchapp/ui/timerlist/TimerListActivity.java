package com.igorganapolsky.vibratingwatchapp.ui.timerlist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import com.igorganapolsky.vibratingwatchapp.R;
import com.igorganapolsky.vibratingwatchapp.ui.timersettings.SetTimerActivity;

public class TimerListActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.llAddTimerButton).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        startActivity(new Intent(getApplicationContext(), SetTimerActivity.class));
    }
}
