package com.bst.pidms.entity;

import java.io.Serializable;

public class History implements Serializable {
    private Integer id;

    private Integer time;

    private String record;

    private Integer userId;

    private static final long serialVersionUID = 1L;

    public History(Integer id, Integer time, String record, Integer userId) {
        this.id = id;
        this.time = time;
        this.record = record;
        this.userId = userId;
    }

    public History() {
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

    public String getRecord() {
        return record;
    }

    public void setRecord(String record) {
        this.record = record == null ? null : record.trim();
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
        sb.append(", record=").append(record);
        sb.append(", userId=").append(userId);
        sb.append("]");
        return sb.toString();
    }
}