package com.bst.pidms.entity;

import org.springframework.data.elasticsearch.annotations.Document;

import java.io.Serializable;

@Document(indexName = "0405", type = "tb_user11")
public class User implements Serializable {
    private Integer id;

    private String username;

    private String password;

    private Integer roleId;

    private String mailAcc;

    private String mailPwd;

    private String smtp;

    private String pop3;

    private static final long serialVersionUID = 1L;

    public User(Integer id, String username, String password, Integer roleId, String mailAcc, String mailPwd, String smtp, String pop3) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.roleId = roleId;
        this.mailAcc = mailAcc;
        this.mailPwd = mailPwd;
        this.smtp = smtp;
        this.pop3 = pop3;
    }

    public User() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getMailAcc() {
        return mailAcc;
    }

    public void setMailAcc(String mailAcc) {
        this.mailAcc = mailAcc == null ? null : mailAcc.trim();
    }

    public String getMailPwd() {
        return mailPwd;
    }

    public void setMailPwd(String mailPwd) {
        this.mailPwd = mailPwd == null ? null : mailPwd.trim();
    }

    public String getSmtp() {
        return smtp;
    }

    public void setSmtp(String smtp) {
        this.smtp = smtp == null ? null : smtp.trim();
    }

    public String getPop3() {
        return pop3;
    }

    public void setPop3(String pop3) {
        this.pop3 = pop3 == null ? null : pop3.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", username=").append(username);
        sb.append(", password=").append(password);
        sb.append(", roleId=").append(roleId);
        sb.append(", mailAcc=").append(mailAcc);
        sb.append(", mailPwd=").append(mailPwd);
        sb.append(", smtp=").append(smtp);
        sb.append(", pop3=").append(pop3);
        sb.append("]");
        return sb.toString();
    }
}