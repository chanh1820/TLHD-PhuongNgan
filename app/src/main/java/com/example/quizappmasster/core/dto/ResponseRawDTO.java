package com.example.quizappmasster.core.dto;

import java.io.Serializable;

public class ResponseRawDTO implements Serializable {

    private Integer status;

    private String message;


    public ResponseRawDTO(Integer status, String message) {
        this.status = status;
        this.message = message;
    }

    public ResponseRawDTO() {
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
