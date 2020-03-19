package com.example.lilactests.questiondetail;

import com.example.lilactests.model.domain.Question;

import java.util.List;

/**
 *  Created by Eventory on 2016/5/6.
 */
public interface QuestionDetailContract {
    interface Presenter {
        void getQuestion(long id);

        void updateQuestion(List<Question> questionList);
    }

    interface View {
        void showProcess();

        void dismissProcess();

        void updateViews(Question questionItem);
    }
}
