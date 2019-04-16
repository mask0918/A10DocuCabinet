package com.bst.pidms.entity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class OwnFile implements Serializable {
    private Integer id;

    private String name;

    private String url;

    private Integer userId;

    private Integer catalogId;

    private Integer category;

    private Long size;

    private Long serverTime;

    private Long uploadTime;

    private String keyword;

    private String tag;

    private String info;

    private Byte collection;

    private Byte attention;

    private Integer downloads;

    private Integer views;

    private Integer scale;

    private Byte recycle;


    private static final long serialVersionUID = 1L;

    public OwnFile(Integer id, String name, String url, Integer userId, Integer catalogId, Integer category, Long size, Long serverTime, Long uploadTime, String keyword, String tag, String info, Byte collection, Byte attention, Integer downloads, Integer views, Integer scale, Byte recycle) {
        this.id = id;
        this.name = name;
        this.url = url;
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

    public String sortByMonth() {
        Date date = new Date(uploadTime);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return String.format("%d年%2d月", cal.get(Calendar.YEAR), cal.get(Calendar.MONTH));

//        return cal.get(Calendar.YEAR) * 100 + cal.get(Calendar.MONTH);
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
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

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public Long getServerTime() {
        return serverTime;
    }

    public void setServerTime(Long serverTime) {
        this.serverTime = serverTime;
    }

    public Long getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(Long uploadTime) {
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

    public Byte getCollection() {
        return collection;
    }

    public void setCollection(Byte collection) {
        this.collection = collection;
    }

    public Byte getAttention() {
        return attention;
    }

    public void setAttention(Byte attention) {
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

    public Byte getRecycle() {
        return recycle;
    }

    public void setRecycle(Byte recycle) {
        this.recycle = recycle;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", url=").append(url);
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