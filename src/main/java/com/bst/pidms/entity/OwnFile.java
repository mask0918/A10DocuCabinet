package com.bst.pidms.entity;

import java.io.Serializable;

public class OwnFile implements Serializable {
    private Integer id;

    private Integer userId;

    private Integer catalogId;

    private Integer category;

    private Integer size;

    private Integer serverTime;

    private Integer uploadTime;

    private String keyword;

    private String tag;

    private String info;

    private Integer collection;

    private Integer attention;

    private Integer downloads;

    private Integer views;

    private Integer scale;

    private Integer recycle;

    private static final long serialVersionUID = 1L;

    public OwnFile(Integer id, Integer userId, Integer catalogId, Integer category, Integer size, Integer serverTime, Integer uploadTime, String keyword, String tag, String info, Integer collection, Integer attention, Integer downloads, Integer views, Integer scale, Integer recycle) {
        this.id = id;
        this.userId = userId;
        this.catalogId = catalogId;
        this.category = category;
        this.size = size;
        this.serverTime = serverTime;
        this.uploadTime = uploadTime;
        this.keyword = keyword;
        this.tag = tag;
        this.info = info;
        this.collection = collection;
        this.attention = attention;
        this.downloads = downloads;
        this.views = views;
        this.scale = scale;
        this.recycle = recycle;
    }

    public OwnFile() {
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

    public Integer getCatalogId() {
        return catalogId;
    }

    public void setCatalogId(Integer catalogId) {
        this.catalogId = catalogId;
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getServerTime() {
        return serverTime;
    }

    public void setServerTime(Integer serverTime) {
        this.serverTime = serverTime;
    }

    public Integer getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(Integer uploadTime) {
        this.uploadTime = uploadTime;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword == null ? null : keyword.trim();
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag == null ? null : tag.trim();
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info == null ? null : info.trim();
    }

    public Integer getCollection() {
        return collection;
    }

    public void setCollection(Integer collection) {
        this.collection = collection;
    }

    public Integer getAttention() {
        return attention;
    }

    public void setAttention(Integer attention) {
        this.attention = attention;
    }

    public Integer getDownloads() {
        return downloads;
    }

    public void setDownloads(Integer downloads) {
        this.downloads = downloads;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public Integer getScale() {
        return scale;
    }

    public void setScale(Integer scale) {
        this.scale = scale;
    }

    public Integer getRecycle() {
        return recycle;
    }

    public void setRecycle(Integer recycle) {
        this.recycle = recycle;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", userId=").append(userId);
        sb.append(", catalogId=").append(catalogId);
        sb.append(", category=").append(category);
        sb.append(", size=").append(size);
        sb.append(", serverTime=").append(serverTime);
        sb.append(", uploadTime=").append(uploadTime);
        sb.append(", keyword=").append(keyword);
        sb.append(", tag=").append(tag);
        sb.append(", info=").append(info);
        sb.append(", collection=").append(collection);
        sb.append(", attention=").append(attention);
        sb.append(", downloads=").append(downloads);
        sb.append(", views=").append(views);
        sb.append(", scale=").append(scale);
        sb.append(", recycle=").append(recycle);
        sb.append("]");
        return sb.toString();
    }
}