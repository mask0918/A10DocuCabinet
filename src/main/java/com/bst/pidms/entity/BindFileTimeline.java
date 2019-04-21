package com.bst.pidms.entity;

import java.io.Serializable;

public class BindFileTimeline implements Serializable {
    private Integer id;

    private Integer fileId;

    private Integer timelineId;

    private static final long serialVersionUID = 1L;

    public BindFileTimeline(Integer id, Integer fileId, Integer timelineId) {
        this.id = id;
        this.fileId = fileId;
        this.timelineId = timelineId;
    }

    public BindFileTimeline() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFileId() {
        return fileId;
    }

    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }

    public Integer getTimelineId() {
        return timelineId;
    }

    public void setTimelineId(Integer timelineId) {
        this.timelineId = timelineId;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", fileId=").append(fileId);
        sb.append(", timelineId=").append(timelineId);
        sb.append("]");
        return sb.toString();
    }
}