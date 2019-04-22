package com.bst.pidms.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Document(indexName = "ownfile", type = "file", shards = 1, replicas = 0)
public class OwnFile implements Serializable {
    @Id
    private Integer id;

    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String name;

    @Field(type = FieldType.Keyword)
    private String url;

    @Field(type = FieldType.Integer)
    private Integer userId;

    @Field(type = FieldType.Integer)
    private Integer catalogId;

    @Field(type = FieldType.Integer)
    private Integer category;

    @Field(type = FieldType.Long)
    private Long size;

    @Field(type = FieldType.Long)
    private Long serverTime;

    @Field(type = FieldType.Long)
    private Long uploadTime;

    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String keyword;

    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String tag;

    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String info;

    @Field(type = FieldType.Integer)
    private Byte collection;

    @Field(type = FieldType.Integer)
    private Byte attention;

    @Field(type = FieldType.Integer)
    private Integer downloads;

    @Field(type = FieldType.Integer)
    private Integer views;

    @Field(type = FieldType.Integer)
    private Integer scale;

    @Field(type = FieldType.Integer)
    private Byte recycle;

    private List<Comment> comments;


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
        Date date = new Date(this.uploadTime);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return String.format("%d年%02d月", cal.get(Calendar.YEAR), cal.get(Calendar.MONTH));
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

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    @Override
    public String toString() {
        return "OwnFile{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", userId=" + userId +
                ", catalogId=" + catalogId +
                ", category=" + category +
                ", size=" + size +
                ", serverTime=" + serverTime +
                ", uploadTime=" + uploadTime +
                ", keyword='" + keyword + '\'' +
                ", tag='" + tag + '\'' +
                ", info='" + info + '\'' +
                ", collection=" + collection +
                ", attention=" + attention +
                ", downloads=" + downloads +
                ", views=" + views +
                ", scale=" + scale +
                ", recycle=" + recycle +
                ", comments=" + comments +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        OwnFile p = (OwnFile) obj;
        return this.id == p.id;
    }
}