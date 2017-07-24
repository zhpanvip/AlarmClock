package com.cypoem.alarmclock;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;


/**
 * Created by edianzu on 2017/4/20.
 */

public class PollingService extends Service {
    public static final String ACTION = "com.srcb.service.PollingService";

    private Notification.Builder mBuilder;
    private NotificationManager mManager;
    private Notification mNotification;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(null==mManager){
            initNotifiManager();
        }
        new PollingThread().start();
        Log.e("Start","Service Start");
        Log.e("Clock","Polling...");

        return START_STICKY;
    }

   /* @Override
    public void onStart(Intent intent, int startId) {
        new PollingThread().start();
    }*/


    private void initNotifiManager() {
        mManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }


    private void showNotification() {

        Intent i = new Intent(this, MessageActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, i,
                PendingIntent.FLAG_UPDATE_CURRENT);
         mBuilder=new Notification.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                 .setWhen(System.currentTimeMillis())
                .setContentIntent(pendingIntent)
                 .setContentTitle("New Message")
                 .setContentText("New Message Content")
                 .setContentIntent(pendingIntent);
        mBuilder.setDefaults(Notification.DEFAULT_ALL);

        mNotification = mBuilder.build();
        mManager.notify(1,mNotification);

    }

    int count = 0;
    class PollingThread extends Thread {
        @Override
        public void run() {
            count ++;
            if (count % 5 == 0) {
                showNotification();
                Log.e("Clock","New message...");
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.out.println("Service:onDestroy");
    }

}
