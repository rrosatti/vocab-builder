package com.example.rodri.vocabbuilder.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.rodri.vocabbuilder.R;
import com.example.rodri.vocabbuilder.adapter.ReviewWordAdapter;
import com.example.rodri.vocabbuilder.database.MyDataSource;
import com.example.rodri.vocabbuilder.model.DetailedWord;
import com.example.rodri.vocabbuilder.util.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rodri on 3/2/2017.
 */

public class WordsToReviewActivity extends AppCompatActivity {

    private RecyclerView listOfWords;
    private MyDataSource dataSource;
    private ReviewWordAdapter adapter;
    private List<DetailedWord> detailedWords = new ArrayList<>();
    private long userId;
    private Util util = new Util();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_words_to_review);

        iniViews();
        userId = getIntent().getExtras().getLong("userId", 0);
        dataSource = new MyDataSource(this);

        if (userId != 0) {
            detailedWords = getWords();

            if (detailedWords != null) {
                adapter = new ReviewWordAdapter(this, detailedWords);
                LinearLayoutManager llm = new LinearLayoutManager(this);
                listOfWords.setLayoutManager(llm);
                listOfWords.setAdapter(adapter);
            } else {
                _finishActivity();
            }
        } else {
            _finishActivity();
        }

    }

    private void iniViews() {
        listOfWords = (RecyclerView) findViewById(R.id.activityWordsToReview_listOfWords);
    }

    private List<DetailedWord> getWords() {
        try {
            dataSource.open();

            List<Long> wordsIds = dataSource.getWordsThatNeedToReview(userId, 0);
            return dataSource.getDetailedWords(wordsIds);

        } catch (Exception e) {
            e.printStackTrace();
            dataSource.close();
            return null;
        }
    }

    private void _finishActivity() {
        String message = getString(R.string.toast_something_went_wrong);
        util.showRedThemeToast(WordsToReviewActivity.this, message);
        finish();
    }
}
