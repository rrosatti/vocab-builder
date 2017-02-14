package com.example.rodri.vocabbuilder.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.rodri.vocabbuilder.R;
import com.example.rodri.vocabbuilder.database.MyDataSource;
import com.example.rodri.vocabbuilder.model.DetailedWord;
import com.example.rodri.vocabbuilder.model.Word;

/**
 * Created by rodri on 2/12/2017.
 */

public class CardBackFragment extends Fragment {

    private TextView txtTrans1;
    private TextView txtTrans2;
    private TextView txtTrans3;
    private long wordId;
    private MyDataSource dataSource;
    private DetailedWord dWord;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_card_back, container, false);

        iniViews(view);
        dataSource = new MyDataSource(getActivity());

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        wordId = getArguments().getLong("wordId");
        getWord();
        fillViews();

    }

    private void iniViews(View v) {
        txtTrans1 = (TextView) v.findViewById(R.id.fragmentCardBack_txtTrans1);
        txtTrans2 = (TextView) v.findViewById(R.id.fragmentCardBack_txtTrans2);
        txtTrans3 = (TextView) v.findViewById(R.id.fragmentCardBack_txtTrans3);
    }

    private void getWord() {
        try {
            dataSource.open();

            dWord = dataSource.getDetailedWord(wordId);

            dataSource.close();
        } catch (Exception e) {
            e.printStackTrace();
            dataSource.close();
        }
    }

    private void fillViews() {
        Word w = dWord.getWord();

        if (!w.getTranslation1().isEmpty()) {
            txtTrans1.setText(w.getTranslation1());
            txtTrans1.setVisibility(View.VISIBLE);
        }
        if (!w.getTranslation2().isEmpty()) {
            txtTrans2.setText(w.getTranslation2());
            txtTrans2.setVisibility(View.VISIBLE);
        }
        if (!w.getTranslation3().isEmpty()) {
            txtTrans3.setText(w.getTranslation3());
            txtTrans3.setVisibility(View.VISIBLE);
        }
    }
}
