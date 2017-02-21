package com.example.rodri.vocabbuilder.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.rodri.vocabbuilder.R;
import com.example.rodri.vocabbuilder.adapter.CardPagerAdapter;
import com.example.rodri.vocabbuilder.database.MyDataSource;
import com.example.rodri.vocabbuilder.interfaces.OnSetWordResult;
import com.example.rodri.vocabbuilder.model.DetailedWord;
import com.example.rodri.vocabbuilder.model.GameProgress;
import com.example.rodri.vocabbuilder.model.Login;
import com.example.rodri.vocabbuilder.service.GameProgressService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rodri on 2/9/2017.
 */

public class FlashcardActivity extends AppCompatActivity implements OnSetWordResult{

    private ViewPager pager;
    private int numOfWords;
    private List<DetailedWord> words = new ArrayList<>();
    private List<Long> wordsIds = new ArrayList<>();
    private CardPagerAdapter cardAdapter;
    private MyDataSource dataSource;
    private GameProgressService mGameProgressService;
    private boolean mServiceBound = false;
    private GameProgress gameProgress;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flashcard);

        iniViews();
        dataSource = new MyDataSource(this);
        numOfWords = getIntent().getExtras().getInt("numOfWords", 5);

        getWords();
        pager.setAdapter(cardAdapter);
        gameProgress = new GameProgress(wordsIds);

        // 1 - get num of words
        // 2 - get the X words to be played with
        // 3 - create the flashcard fragments (front and back) dynamically (for loop?)
        //      3.1 - create a new fragment passing the wordId as an argument?
        // 4 - save the result of each card
        // 5 - when finished, show a result screen

    }
/**
    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            GameProgressService.MyBinder myBinder = (GameProgressService.MyBinder) service;
            mGameProgressService = myBinder.getService();
            mServiceBound = true;


            mGameProgressService.initializeGameProgress(wordsIds);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mServiceBound = false;
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, GameProgressService.class);
        startService(intent);
        bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);

    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mServiceBound) {
            unbindService(mServiceConnection);
            mServiceBound = false;
        }
    }*/

    private void iniViews() {
        pager = (ViewPager) findViewById(R.id.activityFlashCardGame_pager);
    }

    private void getWords() {
        try {
            dataSource.open();

            long userId = Login.getInstance().getUserId();
            // need to implement a method to get only the words that NEED to be practiced (special algorithm)
            words = dataSource.getDetailedWords(userId, numOfWords);
            wordsIds = dataSource.getDetailedWordsIds(userId, numOfWords);

            cardAdapter = new CardPagerAdapter(getFragmentManager(), wordsIds);

            dataSource.close();
        } catch (Exception e) {
            e.printStackTrace();
            dataSource.close();
        }

    }

    @Override
    public void setWordResult(long wordId, int result) {
        gameProgress.setResult(wordId, result);
        Toast.makeText(this, "wordId: " + wordId + " result: " + result, Toast.LENGTH_SHORT).show();
    }
}
