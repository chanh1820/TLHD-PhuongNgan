package com.example.quizappmasster.core.sco;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public class PostSCO extends BaseSCO{

    @JsonIgnoreProperties(ignoreUnknown = true)
    private Integer id;

    @JsonIgnoreProperties(ignoreUnknown = true)
    private String topicCode;

    @JsonIgnoreProperties(ignoreUnknown = true)
    private String userId;

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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
