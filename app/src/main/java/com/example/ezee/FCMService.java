package com.example.ezee;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.room.Room;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Random;

public class FCMService extends FirebaseMessagingService {

    String title, msg;
    int uid;

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        if (remoteMessage.getData() != null){
            title = remoteMessage.getData().get("title");
            msg = remoteMessage.getData().get("body");
//            new dbThread().start();
            showNotification(title, msg);
        }
        else if (remoteMessage.getNotification() != null) {
            title = remoteMessage.getNotification().getTitle();
            msg = remoteMessage.getNotification().getBody();
//            new dbThread().start();
            showNotification(title, msg);
        }
    }

    public void showNotification(String title, String message){

        Intent intent = new Intent(this, NotificationHistory.class);
        String channel_id = "fb_channel";
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), channel_id)
                .setAutoCancel(true)
                .setOnlyAlertOnce(true)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .setContentTitle(title)
                .setContentText(message)
                .setSmallIcon(R.drawable.ic_baseline_notifications_24);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(channel_id, "fb", NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(notificationChannel);
        }



        Random rd = new Random();
        notificationManager.notify(rd.nextInt(), builder.build());
    }

    class dbThread extends Thread{
        @Override
        public void run() {
            super.run();

            AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                    AppDatabase.class, "notifications").build();

            NotiDAO notiDao = db.userDao();
            notiDao.insert(new NotiClass(title, msg));
//            Log.i("Task", "Inserted in DB");
        }
    }

}
