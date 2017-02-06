package com.example.rodri.vocabbuilder.activity;

import android.content.res.TypedArray;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.example.rodri.vocabbuilder.R;
import com.example.rodri.vocabbuilder.adapter.ViewPagerAdapter;
import com.example.rodri.vocabbuilder.tabs.SlidingTabLayout;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ViewPager pager;
    private ViewPagerAdapter pagerAdapter;
    private SlidingTabLayout tabs;
    private CharSequence Titles[] = {"Words", "Practice", "Statistics", "Settings"};
    private int[] tabsIconsId;
    private int numOfTabs = Titles.length;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iniViews();
        setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayShowTitleEnabled(false);

        TypedArray tabsIcons = getResources().obtainTypedArray(R.array.tab_icons);
        getTabsIconId(tabsIcons);
        pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), tabsIconsId, numOfTabs, this);

        pager.setAdapter(pagerAdapter);

        // This make the tabs space evenly in available width
        tabs.setDistributeEvenly(true);

        // Setting custom color for the scroll bar indicator
        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.colorAccent);
            }
        });

        // Setting the ViewPager for the SlidingTabsLayout
        tabs.setViewPager(pager);

    }

    private void iniViews() {
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        pager = (ViewPager) findViewById(R.id.pager);
        tabs = (SlidingTabLayout) findViewById(R.id.tabs);
    }

    private void getTabsIconId(TypedArray tabsIcons) {
        tabsIconsId = new int[tabsIcons.length()];
        for (int i = 0; i < tabsIcons.length(); i++) {
            tabsIconsId[i] = tabsIcons.getResourceId(i, 0);
        }
    }

}
