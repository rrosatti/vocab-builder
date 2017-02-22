package com.example.rodri.vocabbuilder.activity;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rodri.vocabbuilder.R;
import com.example.rodri.vocabbuilder.adapter.FlashCardResultAdapter;
import com.example.rodri.vocabbuilder.database.MyDataSource;
import com.example.rodri.vocabbuilder.model.DetailedWord;
import com.example.rodri.vocabbuilder.model.GameProgress;

import java.util.List;

/**
 * Created by rodri on 2/22/2017.
 */

public class FlashCardResultActivity extends AppCompatActivity {

    private TextView txtTotalWords;
    private TextView txtCorrect;
    private TextView txtIncorrect;
    private RecyclerView listOfWords;
    private MyDataSource dataSource;
    private GameProgress gameProgress;
    private List<DetailedWord> detailedWordList;
    private FlashCardResultAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flashcard_result);

        iniViews();
        dataSource = new MyDataSource(this);
        gameProgress = tryToGetGameProgress();

        if (gameProgress == null) {
            Toast.makeText(this, R.string.toast_something_went_wrong, Toast.LENGTH_SHORT).show();
        } else {
            showResults();
        }
    }

    private void iniViews() {
        txtTotalWords = (TextView) findViewById(R.id.activityFlashCardResult_txtTotalWords);
        txtCorrect = (TextView) findViewById(R.id.activityFlashCardResult_txtCorrect);
        txtIncorrect = (TextView) findViewById(R.id.activityFlashCardResult_txtIncorrect);
        listOfWords = (RecyclerView) findViewById(R.id.activityFlashCardResult_listOfWords);
    }

    private GameProgress tryToGetGameProgress() {
        GameProgress tempGameProgress = (GameProgress) getIntent().getSerializableExtra("gameProgress");
        return tempGameProgress;
    }

    private void showResults() {
        Resources res = getResources();

        String totalWords = String.format(res.getString(R.string.text_total_words), gameProgress.getNumOfWords());
        txtTotalWords.setText(totalWords);

        String correct = String.format(res.getString(R.string.text_correct), gameProgress.getNumCorrect());
        txtCorrect.setText(correct);

        String incorrect = String.format(res.getString(R.string.text_incorrect), gameProgress.getNumIncorrect());
        txtIncorrect.setText(incorrect);

        detailedWordList = getDetailedWords(gameProgress.getWordsIds());
        adapter = new FlashCardResultAdapter(this, detailedWordList, gameProgress);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        listOfWords.setLayoutManager(llm);
        listOfWords.setAdapter(adapter);
    }

    private List<DetailedWord> getDetailedWords(List<Long> wordsIds) {
        List<DetailedWord> tempDetailedWordList;
        try {
            dataSource.open();

            tempDetailedWordList = dataSource.getDetailedWords(wordsIds);

            dataSource.close();
            return tempDetailedWordList;

        } catch (Exception e) {
            e.printStackTrace();
            dataSource.close();
            return null;
        }
    }
}
