package com.example.quizappmasster.core.dto;

public class LinkDTO {

    private Integer id;

    private String title;

    private String url;

    private Integer drawable;

    public LinkDTO(Integer id, String title, String url, Integer drawable) {
        this.id = id;
        this.title = title;
        this.url = url;
        this.drawable = drawable;
    }

    public LinkDTO() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getDrawable() {
        return drawable;
    }

    public void setDrawable(Integer drawable) {
        this.drawable = drawable;
    }
}
