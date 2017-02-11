package com.example.rodri.vocabbuilder.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.rodri.vocabbuilder.fragment.CardContainerFragment;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by rodri on 2/10/2017.
 */

public class CardPagerAdapter extends FragmentPagerAdapter {

    private List<Long> wordsIds = new ArrayList<>();

    // pass a list of DetailedWords as parameter?
    public CardPagerAdapter(FragmentManager fm, List<Long> wordsIds) {
        super(fm);
        this.wordsIds = wordsIds;
    }

    // 1 - create Fragment CardContainerFragment()
    // 2 - pass the detailed word as argument (setArguments())
    @Override
    public Fragment getItem(int position) {
        long wordId = wordsIds.get(position);
        Fragment cardContainerFragment = new CardContainerFragment();
        Bundle args = new Bundle();
        args.putLong("wordId", wordId);
        cardContainerFragment.setArguments(args);

        return cardContainerFragment;
    }

    // return the size of the list of detailed words
    @Override
    public int getCount() {
        return wordsIds.size();
    }
}
