package com.junaid.cabpool;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    public static final String mOrganizerName = "Junaid Tinwala";
    public static final String mEmail = "jm950@snu.edu.in";
    public static final String PREFS_NAME = "Cab_Prefs";

    private ViewPager mViewPager;
    private TabLayout mTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTabLayout = (TabLayout) findViewById(R.id.tabLayout);

        //Adding the tabs using addTab() method
        mTabLayout.addTab(mTabLayout.newTab().setText("Available Cabs"));
        mTabLayout.addTab(mTabLayout.newTab().setText("My Cabs"));
        mTabLayout.setHorizontalFadingEdgeEnabled(true);
        mTabLayout.setHorizontalScrollBarEnabled(true);


        mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
       // mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);


        //Initializing mViewPager
        mViewPager = (ViewPager) findViewById(R.id.pager);

        //Creating our pager adapter
        Pager adapter = new Pager(getSupportFragmentManager(), mTabLayout.getTabCount());

        //Adding adapter to pager
        mViewPager.setAdapter(adapter);
        mViewPager.getCurrentItem();

        //Adding onTabSelectedListener to swipe views
        mTabLayout.setOnTabSelectedListener(new Listener());
    }

    private class Listener implements TabLayout.OnTabSelectedListener{

        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            mViewPager.setCurrentItem(tab.getPosition());

        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {

        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {

        }
    }
}
