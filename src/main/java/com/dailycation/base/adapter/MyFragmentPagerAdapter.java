package com.dailycation.base.adapter;

/**
 * Created by hehu on 16-12-26.
 */

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

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
