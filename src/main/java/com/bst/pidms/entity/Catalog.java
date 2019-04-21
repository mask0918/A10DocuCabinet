package com.bst.pidms.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Document(indexName = "catelog", type = "catalog", shards = 1, replicas = 0)
public class Catalog implements Serializable {
    @Id
    private Integer id;

    @Field(type = FieldType.Integer)
    private Integer userId;

    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String name;

    @Field(type = FieldType.Integer)
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