package com.example.quizappmasster.core.dto;

public class ScoreDTO {
    private int id;
    private  String userName;
    private  String displayName;
    private  String classRoom;
    private  Integer point ;
    private  String description ;
    private  String createdDate;
    private  Integer createdDateValue;
    private Integer type;

    public ScoreDTO() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getClassRoom() {
        return classRoom;
    }

    public void setClassRoom(String classRoom) {
        this.classRoom = classRoom;
    }

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public Integer getCreatedDateValue() {
        return createdDateValue;
    }

    public void setCreatedDateValue(Integer createdDateValue) {
        this.createdDateValue = createdDateValue;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
