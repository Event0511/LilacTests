package com.example.lilactests.questions;

import com.example.lilactests.model.domain.Question;

import java.util.List;

/**
 *  Created by wing on 2016/5/6.
 */
public interface QuestionsContract {
    interface Presenter {
        void showQuestionsList();

        void refreshQuestions();

        void deleteQuestion(Long id);

    }

    interface View {

        void showQuestions(List<Question> questionList);

        void refreshQuestions(List<Question> questionList);

        void showLoading();

        void cancelLoading();
    }
}
