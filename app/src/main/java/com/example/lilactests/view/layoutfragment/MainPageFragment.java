package com.example.lilactests.view.layoutfragment;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.lilactests.R;
import com.example.lilactests.SolvingProblemsActivity;
import com.example.lilactests.questions.MistakesActivity;

/**
 *  Created by Eventory on 2017/2/12 0012.
 */

public class MainPageFragment extends Fragment {
    private static final String ARG_POSITION = "position";
    private static int nowPosition;
    View view = null;
    Button chapterExcBtn = null;
    Button simulateTestBtn = null;
    Button wrongQuestBtn = null;

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
                intent.putExtra("problem amount", 5);
                startActivity(intent);
            }
        });
        simulateTestBtn = (Button) view.findViewById(R.id.simulate_test_button);
        simulateTestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SolvingProblemsActivity.class);
                intent.putExtra("problem amount", 10);
                startActivity(intent);
            }
        });
        wrongQuestBtn = (Button) view.findViewById(R.id.wrong_quest_button);
        wrongQuestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MistakesActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }

}
