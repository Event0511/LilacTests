package com.example.lilactests.db;

import android.graphics.Bitmap;

import org.litepal.crud.DataSupport;

/**
 *  Created by Eventory on 2017/2/15 0015.
 */

public class MultipleChoice extends DataSupport {
    private int id;
    private String course; //科目
    private int optionsCount; //选项数目
    private int rightOption; //正确选项
    private String question; //题干
    private String optionStatement; //选项描述
    private Bitmap[] questionImg; //题干中的图片
    private Bitmap[] optionImg; //选项中的图片

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public int getOptionsCount() {
        return optionsCount;
    }

    public void setOptionsCount(int optionsCount) {
        this.optionsCount = optionsCount;
    }

    public int getRightOption() {
        return rightOption;
    }

    public void setRightOption(int rightOption) {
        this.rightOption = rightOption;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getOptionStatement() {
        return optionStatement;
    }

    public void setOptionStatement(String optionStatement) {
        this.optionStatement = optionStatement;
    }

    public Bitmap[] getQuestionImg() {
        return questionImg;
    }

    public void setQuestionImg(Bitmap[] questionImg) {
        this.questionImg = questionImg;
    }

    public Bitmap[] getOptionImg() {
        return optionImg;
    }

    public void setOptionImg(Bitmap[] optionImg) {
        this.optionImg = optionImg;
    }
}
