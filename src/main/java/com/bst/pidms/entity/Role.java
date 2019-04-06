package com.bst.pidms.entity;

import java.io.Serializable;

public class Role implements Serializable {
    private Integer id;

    private String name;

    private Integer storage;

    private static final long serialVersionUID = 1L;

    public Role(Integer id, String name, Integer storage) {
        this.id = id;
        this.name = name;
        this.storage = storage;
    }

    public Role() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getStorage() {
        return storage;
    }

    public void setStorage(Integer storage) {
        this.storage = storage;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", storage=").append(storage);
        sb.append("]");
        return sb.toString();
    }
}