package com.example.lilactests.model.Question;

import com.example.lilactests.model.domain.Question;

import java.util.List;

/**
 * Created by Eventory on 2016/4/12.
 * QuestionModel Interface
 */
public interface IQuestionModel {
    boolean addQuestion(Question question);

    boolean updateQuestion(Question question);

    void deleteQuestion(Long id);

    List<Question> selectAll();

    Question selectQuestion(long id);
}
