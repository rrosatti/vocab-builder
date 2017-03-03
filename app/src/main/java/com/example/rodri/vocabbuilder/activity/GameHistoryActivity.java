package com.example.rodri.vocabbuilder.activity;

import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rodri.vocabbuilder.R;
import com.example.rodri.vocabbuilder.adapter.GameHistoryAdapter;
import com.example.rodri.vocabbuilder.database.MyDataSource;
import com.example.rodri.vocabbuilder.model.Game;
import com.example.rodri.vocabbuilder.model.Login;

import java.util.List;

/**
 * Created by rodri on 2/25/2017.
 */

public class GameHistoryActivity extends AppCompatActivity {

    private RecyclerView listOfGames;
    private TextView txtTotalWords;
    private TextView txtCorrectBottom;
    private TextView txtIncorrectBottom;
    private MyDataSource dataSource;
    private GameHistoryAdapter adapter;
    private List<Game> games;
    private ProgressBar progressBar;
    private LinearLayout containerBottom;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_history);

        iniViews();
        dataSource = new MyDataSource(this);

        GetDataFromDatabase task = new GetDataFromDatabase();
        task.execute("");

    }

    private void iniViews() {
        listOfGames = (RecyclerView) findViewById(R.id.activityGameHistory_listOfGames);
        txtTotalWords = (TextView) findViewById(R.id.activityGameHistory_txtTotalWordsBottom);
        txtCorrectBottom = (TextView) findViewById(R.id.activityGameHistory_txtCorrectBottom);
        txtIncorrectBottom = (TextView) findViewById(R.id.activityGameHistory_txtIncorrectBottom);
        progressBar = (ProgressBar) findViewById(R.id.activityGameHistory_progressBar);
        containerBottom = (LinearLayout) findViewById(R.id.activityGameHistory_containerBottom);
    }

    private List<Game> getGames() {
        try {
            dataSource.open();

            long userId = Login.getInstance().getUserId();
            games = dataSource.getGames(userId);

            return games;

        } catch (Exception e) {
            e.printStackTrace();
            dataSource.close();
            return null;
        }
    }

    private void updateBottomInfo() {
        int totalWords = 0;
        int totalCorrect = 0;
        int totalIncorrect = 0;

        for (Game game : games) {
            totalWords += game.getNumWords();
            totalCorrect += game.getCorrect();
            totalIncorrect += game.getIncorrect();
        }

        Resources res = getResources();
        String sTotalWords = String.format(res.getString(R.string.text_total_words), totalWords);
        String sTotalCorrect = String.format(res.getString(R.string.text_correct), totalCorrect);
        String sTotalIncorrect = String.format(res.getString(R.string.text_incorrect), totalIncorrect);

        txtTotalWords.setText(sTotalWords);
        txtCorrectBottom.setText(sTotalCorrect);
        txtIncorrectBottom.setText(sTotalIncorrect);
    }

    private class GetDataFromDatabase extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                Thread.sleep(1000);
                games = getGames();
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }

            return "";
        }

        @Override
        protected void onPostExecute(String s) {
            if (games != null) {
                updateBottomInfo();
                progressBar.setVisibility(View.GONE);

                LinearLayoutManager llm = new LinearLayoutManager(GameHistoryActivity.this);
                adapter = new GameHistoryAdapter(GameHistoryActivity.this, games);
                listOfGames.setLayoutManager(llm);
                listOfGames.setAdapter(adapter);

                containerBottom.setVisibility(View.VISIBLE);

            } else {
                Toast.makeText(GameHistoryActivity.this,
                        R.string.toast_no_games_history_to_show, Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }
}
