package com.example.exsecutum;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.sql.Time;
import java.util.Calendar;

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


        //TODO: uncomment this for the demo
        timeLeftMilli = 10000;

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
                countdownTime.setText("timer done!");
                setNotif();
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

    private void setNotif() {

        AlarmManager alarm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(this, timerAlarm.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);

        alarm.set(AlarmManager.RTC_WAKEUP, 0, pendingIntent);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = "timerReminderChannel";
            String description = "Channel for the timer alarm manager";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel("TimerAlarm", name, importance);
            channel.setDescription(description);


            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);

        }


    }

}