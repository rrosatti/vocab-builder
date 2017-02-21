package com.example.rodri.vocabbuilder.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.example.rodri.vocabbuilder.model.GameProgress;

import java.util.List;

/**
 * Created by rodri on 2/14/2017.
 */

public class GameProgressService extends Service {

    private GameProgress gameProgress;
    private static String LOG_TAG = "GameProgressService";
    private IBinder mBinder = new MyBinder();

    @Override
    public void onCreate() {
        super.onCreate();
        Log.v(LOG_TAG, "in onCreate");
        gameProgress = new GameProgress();
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.v(LOG_TAG, "in onBind");
        return mBinder;
    }

    @Override
    public void onRebind(Intent intent) {
        Log.v(LOG_TAG, "in onRebind");
        super.onRebind(intent);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.v(LOG_TAG, "in onUnbind");
        return super.onUnbind(intent);
    }

    public void onDestroy() {
        super.onDestroy();
        Log.v(LOG_TAG, "in onDestroy");
        // do something?
    }

    public class MyBinder extends Binder {
        public GameProgressService getService() {
            return GameProgressService.this;
        }
    }

    public void initializeGameProgress(List<Long> wordsIds) {
        Log.v(LOG_TAG, "in initializeGameProgress");
        gameProgress.setWordsIds(wordsIds);

    }

}
