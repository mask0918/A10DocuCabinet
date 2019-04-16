package com.bst.pidms.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Catalog implements Serializable {
    private Integer id;

    private Integer userId;

    private String name;

    private Integer pid;

    private List<Catalog> nodes = new ArrayList<Catalog>();

    private static final long serialVersionUID = 1L;

    public Catalog(Integer id, Integer userId, String name, Integer pid) {
        this.id = id;
        this.userId = userId;
        this.name = name;
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

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public List<Catalog> getNodes() {
        return nodes;
    }

    public void setNodes(List<Catalog> nodes) {
        this.nodes = nodes;
    }

    @Override
    public String toString() {
        return "Catalog{" +
                "id=" + id +
                ", userId=" + userId +
                ", name='" + name + '\'' +
                ", pid=" + pid +
                ", nodes=" + nodes +
                '}';
    }
}