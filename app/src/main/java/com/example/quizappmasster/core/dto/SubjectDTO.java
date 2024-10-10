package com.example.quizappmasster.core.dto;

import java.io.Serializable;

public class SubjectDTO implements Serializable {
    private Integer id;

    private String topicCode;

    private String topicName;

    private String numItem;

    private Integer minutesOfNumber;

    public SubjectDTO() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTopicCode() {
        return topicCode;
    }

    public void setTopicCode(String topicCode) {
        this.topicCode = topicCode;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public String getNumItem() {
        return numItem;
    }

    public void setNumItem(String numItem) {
        this.numItem = numItem;
    }

    public Integer getMinutesOfNumber() {
        return minutesOfNumber;
    }

    public void setMinutesOfNumber(Integer minutesOfNumber) {
        this.minutesOfNumber = minutesOfNumber;
    }
}
