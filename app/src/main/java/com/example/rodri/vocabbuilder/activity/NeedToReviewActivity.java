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
import com.example.rodri.vocabbuilder.database.MySQLiteHelper;
import com.example.rodri.vocabbuilder.model.DetailedWord;
import com.example.rodri.vocabbuilder.model.Login;

import java.util.List;

/**
 * Created by rodri on 2/28/2017.
 */

public class NeedToReviewActivity extends AppCompatActivity {

    private RecyclerView listOfWords;
    private MyDataSource dataSource;
    private List<DetailedWord> detailedWords;
    private ReviewWordAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_need_to_review);

        iniViews();
        dataSource = new MyDataSource(this);

        detailedWords = getWords();

        if (detailedWords != null) {
            adapter = new ReviewWordAdapter(this, detailedWords);
            LinearLayoutManager llm = new LinearLayoutManager(this);
            listOfWords.setLayoutManager(llm);
            listOfWords.setAdapter(adapter);
        } else {
            Toast.makeText(this, R.string.toast_something_went_wrong, Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void iniViews() {
        listOfWords = (RecyclerView) findViewById(R.id.activityNeedToReview_listOfWords);
    }

    private List<DetailedWord> getWords() {
        try {
            dataSource.open();

            long userId = Login.getInstance().getUserId();
            return dataSource.getDetailedWordsInReviewOrder(userId);

        } catch (Exception e) {
            e.printStackTrace();
            dataSource.close();
            return null;
        }
    }
}
