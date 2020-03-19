package com.example.lilactests.model.domain;


import java.io.Serializable;
import java.util.Date;

/**
 * Created by Eventory on 2017/5/17.
 * The entity of User.
 */
public class User implements Serializable {

    private long id;
    private long account;
    private String name;
    private String nickname;
    private String signature;
    private Date birthDay;
    private String gender;
    private String faculty;
    private String frequentCourse;
    private String password;
    private static final long serialVersionUID = 0L;

    private enum FrequentCourse {
        微积分, 集合论与图论, 英语;
    }

    private enum Faculty {
        计算机学院, 机电学院;
    }

    public User() {
    }

    public User(long id, long account, String name, String password, String nickname, String gender, String signature, Date birthDay, String faculty, String frequentCourse) {
        this.id = id;
        this.account = account;
        this.name = name;
        this.password = password;
        this.nickname = nickname;
        this.gender = gender;
        this.signature = signature;
        this.birthDay = birthDay;
        this.faculty = faculty;
        this.frequentCourse = frequentCourse;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getAccount() {
        return account;
    }

    public void setAccount(long account) {
        this.account = account;
    }
    public String getPassword(){
        return password;
    }
    public void setPassword(String password)
    {this.password = password;}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public Date getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public String getFrequentCourse() {
        return frequentCourse;
    }

    public void setFrequentCourse(String frequentCourse) {
        this.frequentCourse = frequentCourse;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

//    public User(long id, String title, String content, Date date, Date createTime) {
//        this.title = title;
//        this.content = content;
//        this.date = date;
//        this.id = id;
//        this.createDate = createTime;
//    }
//
//    public User(String title, String content, Date date, Date createTime) {
//        this.title = title;
//        this.content = content;
//        this.date = date;
//        this.createDate = createTime;
//    }

}
