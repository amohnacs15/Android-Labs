package com.androidtitan.dailyselfie;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

/**
 * Created by amohnacs on 9/30/15.
 */
public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        int IDENTIFIER = (int) System.currentTimeMillis();

        Log.e("AlarmReceiver", "... Alarm Received");

        RemoteViews mContentView = new RemoteViews(
                context.getPackageName(),
                R.layout.layout_resource);

        final Intent restartMainActivityIntent = new Intent(context,
                MainActivity.class);
        restartMainActivityIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        PendingIntent launcherIntent = PendingIntent.getActivity(context, IDENTIFIER, restartMainActivityIntent, 0);

        Notification.Builder notificationBuilder = new Notification.Builder(context)
                .setTicker("It's time to take a selfie!")
                .setSmallIcon(android.R.drawable.stat_sys_warning)
                .setAutoCancel(true)
                .setContentIntent(launcherIntent)
                .setContent(mContentView);

        NotificationManager nm = (NotificationManager) context.getSystemService(
                context.NOTIFICATION_SERVICE);
        nm.notify(IDENTIFIER, notificationBuilder.build());
    }
}
