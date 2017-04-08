package com.example.lilactests.view.layoutfragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.lilactests.R;
import com.example.lilactests.SolvingProblemsActivity;
import com.example.lilactests.notes.MainActivity;

import butterknife.BindView;

/**
 *  Created by Eventory on 2017/2/12 0012.
 */

public class MainPageFragment extends Fragment implements View.OnClickListener{
    private static final String ARG_POSITION = "position";
    private static int nowPosition;
    View view = null;
    Button chapterExcBtn = null;
    @BindView(R.id.simulate_test_button)
    Button simulateTestBtn;

    public MainPageFragment() {
        super();
    }

    public static MainPageFragment newInstance(int position) {
        MainPageFragment mainPageFragment = new MainPageFragment();
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
        view = inflater.inflate(R.layout.page_main, container, false);
        chapterExcBtn = (Button) view.findViewById(R.id.chapter_exc_button);
        chapterExcBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SolvingProblemsActivity.class);
                startActivity(intent);
            }
        });
        simulateTestBtn = (Button) view.findViewById(R.id.simulate_test_button);
        simulateTestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.chapter_exc_button:
                Toast.makeText(getActivity(), "ChapterExc", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), SolvingProblemsActivity.class);
                startActivity(intent);
                break;
        }
    }
}
