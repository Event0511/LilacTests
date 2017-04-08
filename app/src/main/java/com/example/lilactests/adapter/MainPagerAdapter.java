package com.example.lilactests.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.lilactests.view.layoutfragment.MainPageFragment;
import com.example.lilactests.view.layoutfragment.NotesPageFragment;
import com.example.lilactests.view.layoutfragment.StatisticPageFragment;

/**
 *  Created by Eventory on 2017/2/11 0011.
 */

public class MainPagerAdapter extends FragmentPagerAdapter {

    private final String[] TITLES = { "主页", "笔记", "统计"};

    public MainPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return TITLES[position];
    }

    @Override
    public int getCount() {
        return TITLES.length;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment;
        switch (position) {
            case 0:
                fragment = MainPageFragment.newInstance(position);
                break;
            case 1:
                fragment = NotesPageFragment.newInstance(position);
                break;
            case 2:
                fragment = StatisticPageFragment.newInstance(position);
                break;
            default:
                fragment = MainPageFragment.newInstance(position);
                break;
        }
        return fragment;
    }
}
