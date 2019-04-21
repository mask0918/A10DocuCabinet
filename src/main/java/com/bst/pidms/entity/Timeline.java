package com.bst.pidms.entity;

import java.io.Serializable;

public class Timeline implements Serializable {
    private Integer id;

    private String date;

    private static final long serialVersionUID = 1L;

    public Timeline(Integer id, String date) {
        this.id = id;
        this.date = date;
    }

    public Timeline() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date == null ? null : date.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", date=").append(date);
        sb.append("]");
        return sb.toString();
    }
}