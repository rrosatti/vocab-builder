package com.example.rodri.vocabbuilder.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.rodri.vocabbuilder.R;
import com.example.rodri.vocabbuilder.activity.NewWordActivity;
import com.example.rodri.vocabbuilder.adapter.DetailedWordAdapter;
import com.example.rodri.vocabbuilder.database.MyDataSource;
import com.example.rodri.vocabbuilder.model.DetailedWord;
import com.example.rodri.vocabbuilder.model.Login;
import com.example.rodri.vocabbuilder.model.Word;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rodri on 2/4/2017.
 */

public class WordsFragment extends Fragment {

    private RecyclerView listOfWords;
    private FloatingActionButton btNewWord;
    private MyDataSource dataSource;
    private List<DetailedWord> words = new ArrayList<>();
    private DetailedWordAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab_words, container, false);

        iniViews(v);
        dataSource = new MyDataSource(getActivity());
        getDataFromDatabase();

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        listOfWords.setLayoutManager(llm);

        listOfWords.setAdapter(adapter);

        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                getDataFromDatabase();
                listOfWords.setAdapter(adapter);
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimaryDark, R.color.colorPrimary);

        btNewWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), NewWordActivity.class);
                startActivityForResult(i, 1);
            }
        });

    }

    private void iniViews(View v) {
        listOfWords = (RecyclerView) v.findViewById(R.id.tabWords_listWords);
        btNewWord = (FloatingActionButton) v.findViewById(R.id.tabWords_btNewWord);
        swipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.tabWords_swipeContainer);
    }

    private void getDataFromDatabase() {
        try {
            dataSource.open();

            long userId = Login.getInstance().getUserId();
            words = dataSource.getDetailedWords(userId);

            if (words != null) {
                adapter = new DetailedWordAdapter(getActivity(), this, words);
            } else {
                adapter = new DetailedWordAdapter(getActivity(), this, new ArrayList<DetailedWord>());
            }

            dataSource.close();
        } catch (Exception e) {
            e.printStackTrace();
            dataSource.close();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) {
            case Activity.RESULT_OK: {
                getDataFromDatabase();
                listOfWords.setAdapter(adapter);
            }
        }
    }

}
