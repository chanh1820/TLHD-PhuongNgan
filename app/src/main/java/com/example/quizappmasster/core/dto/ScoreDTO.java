package com.example.quizappmasster.core.dto;

public class ScoreDTO {
    private int id;
    private  String userName;
    private  String displayName;
    private  String classRoom;
    private  String numTrue;
    private  Integer point ;
    private  String description ;
    private  String createdDate;
    private  Integer createdDateValue;
    private Integer type;


    public ScoreDTO(int id, String userName, String displayName,String classRoom, String numTrue, Integer point, String description, String createdDate, Integer type) {
        this.id = id;
        this.userName = userName;
        this.displayName = displayName;
        this.classRoom = classRoom;
        this.numTrue = numTrue;
        this.point = point;
        this.description = description;
        this.createdDate = createdDate;
        this.type = type;
    }

    public ScoreDTO(int id, String displayName, String numTrue, Integer point, String createdDate, Integer type) {
        this.id = id;
        this.displayName = displayName;
        this.numTrue = numTrue;
        this.point = point;
        this.createdDate = createdDate;
        this.type = type;
    }

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

    public String getNumTrue() {
        return numTrue;
    }

    public void setNumTrue(String numTrue) {
        this.numTrue = numTrue;
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
