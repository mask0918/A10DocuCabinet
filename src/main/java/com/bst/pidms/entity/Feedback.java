package com.bst.pidms.entity;

import java.io.Serializable;

public class Feedback implements Serializable {
    private Integer id;

    private Long time;

    private String title;

    private String content;

    private Integer userId;

    private static final long serialVersionUID = 1L;

    public Feedback(Integer id, Long time, String title, String content, Integer userId) {
        this.id = id;
        this.time = time;
        this.title = title;
        this.content = content;
        this.userId = userId;
    }

    public Feedback() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", time=").append(time);
        sb.append(", title=").append(title);
        sb.append(", content=").append(content);
        sb.append(", userId=").append(userId);
        sb.append("]");
        return sb.toString();
    }
}