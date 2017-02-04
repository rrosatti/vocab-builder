package com.example.rodri.vocabbuilder.activity;

import android.support.v4.view.ViewPager;
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
    private int numOfTabs = Titles.length;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iniViews();
        setSupportActionBar(toolbar);

        pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), Titles, numOfTabs);

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
}
