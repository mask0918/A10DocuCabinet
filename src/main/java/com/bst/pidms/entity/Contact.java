package com.bst.pidms.entity;

import java.io.Serializable;

public class Contact implements Serializable {
    private Integer id;

    private Integer userId;

    private String remark;

    private String email;

    private static final long serialVersionUID = 1L;

    public Contact(Integer id, Integer userId, String remark, String email) {
        this.id = id;
        this.userId = userId;
        this.remark = remark;
        this.email = email;
    }

    public Contact() {
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", userId=").append(userId);
        sb.append(", remark=").append(remark);
        sb.append(", email=").append(email);
        sb.append("]");
        return sb.toString();
    }
}