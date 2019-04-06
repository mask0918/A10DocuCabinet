package com.bst.pidms.entity.video;

import com.alibaba.fastjson.annotation.JSONType;
import com.google.common.collect.Lists;

import java.util.List;

/**
 * @Author: BST
 * @Date: 2019/4/5 13:46
 */

@JSONType(orders = {"videoLabels", "shotLabels"})
public class VideoResults {
    private List<VideoLabel> videoLabels;
    private List<ShotLabel> shotLabels;

    public List<VideoLabel> getVideoLabels() {
        return videoLabels;
    }

    public void setVideoLabels(List<VideoLabel> videoLabels) {
        this.videoLabels = videoLabels;
    }

    public List<ShotLabel> getShotLabels() {
        return shotLabels;
    }

    public void setShotLabels(List<ShotLabel> shotLabels) {
        this.shotLabels = shotLabels;
    }
}
