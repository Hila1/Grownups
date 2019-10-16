package com.example.shula.mygrownupsapp.services;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.lang.reflect.Member;
import java.util.Map;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
//    private static final String MEMBERID = "memberId";
//    private static final String Level= "level";
//    @Override
//    public void onMessageReceived(RemoteMessage remoteMessage) {
//
//        if(remoteMessage.getData().size() > 0){
//            Map<String, String> data = remoteMessage.getData();
//
//            String membeId = data.get(MEMBERID);
//            String imageUrl = data.get(IMAGEURL);
//            String level = data.get(Level);
//            String text = level+ "to get options click here";
//            Member member = new Member();
//            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
//            mBuilder.setSmallIcon(R.mipmap.ic_launcher);
//
//            //I'm only using username and text for this tutorial. but you can use the other
//            // variables for when you have a custom view on your notifications and what to do
//            // when the user clicks the notification
//            mBuilder.setContentTitle(memberName);
//            mBuilder.setContentText(text);
//            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
//            mBuilder.setSmallIcon(R.mipmap.ic_launcher);
//
//            //I'm only using username and text for this tutorial. but you can use the other
//            // variables for when you have a custom view on your notifications and what to do
//            // when the user clicks the notification
//            mBuilder.setContentTitle(username);
//            mBuilder.setContentText(text);
//
//            Intent resultIntent = new Intent(this, MainActivity.class);
//            TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
//
//// Adds the Intent that starts the Activity to the top of the stack
//            stackBuilder.addNextIntent(resultIntent);
//            PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);
//            mBuilder.setContentIntent(resultPendingIntent);
//
//            NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//
//// notificationID allows you to update the notification later on.
//            mNotificationManager.notify(0, mBuilder.build());
//        }
//    }
}
