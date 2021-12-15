package com.example.exsecutum;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class taskAlarm extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        Intent mainPage = new Intent(context, DailyView.class);
        mainPage.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendIntent = PendingIntent.getActivity(context, 0 , mainPage, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,"TaskAlarm")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Exsecutum")
                .setContentText("You have tasks to do!")
                .setAutoCancel(true)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM))
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendIntent);

        NotificationManagerCompat notifManager = NotificationManagerCompat.from(context);
        notifManager.notify(002, builder.build());



    }
}
