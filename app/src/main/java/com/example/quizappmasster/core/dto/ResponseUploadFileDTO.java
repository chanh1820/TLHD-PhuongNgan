package com.example.quizappmasster.core.dto;

import java.util.List;

public class ResponseUploadFileDTO {

    private String statusCode;
    private List<String> data;

    public ResponseUploadFileDTO() {
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }
}
