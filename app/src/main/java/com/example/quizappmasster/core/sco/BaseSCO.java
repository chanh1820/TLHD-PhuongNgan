package com.example.quizappmasster.core.sco;

import java.io.Serializable;

public class BaseSCO {

    boolean deleteFlag;

    int limit;

    public Boolean getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Boolean deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
}
