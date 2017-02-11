package com.example.rodri.vocabbuilder.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.rodri.vocabbuilder.R;
import com.example.rodri.vocabbuilder.model.DetailedWord;

/**
 * Created by rodri on 2/11/2017.
 */

public class CardContainerFragment extends Fragment {

    private long wordId;
    private ImageButton btShowAnswer;
    private boolean flipped = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_card_container, container, false);

        iniViews(rootView);
        wordId = getArguments().getLong("wordId");

        Fragment cardFront = new CardFrontFragment();
        Bundle args = new Bundle();
        args.putLong("wordId", wordId);
        cardFront.setArguments(args);

        getChildFragmentManager()
                .beginTransaction().
                add(R.id.fragmentCardContainer_container, cardFront)
                .commit();

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        btShowAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flipCard();
            }
        });

    }

    private void iniViews(View v) {
        btShowAnswer = (ImageButton) v.findViewById(R.id.fragmentCardContainer_btShowAnswer);
    }

    private void flipCard() {
        Toast.makeText(getActivity(), "YEAH!", Toast.LENGTH_SHORT).show();
        // need to implement this method
        return null;
    }
}
