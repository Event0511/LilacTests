package com.example.lilactests.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.lilactests.model.domain.Question;
import com.example.lilactests.utils.DBService;
import com.example.lilactests.view.layoutfragment.ProblemPageFragment;

import java.util.List;

/**
 *  Created by Eventory on 2017/7/14.
 */

public class ProblemPagerAdapter extends FragmentPagerAdapter {
    private int amount;
    private List<Question> questionList;

    public ProblemPagerAdapter(FragmentManager fm, int amount) {
        super(fm);
        this.amount = amount;
        questionList = new DBService().getQuestion();
    }

    public void setSelectedAnswer(int id, int selectedAnswer) {
        questionList.get(id).selectedAnswer = selectedAnswer;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return position+"/"+amount;
    }

    @Override
    public int getCount() {
        return amount;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = ProblemPageFragment.newInstance(position, amount, questionList);
        return fragment;
    }
}
