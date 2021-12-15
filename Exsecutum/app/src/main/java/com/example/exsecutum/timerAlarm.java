package com.example.exsecutum;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class timerAlarm extends BroadcastReceiver {

    //Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);


    @Override
    public void onReceive(Context context, Intent intent) {
        Intent timerPage = new Intent(context, timer.class);
        timerPage.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendIntent = PendingIntent.getActivity(context, 0 , timerPage, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,"TimerAlarm")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("Timer Alarm Manager")
                .setContentText("Your timer is done, go take a break.")
                .setAutoCancel(true)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM))
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendIntent);

        NotificationManagerCompat notifManager = NotificationManagerCompat.from(context);
        notifManager.notify(001, builder.build());

    }
}
