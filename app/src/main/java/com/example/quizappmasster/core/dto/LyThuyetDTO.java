package com.example.quizappmasster.core.dto;

import android.content.Intent;

public class LyThuyetDTO {

    public Integer id;

    public String name;

    public String subject;

    public String fileName;

    private String subjectCode;

    public LyThuyetDTO(Integer id, String name, String subject, String fileName, String subjectCode) {
        this.id = id;
        this.name = name;
        this.subject = subject;
        this.fileName = fileName;
        this.subjectCode = subjectCode;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }
}
