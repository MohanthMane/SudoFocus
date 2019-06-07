package com.example.sudofocus;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText minuteInput,secondsInput;
    private Button start;
    private String minuteString,secondsString;
    private long timeInMillis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        minuteInput = (EditText) findViewById(R.id.minutesInput);
        secondsInput = (EditText) findViewById(R.id.secondsInput);
        start = (Button) findViewById(R.id.go);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getTime();
                TimeUtil timeUtil = new TimeUtil(minuteString, secondsString);
                if(timeUtil.isValid()) {
                    timeInMillis = timeUtil.toMillis();
                    Intent intent = new Intent(MainActivity.this,TimerActivity.class);
                    intent.putExtra("duration", timeInMillis);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(MainActivity.this,
                            "Give valid input", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    public void getTime() {
        minuteString = minuteInput.getText().toString();
        secondsString = secondsInput.getText().toString();
        if(minuteString.isEmpty())
            minuteString = "0";
        if(secondsString.isEmpty())
            secondsString = "0";
    }

}
