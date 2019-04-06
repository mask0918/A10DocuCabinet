package com.bst.pidms.entity;

import java.io.Serializable;

public class Manager implements Serializable {
    private Integer id;

    private String account;

    private String password;

    private static final long serialVersionUID = 1L;

    public Manager(Integer id, String account, String password) {
        this.id = id;
        this.account = account;
        this.password = password;
    }

    public Manager() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account == null ? null : account.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", account=").append(account);
        sb.append(", password=").append(password);
        sb.append("]");
        return sb.toString();
    }
}