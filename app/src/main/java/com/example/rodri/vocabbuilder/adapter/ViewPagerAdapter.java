package com.example.rodri.vocabbuilder.adapter;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;

import com.example.rodri.vocabbuilder.fragment.PracticeFragment;
import com.example.rodri.vocabbuilder.fragment.SettingsFragment;
import com.example.rodri.vocabbuilder.fragment.StatisticsFragment;
import com.example.rodri.vocabbuilder.fragment.WordsFragment;

/**
 * Created by rodri on 2/4/2017.
 */

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    private CharSequence Titles[];
    private int[] imageResId;
    private int numOfTabs;
    private Activity activity;

    public ViewPagerAdapter(FragmentManager fm, int[] imageResId, int numOfTabs, Activity activity) {
        super(fm);

        this.imageResId = imageResId;
        this.numOfTabs = numOfTabs;
        this.activity = activity;
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

    /**
     * Implemented it following this tutorial: https://guides.codepath.com/android/Google-Play-Style-Tabs-using-TabLayout
     *
     * @param position
     * @return
     */
    @Override
    public CharSequence getPageTitle(int position) {

        //return Titles[position];
        Drawable image = ContextCompat.getDrawable(activity, imageResId[position]);
        image.setBounds(0, 0, 60, 60);
        SpannableString sb = new SpannableString(" ");
        ImageSpan imageSpan = new ImageSpan(image, ImageSpan.ALIGN_BOTTOM);
        sb.setSpan(imageSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return sb;
    }

}
