package com.bst.pidms.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;

@Document(indexName = "comment", type = "comment", shards = 1, replicas = 0)
public class Comment implements Serializable {
    @Id
    private Integer id;

    @Field(type = FieldType.Long)
    private Long time;

    @Field(type = FieldType.Integer)
    private Integer fileId;

    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String content;

    private static final long serialVersionUID = 1L;

    public Comment(Integer id, Long time, Integer fileId, String content) {
        this.id = id;
        this.time = time;
        this.fileId = fileId;
        this.content = content;
    }

    public Comment() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public Integer getFileId() {
        return fileId;
    }

    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", time=").append(time);
        sb.append(", fileId=").append(fileId);
        sb.append(", content=").append(content);
        sb.append("]");
        return sb.toString();
    }
}