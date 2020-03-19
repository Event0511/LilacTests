package com.example.lilactests.model.domain;


import java.io.Serializable;
import java.util.Date;

/**
 * Created by Eventory on 2017/5/17.
 * The entity of QuestionItem.
 */
public class Question implements Serializable {
    //编号
    public long ID;
    //标识码
    public long idCode;
    //题目
    public String subject;
    //四个选项
    public String answerA;
    public String answerB;
    public String answerC;
    public String answerD;
    //详情
    public String explanation;
    //用户选中的答案
    public int selectedAnswer;
    //答案
    public int answer;
    //完成日期和复习日期
    public Date finishDate;
    public Date reviewDate;
    //是否收藏
    public boolean isFavorite;
    //
    public boolean isMistake;

    public int likeCount;
    public int dislikeCount;
    public Course course;
    private static final long serialVersionUID = 0L;

    enum Course {
        微积分, 集合论与图论, 英语;
    }

    public Question() {
    }

    public Question(long ID, long idCode, String subject, int answer, int selectedAnswer, String answerA, String answerB, String answerC, String answerD, String explanation, Date finishDate, Date reviewDate, boolean isFavorite, boolean isMistake) {
        this.ID = ID;
        this.idCode = idCode;
        this.subject = subject;
        this.answer = answer;
        this.selectedAnswer = selectedAnswer;
        this.answerA = answerA;
        this.answerB = answerB;
        this.answerC = answerC;
        this.answerD = answerD;
        this.explanation = explanation;
        this.finishDate = finishDate;
        this.reviewDate = reviewDate;
        this.isFavorite = isFavorite;
        this.isMistake = isMistake;
    }

    public Question(Date finishDate, Date reviewDate) {
        this.finishDate = finishDate;
        this.reviewDate = reviewDate;
    }

}
