package com.example.exsecutum;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class timer extends AppCompatActivity {
    private TextView countdownTime;
    private Button startBtn;
    private Button stopBtn;

    private CountDownTimer timer;
    private long timeLeftMilli = 1200000; //default time is 20 mins in milliseconds
    private boolean timerOn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timer_page);

        countdownTime = findViewById(R.id.countdown_time);
        startBtn = findViewById(R.id.start_button);
        stopBtn = findViewById(R.id.stop_button);

        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startTimer();
            }
        });

        stopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopTimer();
            }
        });
    }

    public void startTimer() {
        //timerOn = true;
        timer = new CountDownTimer(timeLeftMilli, 1000){ //every 1000 milli seconds (1s) timer reacts

            @Override
            public void onTick(long l) {
                timeLeftMilli = l;
                updateTime();
            }

            @Override
            public void onFinish() {

            }
        }.start();

    }


    public void stopTimer(){
        timer.cancel();
        //timerOn = false;
    }
    private void updateTime() {
        int mins = (int) timeLeftMilli / 60000;
        int sec = (int) (timeLeftMilli % 60000) / 1000;

        String timeBuild = mins + ":";
        if(sec < 10){timeBuild += "0" + sec;}
        else {timeBuild += sec;}

        countdownTime.setText(timeBuild);
    }


}