package com.bst.pidms.entity;

import java.io.Serializable;

public class Catalog implements Serializable {
    private Integer id;

    private Integer userId;

    private String name;

    private Integer empty;

    private Integer pid;

    private static final long serialVersionUID = 1L;

    public Catalog(Integer id, Integer userId, String name, Integer empty, Integer pid) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.empty = empty;
        this.pid = pid;
    }

    public Catalog() {
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

    public Integer getEmpty() {
        return empty;
    }

    public void setEmpty(Integer empty) {
        this.empty = empty;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
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
        sb.append(", empty=").append(empty);
        sb.append(", pid=").append(pid);
        sb.append("]");
        return sb.toString();
    }
}