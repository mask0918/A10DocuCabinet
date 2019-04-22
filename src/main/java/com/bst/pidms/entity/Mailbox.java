package com.bst.pidms.entity;

import java.io.Serializable;

public class Mailbox implements Serializable {
    private Integer id;

    private String title;

    private Long time;

    private String sender;

    private String recipient;

    private Integer userId;

    private String fileIndex;

    private String content;

    private static final long serialVersionUID = 1L;

    public Mailbox(Integer id, String title, Long time, String sender, String recipient, Integer userId, String fileIndex, String content) {
        this.id = id;
        this.title = title;
        this.time = time;
        this.sender = sender;
        this.recipient = recipient;
        this.userId = userId;
        this.fileIndex = fileIndex;
        this.content = content;
    }

    public Mailbox() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender == null ? null : sender.trim();
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient == null ? null : recipient.trim();
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getFileIndex() {
        return fileIndex;
    }

    public void setFileIndex(String fileIndex) {
        this.fileIndex = fileIndex == null ? null : fileIndex.trim();
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
        sb.append(", title=").append(title);
        sb.append(", time=").append(time);
        sb.append(", sender=").append(sender);
        sb.append(", recipient=").append(recipient);
        sb.append(", userId=").append(userId);
        sb.append(", fileIndex=").append(fileIndex);
        sb.append(", content=").append(content);
        sb.append("]");
        return sb.toString();
    }
}