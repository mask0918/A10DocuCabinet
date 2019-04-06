package com.bst.pidms.entity.video;

import com.alibaba.fastjson.annotation.JSONType;

/**
 * @Author: BST
 * @Date: 2019/4/5 13:27
 */
@JSONType(orders = {"starttime", "endtime", "confidence"})
public class Segment {
    private Double starttime;
    private Double endtime;
    private Double confidence;

    public Double getStarttime() {
        return starttime;
    }

    public void setStarttime(Double starttime) {
        this.starttime = starttime;
    }

    public Double getEndtime() {
        return endtime;
    }

    public void setEndtime(Double endtime) {
        this.endtime = endtime;
    }

    public Double getConfidence() {
        return confidence;
    }

    public void setConfidence(Double confidence) {
        this.confidence = confidence;
    }
}
