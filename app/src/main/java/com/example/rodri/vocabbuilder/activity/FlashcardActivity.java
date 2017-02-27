package com.example.rodri.vocabbuilder.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.rodri.vocabbuilder.R;
import com.example.rodri.vocabbuilder.adapter.CardPagerAdapter;
import com.example.rodri.vocabbuilder.database.MyDataSource;
import com.example.rodri.vocabbuilder.interfaces.IFlashCardInterface;
import com.example.rodri.vocabbuilder.model.GameProgress;
import com.example.rodri.vocabbuilder.model.Login;
import com.example.rodri.vocabbuilder.model.SpacedRepetition;
import com.example.rodri.vocabbuilder.service.GameProgressService;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by rodri on 2/9/2017.
 */

public class FlashCardActivity extends AppCompatActivity implements IFlashCardInterface {

    private ViewPager pager;
    private int numOfWords;
    private List<Long> wordsIds = new ArrayList<>();
    private CardPagerAdapter cardAdapter;
    private MyDataSource dataSource;
    private GameProgress gameProgress;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flashcard);

        iniViews();
        dataSource = new MyDataSource(this);
        numOfWords = getIntent().getExtras().getInt("numOfWords", 5);

        wordsIds = getWords();

        if (wordsIds != null) {
            gameProgress = new GameProgress(wordsIds, this);

            wordsIds.add((long)-1); // it corresponds to the result fragment
            cardAdapter = new CardPagerAdapter(getFragmentManager(), wordsIds);
            pager.setAdapter(cardAdapter);
        } else {
            Toast.makeText(this, R.string.toast_something_went_wrong, Toast.LENGTH_SHORT).show();
            finish();
        }

    }

    private void iniViews() {
        pager = (ViewPager) findViewById(R.id.activityFlashCardGame_pager);
    }

    private List<Long> getWords() {
        try {
            dataSource.open();

            long userId = Login.getInstance().getUserId();
            return dataSource.getDetailedWordsIds(userId, numOfWords);

        } catch (Exception e) {
            e.printStackTrace();
            dataSource.close();
            return null;
        }

    }

    @Override
    public void setWordResult(long wordId, int result) {
        gameProgress.setResult(wordId, result);
        Toast.makeText(this, "wordId: " + wordId + " result: " + result, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showResult() {
        gameProgress.calculateResult();
        boolean updated = updateSpacedRepetition();
        boolean saved = saveDataToDatabase();

        if (updated && saved) {
            Intent i = new Intent(FlashCardActivity.this, FlashCardResultActivity.class);
            i.putExtra("gameProgress", gameProgress);
            startActivity(i);
            setResult(Activity.RESULT_OK);
            finish();
        } else {
            Toast.makeText(this, R.string.toast_something_went_wrong, Toast.LENGTH_SHORT).show();
        }
    }

    private boolean updateSpacedRepetition() {
        try {
            dataSource.open();

            if (wordsIds.contains((long)-1)) {
                wordsIds.remove((long)-1);
            }

            for (int i=0; i<wordsIds.size(); i++) {
                long srId = dataSource.getSpacedRepetitionId(wordsIds.get(i));
                if (srId != 0) {
                    SpacedRepetition sr = dataSource.getSpacedRepetition(srId);
                    sr.update(gameProgress.getWordResult(wordsIds.get(i)));
                    dataSource.updateSpacedRepetition(sr.getId(), sr.getStage(), sr.getCycle(),
                            sr.getLast_review(), sr.getNext_review());
                }

            }

            dataSource.close();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            dataSource.close();
            return false;
        }
    }

    private boolean saveDataToDatabase() {
        try {
            dataSource.open();

            wordsIds.remove((long)-1); // remove the element referring to the result fragment

            long userId = Login.getInstance().getUserId();
            long gameId = dataSource.createGame(gameProgress.getNumOfWords(), gameProgress.getNumCorrect(),
                    gameProgress.getNumIncorrect(), getDate());
            dataSource.createUserGame(userId, gameId);

            for (int i=0; i<wordsIds.size(); i++) {
                long wordId = wordsIds.get(i);
                dataSource.updatePerformance(wordId, gameProgress.getWordResult(wordId));
                dataSource.createGameLog(gameId, wordId, gameProgress.getWordResult(wordId));
            }

            dataSource.close();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            dataSource.close();
            return false;
        }
    }

    private long getDate() {
        Calendar cal = Calendar.getInstance();
        return cal.getTimeInMillis();
    }
}
