package com.bst.pidms.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Document(indexName = "label", type = "label", shards = 1, replicas = 0)
public class Label implements Serializable {
    @Id
    private Integer id;

    @Field(type = FieldType.Integer)
    private Integer userId;

    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String name;

    private Integer pid;

    @Field(type = FieldType.Boolean)
    private Boolean insight;

    private List<Label> nodes = new ArrayList<Label>();

    private static final long serialVersionUID = 1L;

    public Label(Integer id, Integer userId, String name, Boolean insight, Integer pid) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.pid = pid;
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

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public List<Label> getNodes() {
        return nodes;
    }

    public void setNodes(List<Label> nodes) {
        this.nodes = nodes;
    }

    @Override
    public String toString() {
        return "Label{" +
                "id=" + id +
                ", userId=" + userId +
                ", name='" + name + '\'' +
                ", pid=" + pid +
                ", insight=" + insight +
                ", nodes=" + nodes +
                '}';
    }
}