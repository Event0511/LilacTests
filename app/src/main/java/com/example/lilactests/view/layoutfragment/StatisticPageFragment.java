package com.example.lilactests.view.layoutfragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lilactests.R;

/**
 *  Created by Eventory on 2017/2/12 0012.
 */

public class StatisticPageFragment extends Fragment {
    private static final String ARG_POSITION = "position";
    private static int nowPosition;

    public StatisticPageFragment() {
        super();
    }

    public static StatisticPageFragment newInstance(int position) {
        StatisticPageFragment mainPageFragment = new StatisticPageFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_POSITION, position);
        mainPageFragment.setArguments(bundle);
        return mainPageFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        nowPosition = getArguments().getInt(ARG_POSITION);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.page_statistic, container, false);

        return view;
    }

}
