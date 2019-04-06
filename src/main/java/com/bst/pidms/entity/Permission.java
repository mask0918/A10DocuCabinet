package com.bst.pidms.entity;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.elasticsearch.annotations.Document;

import java.io.Serializable;

public class Permission implements Serializable {
    private Integer id;

    private String url;

    private String name;

    private static final long serialVersionUID = 1L;

    public Permission(Integer id, String url, String name) {
        this.id = id;
        this.url = url;
        this.name = name;
    }

    public Permission() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", url=").append(url);
        sb.append(", name=").append(name);
        sb.append("]");
        return sb.toString();
    }
}