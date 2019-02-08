package com.igorganapolsky.vibratingwatchapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private View addTimerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // TODO: Only for quick testing.
//        startActivity(new Intent(getApplicationContext(), SetTimerActivity.class));

        addTimerButton = findViewById(R.id.addTimerButton);
        addTimerButton.setOnClickListener(this);

        findViewById(R.id.ivTimerListImage).setVisibility(ImageView.GONE);
        findViewById(R.id.addTimerButtonImageLabel).setVisibility(View.GONE);
    }

    @Override
    public void onClick(View view) {
        Intent addNewTimerIntent = new Intent(getApplicationContext(), SetTimerActivity.class);

        startActivity(addNewTimerIntent);
    }
}
