package com.example.lilactests.questiondetail;

import android.content.Context;

import com.example.lilactests.model.Question.IQuestionModel;
import com.example.lilactests.model.Question.QuestionModel;
import com.example.lilactests.model.domain.Question;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

/**
 *  Created by Eventory on 2016/4/29.
 */
public class QuestionDetailPresenter implements QuestionDetailContract.Presenter {
    private IQuestionModel mQuestionModel;
    private QuestionDetailContract.View mQuestionUpdateView;

    public QuestionDetailPresenter(Context context, QuestionDetailContract.View iQuestionView) {
        this.mQuestionModel = new QuestionModel(context);
        this.mQuestionUpdateView = iQuestionView;
    }

    @Override
    public void getQuestion(final long id) {
        Observable.create(new Observable.OnSubscribe<Question>() {
            @Override
            public void call(Subscriber<? super Question> subscriber) {
                Question question = mQuestionModel.selectQuestion(id);
                subscriber.onNext(question);
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mQuestionUpdateView.showProcess();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Question>() {

                    @Override
                    public void onNext(Question question) {
                        mQuestionUpdateView.updateViews(question);
                        mQuestionUpdateView.dismissProcess();
                    }

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }

    @Override
    public void updateQuestion(List<Question> questionList) {
        for (Question q : questionList) {
            mQuestionModel.updateQuestion(q);
        }
    }
}
