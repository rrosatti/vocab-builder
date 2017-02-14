package com.example.rodri.vocabbuilder.fragment;

import android.os.Bundle;
import android.support.annotation.AnimatorRes;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

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

        return rootView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        wordId = getArguments().getLong("wordId");

        Fragment cardFront = new CardFrontFragment();
        Bundle args = new Bundle();
        args.putLong("wordId", wordId);
        cardFront.setArguments(args);

        getChildFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentCardContainer_container, cardFront, "fragmentFront")
                .commit();


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

        if (flipped) {
            btShowAnswer.setImageResource(R.drawable.back);
        }
    }

    private void flipCard() {

        Fragment newFragment;
        String tag;
        Bundle args = new Bundle();
        args.putLong("wordId", wordId);
        if (flipped) {
            newFragment = new CardFrontFragment();
            newFragment.setArguments(args);
            tag = "fragmentFront";
            btShowAnswer.setImageBitmap(null);
            btShowAnswer.setImageResource(R.drawable.button_show_answer);
        } else {
            newFragment = new CardBackFragment();
            newFragment.setArguments(args);
            tag = "fragmentBack";
            btShowAnswer.setImageBitmap(null);
            btShowAnswer.setImageResource(R.drawable.back);
        }

        getChildFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.animator.card_flip_right_in,
                        R.animator.card_flip_right_out,
                        R.animator.card_flip_left_in,
                        R.animator.card_flip_left_out)
                .replace(R.id.fragmentCardContainer_container, newFragment, tag)
                .commit();


        flipped = !flipped;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        Toast.makeText(getActivity(), "saveInstanceState()", Toast.LENGTH_SHORT).show();
    }
}
