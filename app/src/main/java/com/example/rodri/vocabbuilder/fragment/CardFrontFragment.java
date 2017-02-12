package com.example.rodri.vocabbuilder.fragment;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rodri.vocabbuilder.R;
import com.example.rodri.vocabbuilder.database.MyDataSource;
import com.example.rodri.vocabbuilder.model.DetailedWord;

/**
 * Created by rodri on 2/9/2017.
 */

public class CardFrontFragment extends Fragment {

    private ImageView imgLang;
    private TextView txtWord;
    private DetailedWord dWord;
    private long wordId;
    private MyDataSource dataSource;
    private TypedArray langFlags;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_card_front, container, false);

        iniViews(v);
        dataSource = new MyDataSource(getActivity());
        wordId = getArguments().getLong("wordId", 0);

        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        getWord();
        langFlags = getActivity().getResources().obtainTypedArray(R.array.language_flags);

        imgLang.setImageResource(langFlags.getResourceId((int) dWord.getLanguage().getId() - 1, 0));
        txtWord.setText(dWord.getWord().getName());

    }

    private void iniViews(View v) {
        imgLang =  (ImageView) v.findViewById(R.id.fragmentCardFront_imgLang);
        txtWord = (TextView) v.findViewById(R.id.fragmentCardFront_txtWord);
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


}
