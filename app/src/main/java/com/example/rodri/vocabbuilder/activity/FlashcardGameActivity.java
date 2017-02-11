package com.example.rodri.vocabbuilder.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import com.example.rodri.vocabbuilder.R;
import com.example.rodri.vocabbuilder.adapter.CardPagerAdapter;
import com.example.rodri.vocabbuilder.database.MyDataSource;
import com.example.rodri.vocabbuilder.model.DetailedWord;
import com.example.rodri.vocabbuilder.model.Login;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rodri on 2/9/2017.
 */

public class FlashcardGameActivity extends AppCompatActivity {

    private ViewPager pager;
    private int numOfWords;
    private List<DetailedWord> words = new ArrayList<>();
    private List<Long> wordsIds = new ArrayList<>();
    private CardPagerAdapter cardAdapter;
    private MyDataSource dataSource;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flashcard_game);

        iniViews();
        dataSource = new MyDataSource(this);
        numOfWords = getIntent().getExtras().getInt("numOfWords", 5);

        getWords();
        pager.setAdapter(cardAdapter);

        // 1 - get num of words
        // 2 - get the X words to be played with
        // 3 - create the flashcard fragments (front and back) dynamically (for loop?)
        //      3.1 - create a new fragment passing the wordId as an argument?
        // 4 - save the result of each card
        // 5 - when finished, show a result screen
    }

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

            cardAdapter = new CardPagerAdapter(getSupportFragmentManager(), wordsIds);

            dataSource.close();
        } catch (Exception e) {
            e.printStackTrace();
            dataSource.close();
        }

    }
}
