package com.example.rodri.vocabbuilder.fragment;

import android.os.Bundle;
import android.support.annotation.AnimatorRes;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.rodri.vocabbuilder.R;

/**
 * Created by rodri on 2/11/2017.
 */

public class CardContainerFragment extends Fragment {

    private long wordId;
    private ImageButton btShowAnswer;
    private boolean flipped = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
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
    public void onViewCreated(View view, Bundle savedInstanceState) {

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
        Fragment newFragment;
        Bundle args = new Bundle();
        args.putLong("wordId", wordId);
        if (flipped) {
            newFragment = new CardFrontFragment();
            newFragment.setArguments(args);
        } else {
            newFragment = new CardBackFragment();
            newFragment.setArguments(args);
        }

        getFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.animator.card_flip_right_in,
                        R.animator.card_flip_right_out,
                        R.animator.card_flip_left_in,
                        R.animator.card_flip_left_out)
                .replace(R.id.fragmentCardContainer_container, newFragment)
                .commit();

        // The problem might be because of the getSupportFragmentManager() in FlashcardGameActivity

        flipped = !flipped;
    }
}
