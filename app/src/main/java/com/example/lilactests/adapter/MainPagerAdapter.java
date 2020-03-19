package com.example.lilactests.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.lilactests.view.layoutfragment.MainPageFragment;
import com.example.lilactests.view.layoutfragment.NotesPageFragment;
import com.example.lilactests.view.layoutfragment.StatisticPageFragment;

/**
 *  Created by Eventory on 2017/2/11 0011.
 */

public class MainPagerAdapter extends FragmentPagerAdapter {
    private final String[] TITLES = { "主页", "笔记", "统计"};

    public MainPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return TITLES[position];
    }

    @Override
    public int getCount() {
        return TITLES.length;
    }

    @NonNull
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
