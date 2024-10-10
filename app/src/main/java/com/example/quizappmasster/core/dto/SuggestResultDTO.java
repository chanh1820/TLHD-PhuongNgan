package com.example.quizappmasster.core.dto;

public class SuggestResultDTO {

    String type;
    String value;
    String name;

    public SuggestResultDTO(String type, String value, String name) {
        this.type = type;
        this.value = value;
        this.name = name;
    }

    public SuggestResultDTO() {

    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
