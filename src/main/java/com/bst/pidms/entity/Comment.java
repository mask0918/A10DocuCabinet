package com.bst.pidms.entity;

import java.io.Serializable;

public class Comment implements Serializable {
    private Integer id;

    private Integer time;

    private Integer fileId;

    private String content;

    private static final long serialVersionUID = 1L;

    public Comment(Integer id, Integer time, Integer fileId, String content) {
        this.id = id;
        this.time = time;
        this.fileId = fileId;
        this.content = content;
    }

    public Comment() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public Integer getFileId() {
        return fileId;
    }

    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", time=").append(time);
        sb.append(", fileId=").append(fileId);
        sb.append(", content=").append(content);
        sb.append("]");
        return sb.toString();
    }
}