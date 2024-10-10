package com.example.quizappmasster.core.dto;


import lombok.Data;

import java.time.LocalDateTime;


public class TopicDTO {
    private Integer id;

    private String code;

    private String name;

    private LocalDateTime createDate;

    private boolean deleteFlag;
}
