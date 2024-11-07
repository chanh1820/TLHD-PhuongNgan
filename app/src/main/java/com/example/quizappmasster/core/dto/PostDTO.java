package com.example.quizappmasster.core.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PostDTO {
    private Integer id;

    private String topicCode;

    private String title;

    private String content;

    private String userId;

    private String author;
    private String fileame;

    private String createDate;
    private String updateDate;

    private boolean deleteFlag;

    private List<PostCommentDTO> postCommentDTOList;

    private List<PostInteractionDTO> postInteractionDTOList;

    private Integer countComment;

    private Integer countInteract;

    private Boolean isLike;

    public PostDTO() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTopicCode() {
        return topicCode;
    }

    public void setTopicCode(String topicCode) {
        this.topicCode = topicCode;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public boolean isDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(boolean deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public List<PostCommentDTO> getPostCommentDTOList() {
        return postCommentDTOList;
    }

    public void setPostCommentDTOList(List<PostCommentDTO> postCommentDTOList) {
        this.postCommentDTOList = postCommentDTOList;
    }

    public List<PostInteractionDTO> getPostInteractionDTOList() {
        return postInteractionDTOList;
    }

    public void setPostInteractionDTOList(List<PostInteractionDTO> postInteractionDTOList) {
        this.postInteractionDTOList = postInteractionDTOList;
    }

    public Integer getCountComment() {
        return countComment;
    }

    public void setCountComment(Integer countComment) {
        this.countComment = countComment;
    }

    public Integer getCountInteract() {
        return countInteract;
    }

    public void setCountInteract(Integer countInteract) {
        this.countInteract = countInteract;
    }

    public Boolean getIsLike() {
        return isLike;
    }

    public void setIsLike(Boolean isLike) {
        this.isLike = isLike;
    }
}
