package com.example.quizappmasster.core.dto;

public class VersionDTO {

    private Integer version;

    private String updatedDate;

    private String description;

    public VersionDTO(Integer version, String updatedDate, String description) {
        this.version = version;
        this.updatedDate = updatedDate;
        this.description = description;
    }

    public VersionDTO() {

    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
