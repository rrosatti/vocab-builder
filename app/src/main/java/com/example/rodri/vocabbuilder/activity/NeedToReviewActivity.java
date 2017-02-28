package com.example.rodri.vocabbuilder.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import com.example.rodri.vocabbuilder.R;
import com.example.rodri.vocabbuilder.database.MyDataSource;

/**
 * Created by rodri on 2/28/2017.
 */

public class NeedToReviewActivity extends AppCompatActivity {

    private RecyclerView listOfWords;
    private MyDataSource dataSource;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_need_to_review);

        iniViews();
        dataSource = new MyDataSource(this);
    }

    private void iniViews() {
        listOfWords = (RecyclerView) findViewById(R.id.activityNeedToReview_listOfWords);
    }
}
