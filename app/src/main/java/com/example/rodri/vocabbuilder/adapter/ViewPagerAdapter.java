package com.example.rodri.vocabbuilder.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.rodri.vocabbuilder.fragment.PracticeFragment;
import com.example.rodri.vocabbuilder.fragment.SettingsFragment;
import com.example.rodri.vocabbuilder.fragment.StatisticsFragment;
import com.example.rodri.vocabbuilder.fragment.WordsFragment;

/**
 * Created by rodri on 2/4/2017.
 */

public class ViewPagerAdapter extends FragmentStatePagerAdapter  implements IconTabProvider{

    private CharSequence Titles[];
    private int numOfTabs;

    public ViewPagerAdapter(FragmentManager fm, CharSequence Titles[], int numOfTabs) {
        super(fm);

        this.Titles = Titles;
        this.numOfTabs = numOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: {
                WordsFragment wordsFragment = new WordsFragment();
                return wordsFragment;
            }
            case 1: {
                PracticeFragment practiceFragment = new PracticeFragment();
                return practiceFragment;
            }
            case 2: {
                StatisticsFragment statisticsFragment = new StatisticsFragment();
                return statisticsFragment;
            }
            case 3: {
                SettingsFragment settingsFragment = new SettingsFragment();
                return settingsFragment;
            }
            default: {
                return null;
            }
        }
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return Titles[position];
    }

}
