package com.bst.pidms.entity;

import java.io.Serializable;

public class Label implements Serializable {
    private Integer id;

    private Integer userId;

    private String name;

    private Boolean insight;

    private static final long serialVersionUID = 1L;

    public Label(Integer id, Integer userId, String name, Boolean insight) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.insight = insight;
    }

    public Label() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Boolean getInsight() {
        return insight;
    }

    public void setInsight(Boolean insight) {
        this.insight = insight;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", userId=").append(userId);
        sb.append(", name=").append(name);
        sb.append(", insight=").append(insight);
        sb.append("]");
        return sb.toString();
    }
}