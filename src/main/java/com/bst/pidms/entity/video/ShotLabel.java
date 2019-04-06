package com.bst.pidms.entity.video;

import com.alibaba.fastjson.annotation.JSONType;

import java.util.List;

/**
 * @Author: BST
 * @Date: 2019/4/5 13:44
 */
@JSONType(orders = {"shotlabel", "category", "segments"})
public class ShotLabel {
    private String shotlabel;
    private String category;
    private List<Segment> segments;

    public String getShotlabel() {
        return shotlabel;
    }

    public void setShotlabel(String shotlabel) {
        this.shotlabel = shotlabel;
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
