package com.dailycation.base.adapter;

/**
 * Created by hehu on 16-12-26.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by hehu on 16-12-26.
 */
public class MyFragmentPagerAdapter extends FragmentPagerAdapter {
    private Fragment[] mmFragments;
    private FragmentManager fm;
    public MyFragmentPagerAdapter(FragmentManager fm, Fragment[] fragments) {
        super(fm);
        mmFragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return mmFragments[position];
    }

    @Override
    public int getCount() {
        return mmFragments == null?0:mmFragments.length;
    }

}
