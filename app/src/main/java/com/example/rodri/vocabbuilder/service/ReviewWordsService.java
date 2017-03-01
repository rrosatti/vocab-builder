package com.example.rodri.vocabbuilder.service;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;

import com.example.rodri.vocabbuilder.database.MyDataSource;

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
        dataSource = new MyDataSource(getApplicationContext());
        List<Long> usersIds = getUsersActiveNotification();


        if (usersIds != null) {
            for (int i=0; i<usersIds.size(); i++) {
                List<Long> wordsIds = getWords(usersIds.get(i));
                showNotification(usersIds.get(i), wordsIds.size());
            }
        }
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
        // get user info (name)
        return null;
    }
}
