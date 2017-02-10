package com.example.rodri.vocabbuilder.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


/**
 * Created by rodri on 2/10/2017.
 */

public class CardPagerAdapter extends FragmentPagerAdapter {

    // pass a list of DetailedWords as parameter?
    public CardPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    // 1 - create Fragment CardContainerFragment()
    // 2 - pass the detailed word as argument (setArguments())
    @Override
    public Fragment getItem(int position) {
        return new CardContainerFragment();
    }

    // return the size of the list of detailed words
    @Override
    public int getCount() {
        return 0;
    }
}
