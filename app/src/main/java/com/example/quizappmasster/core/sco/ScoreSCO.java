package com.example.quizappmasster.core.sco;

public class ScoreSCO {

    private String name;

    private String classRoom;

    private Integer minScore;

    private Integer maxScore;

    private Integer type;

    private Integer createValue;

    public ScoreSCO() {
    }

    public ScoreSCO(String name, String classRoom, Integer minScore, Integer maxScore, Integer type, Integer createValue) {
        this.name = name;
        this.classRoom = classRoom;
        this.minScore = minScore;
        this.maxScore = maxScore;
        this.type = type;
        this.createValue = createValue;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClassRoom() {
        return classRoom;
    }

    public void setClassRoom(String classRoom) {
        this.classRoom = classRoom;
    }

    public Integer getMinScore() {
        return minScore;
    }

    public void setMinScore(Integer minScore) {
        this.minScore = minScore;
    }

    public Integer getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(Integer maxScore) {
        this.maxScore = maxScore;
    }

    public Integer getCreateValue() {
        return createValue;
    }

    public void setCreateValue(Integer createValue) {
        this.createValue = createValue;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
