package com.example.lilactests.questions;

import com.example.lilactests.model.domain.Question;

import java.util.List;

/**
 * Created by Eventory on 2016/4/20.
 * the view interface for IListQuestionsPresenter
 */
public interface IQuestionShowView {

    void showQuestions(List<Question> questionList);

    void refreshQuestions(List<Question> questionList);

    void showLoading();

    void cancelLoading();
}
