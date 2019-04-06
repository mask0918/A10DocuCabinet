package com.bst.pidms.entity.video;

import com.alibaba.fastjson.annotation.JSONType;

import java.util.List;

/**
 * @Author: BST
 * @Date: 2019/4/5 13:23
 */
@JSONType(orders = {"videolabel", "category", "segments"})
public class VideoLabel {
    private String videolabel;
    private String category;
    private List<Segment> segments;

    public String getVideolabel() {
        return videolabel;
    }

    public void setVideolabel(String videolabel) {
        this.videolabel = videolabel;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<Segment> getSegments() {
        return segments;
    }

    public void setSegments(List<Segment> segments) {
        this.segments = segments;
    }
}
