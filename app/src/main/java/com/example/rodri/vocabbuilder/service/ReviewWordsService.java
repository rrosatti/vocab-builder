package com.example.rodri.vocabbuilder.service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.IBinder;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.NotificationCompat;

import com.example.rodri.vocabbuilder.R;
import com.example.rodri.vocabbuilder.activity.WordsToReviewActivity;
import com.example.rodri.vocabbuilder.database.MyDataSource;
import com.example.rodri.vocabbuilder.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rodri on 3/1/2017.
 */

public class ReviewWordsService extends Service {

    private static final String MY_PREFERENCES = "com.example.rodri.vocabbuilder";
    private static final String USER_ID = "com.example.rodri.vocabbuilder.userid";

    private MyDataSource dataSource;
    private SharedPreferences sharedPreferences;

    public IBinder onBind(Intent intent) { return null; }

    @Override
    public void onDestroy() {
        System.out.println("onDestroy() was called!");
    }

    @Override
    public void onStart(Intent intent, int startId) {
        System.out.println("onStart() ReviewWordsService");
        dataSource = new MyDataSource(getApplicationContext());
        //List<Long> usersIds = getUsersActiveNotification();
        sharedPreferences = getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE);
        long userId = sharedPreferences.getLong(USER_ID, 0);

        if (userId != 0) {
            List<Long> wordsIds = getWords(userId);
            if (wordsIds != null) {
                showNotification(userId, wordsIds.size());
            }
        }

        //Old implementation considering multiple users
        /**
        if (usersId != null) {
            for (int i=0; i<usersIds.size(); i++) {
                List<Long> wordsIds = getWords(usersIds.get(i));
                if (wordsIds != null) {
                    showNotification(usersIds.get(i), wordsIds.size());
                }

            }
        }*/
    }

    private List<Long> getUsersActiveNotification() {
        try {
            dataSource.open();

            return dataSource.getUsersActiveNotification();

        } catch (Exception e) {
            e.printStackTrace();
            dataSource.close();
            return null;
        }
    }

    private List<Long> getWords(Long userId) {
        try {
            dataSource.open();

            return dataSource.getWordsThatNeedToReview(userId, 0);

        } catch (Exception e) {
            e.printStackTrace();
            dataSource.close();
            return null;
        }
    }

    private void showNotification(long userId, long numWords) {
        String message = "";
        User user = getUser(userId);
        if (user != null) {
            Resources res = getResources();
            message = res.getQuantityString(R.plurals.string_notification_message, (int) numWords, user.getName(), numWords);
        }

        // need to change the icon
        NotificationCompat.Builder mBuilder = (android.support.v7.app.NotificationCompat.Builder)
                new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.owl_256)
                .setContentTitle("VocabBuilder")
                .setContentText(message)
                .setAutoCancel(true);

        Intent resultIntent = new Intent(this, WordsToReviewActivity.class);
        resultIntent.putExtra("userId", userId);

        // The Stack Builder ensures that navigating backwards from the Activity leads out of
        // the application to the Home Screen
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(WordsToReviewActivity.class);
        stackBuilder.addNextIntent(resultIntent);

        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(resultPendingIntent);

        int mNotificationId = 001;

        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(mNotificationId, mBuilder.build());

    }

    private User getUser(long userId) {
        try {
            dataSource.open();

            return dataSource.getUser(userId);

        } catch (Exception e) {
            e.printStackTrace();
            dataSource.close();
            return null;
        }
    }
}
