package com.example.sudofocus;

import android.app.ActivityManager;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Build;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class TimerActivity extends AppCompatActivity {

    private TextView textView,timerDisplay;
    private long duration;
    private static final long initialTimer = 5000;
    private static final String TAG = "{DEBUG INFO}";
    private long count;
    ActivityManager activityManager;
    AudioManager audioManager;
    private Button secretButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        activityManager = (ActivityManager)
                getApplicationContext().getSystemService(getApplicationContext().ACTIVITY_SERVICE);
        Bundle bundle = getIntent().getExtras();
        duration = bundle.getLong("duration");

        instantiate();

        secretButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prepareToFinish();
            }
        });

        new CountDownTimer(initialTimer, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                timerDisplay.setText(String.valueOf(--count) + ":00");
            }

            @Override
            public void onFinish() {
                textView.setText(R.string.timerEndsIn);
                count = duration/1000;
                startLockTask();
                initialiseFocusTimer();
            }
        }.start();

    }

    public void initialiseFocusTimer() {
        new CountDownTimer(duration, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                if(activityManager.getLockTaskModeState() == ActivityManager.LOCK_TASK_MODE_NONE) {
                    startLockTask();
                }
                timerDisplay.setText(String.valueOf(--count) + ":00");
            }

            @Override
            public void onFinish() {
                prepareToFinish();
            }
        }.start();
    }

    public void prepareToFinish() {
        textView.setText(R.string.TaskEnd);
        audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
        stopLockTask();
        finish();
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(TimerActivity.this,
                "Focus on your work", Toast.LENGTH_LONG).show();
    }

    public void instantiate() {
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && !notificationManager.isNotificationPolicyAccessGranted()) {

            Intent intent = new Intent(
                    android.provider.Settings
                            .ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);

            startActivity(intent);
        }
        audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
        Log.d(TAG, "instantiate: " + audioManager.getRingerMode());
        secretButton = (Button) findViewById(R.id.secret);
        textView = (TextView) findViewById(R.id.timerText);
        timerDisplay = (TextView) findViewById(R.id.timerDisplay);
        timerDisplay.setText(String.valueOf(initialTimer/1000) + ":00");
        count = initialTimer/1000;
    }

}
