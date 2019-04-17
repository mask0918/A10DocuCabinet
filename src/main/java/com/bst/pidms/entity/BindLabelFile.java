package com.bst.pidms.entity;

import java.io.Serializable;

public class BindLabelFile implements Serializable {
    private Integer id;

    private Integer labelId;

    private Integer fileId;

    private Integer category;

    private static final long serialVersionUID = 1L;

    public BindLabelFile(Integer id, Integer labelId, Integer fileId, Integer category) {
        this.id = id;
        this.labelId = labelId;
        this.fileId = fileId;
        this.category = category;
    }

    public BindLabelFile() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getLabelId() {
        return labelId;
    }

    public void setLabelId(Integer labelId) {
        this.labelId = labelId;
    }

    public Integer getFileId() {
        return fileId;
    }

    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", labelId=").append(labelId);
        sb.append(", fileId=").append(fileId);
        sb.append(", category=").append(category);
        sb.append("]");
        return sb.toString();
    }
}