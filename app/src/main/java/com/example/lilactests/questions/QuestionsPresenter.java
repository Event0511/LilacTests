package com.example.lilactests.questions;

import android.content.Context;
import android.os.SystemClock;

import com.example.lilactests.model.Question.IQuestionModel;
import com.example.lilactests.model.Question.QuestionModel;
import com.example.lilactests.model.domain.Question;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 *  Created by wing on 2016/4/20.
 */
public class QuestionsPresenter implements QuestionsContract.Presenter {
    private QuestionsContract.View mQuestionsShowView;
    private IQuestionModel mQuestionModel;
    public QuestionsPresenter(Context context, QuestionsContract.View questionsShowView) {
        this.mQuestionsShowView = questionsShowView;
        mQuestionModel = new QuestionModel(context);
    }

    private Observable.OnSubscribe<List> mGetQuestionCall = new Observable.OnSubscribe<List>() {
        @Override
        public void call(Subscriber<? super List> subscriber) {
            List<Question> questions = getQuestions();
            subscriber.onNext(questions);
            subscriber.onCompleted();
        }
    };

    @Override
    public void showQuestionsList() {
        Observable.create(mGetQuestionCall)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List>() {

                    @Override
                    public void onNext(List questions) {
                        mQuestionsShowView.showQuestions(questions);
                        mQuestionsShowView.cancelLoading();
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
    public void refreshQuestions() {
        Observable.create(mGetQuestionCall)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List>() {
                    @Override
                    public void onNext(List list) {
                        mQuestionsShowView.refreshQuestions(list);
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
    public void deleteQuestion(final Long id) {
        mQuestionModel.deleteQuestion(id);
        /*Observable.create(new Observable.OnSubscribe<Void>() {
            @Override
            public void call(Subscriber<? super Void> subscriber) {
                mQuestionModel.deleteQuestion(id);
            }
        }).subscribeOn(Schedulers.io());*/
    }

    private List<Question> getQuestions() {
        List<Question> questions = mQuestionModel.selectAll();
        List<Question> mistakes = new ArrayList<>();
        for (Question q : questions) {
            if (q.isMistake) {
                mistakes.add(q);
            }
        }
        SystemClock.sleep(1000);
        return mistakes;
    }
}
