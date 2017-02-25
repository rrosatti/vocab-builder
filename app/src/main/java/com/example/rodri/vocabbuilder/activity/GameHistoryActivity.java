package com.example.rodri.vocabbuilder.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
    private TextView txtTotalGames;
    private TextView txtCorrectBottom;
    private TextView txtIncorrectBottom;
    private MyDataSource dataSource;
    private GameHistoryAdapter adapter;
    private List<Game> games;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_history);

        iniViews();
        dataSource = new MyDataSource(this);

        // implement a progress bar?
        if (games != null) {
            updateBottomInfo();

            LinearLayoutManager llm = new LinearLayoutManager(this);
            adapter = new GameHistoryAdapter(this, games);
            listOfGames.setLayoutManager(llm);
            listOfGames.setAdapter(adapter);

        } else {
            Toast.makeText(this, R.string.toast_something_went_wrong, Toast.LENGTH_SHORT).show();
        }

        games = getGames();
    }

    private void iniViews() {
        listOfGames = (RecyclerView) findViewById(R.id.activityGameHistory_listOfGames);
        txtTotalGames = (TextView) findViewById(R.id.activityGameHistory_txtTotalGames);
        txtCorrectBottom = (TextView) findViewById(R.id.activityGameHistory_txtCorrectBottom);
        txtIncorrectBottom = (TextView) findViewById(R.id.activityGameHistory_txtIncorrectBottom);
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
}
