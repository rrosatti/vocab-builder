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

    public interface IGameProgressService {
        public void StartGameProgress(List<Long> wordsIds);
    }

    public class LocalBinder extends Binder implements IGameProgressService{
        GameProgressService getService() {
            return GameProgressService.this;
        }

        @Override
        public void StartGameProgress(List<Long> wordsIds) {
            gameProgress = new GameProgress(wordsIds);
        }
    }

    @Override
    public void onCreate() {
        Toast.makeText(this, "onCreate() was called", Toast.LENGTH_SHORT).show();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("GameProgressService", "Received start id " + startId + ": " + intent);
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, "onDestroy() was called", Toast.LENGTH_SHORT).show();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    private final IBinder mBinder = new LocalBinder();
}
