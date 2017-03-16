package com.junaid.cabpool;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by Junaid on 02-03-2017.
 */

public class Pager extends FragmentStatePagerAdapter {

    int mTabCount;

    public Pager(FragmentManager fm, int tabCount) {
        super(fm);
        this.mTabCount = tabCount;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:
                AvailableCabs tab1 = new AvailableCabs();
                return tab1;
            case 1:
                MyCabs tab2 = new MyCabs();
                return tab2;
            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return mTabCount;
    }
}
