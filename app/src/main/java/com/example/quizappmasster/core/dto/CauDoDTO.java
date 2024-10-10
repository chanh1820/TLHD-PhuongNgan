package com.example.quizappmasster.core.dto;

import java.util.HashMap;
import java.util.Map;

public class CauDoDTO {
    private int id;

    private String cauHoi;

    private String dapan;

    private String imgUrl;

    public CauDoDTO() {
    }

    public CauDoDTO(String cauHoi, String dapan, String imgUrl) {
        this.cauHoi = cauHoi;
        this.imgUrl = imgUrl;
        this.dapan = dapan;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getDapan() {
        return dapan;
    }

    public void setDapan(String dapan) {
        this.dapan = dapan;
    }

    public String getCauHoi() {
        return cauHoi;
    }

    public void setCauHoi(String cauHoi) {
        this.cauHoi = cauHoi;
    }

    public Map<String,Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("dapan",dapan);
        result.put("imgUrl",imgUrl);
        return result;
    }
}
