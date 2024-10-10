package com.example.quizappmasster.core.dto;

import java.io.Serializable;

public class QuestionDTO implements Serializable {
    private  int _id;
    private  String question;
    private  String answerA;
    private  String answerB;
    private  String answerC;
    private  String answerD;
    private  String answerE;

    private Integer pointA;
    private Integer pointB;
    private Integer pointC;
    private Integer pointD;
    private Integer pointE;
    private  String topicCode;
    private  String image;
    private String traloi;
    private Integer pointTraloi;
    public int choiceID= -1; //hỗ trợ check Id của radiogroup

    public String getTraloi() {
        return traloi;
    }

    public void setTraloi(String traloi) {
        this.traloi = traloi;
    }

    public Integer getPointTraloi() {
        return pointTraloi;
    }

    public void setPointTraloi(Integer pointTraloi) {
        this.pointTraloi = pointTraloi;
    }

    public QuestionDTO() {
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswerA() {
        return answerA;
    }

    public void setAnswerA(String answerA) {
        this.answerA = answerA;
    }

    public String getAnswerB() {
        return answerB;
    }

    public void setAnswerB(String answerB) {
        this.answerB = answerB;
    }

    public String getAnswerC() {
        return answerC;
    }

    public void setAnswerC(String answerC) {
        this.answerC = answerC;
    }

    public String getAnswerD() {
        return answerD;
    }

    public void setAnswerD(String answerD) {
        this.answerD = answerD;
    }

    public String getAnswerE() {
        return answerE;
    }

    public void setAnswerE(String answerE) {
        this.answerE = answerE;
    }

    public Integer getPointA() {
        return pointA;
    }

    public void setPointA(Integer pointA) {
        this.pointA = pointA;
    }

    public Integer getPointB() {
        return pointB;
    }

    public void setPointB(Integer pointB) {
        this.pointB = pointB;
    }

    public Integer getPointC() {
        return pointC;
    }

    public void setPointC(Integer pointC) {
        this.pointC = pointC;
    }

    public Integer getPointD() {
        return pointD;
    }

    public void setPointD(Integer pointD) {
        this.pointD = pointD;
    }

    public Integer getPointE() {
        return pointE;
    }

    public void setPointE(Integer pointE) {
        this.pointE = pointE;
    }

    public String getTopicCode() {
        return topicCode;
    }

    public void setTopicCode(String topicCode) {
        this.topicCode = topicCode;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getChoiceID() {
        return choiceID;
    }

    public void setChoiceID(int choiceID) {
        this.choiceID = choiceID;
    }
}
