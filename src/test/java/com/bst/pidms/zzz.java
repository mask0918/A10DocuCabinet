package com.bst.pidms;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bst.pidms.entity.User;
import com.bst.pidms.entity.video.*;
import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: BST
 * @Date: 2019/4/5 12:03
 */
public class zzz {
    public static void main(String[] args) {
        Segment segment = new Segment();
        segment.setStarttime(0.000);
        segment.setEndtime(309.27);
        segment.setConfidence(0.98493);
        VideoLabel videoLabel = new VideoLabel();
        videoLabel.setVideolabel("摩天大楼");
        videoLabel.setCategory("建造");
        videoLabel.setSegments(Lists.newArrayList(segment));

        Segment segment1 = new Segment();
        segment1.setStarttime(162.839);
        segment1.setEndtime(167.29);
        segment1.setConfidence(0.5324);
        ShotLabel shotLabel = new ShotLabel();
        videoLabel.setCategory("生物学");
        shotLabel.setSegments(Lists.newArrayList(segment1));

        VideoResults videoResults = new VideoResults();
        videoResults.setVideoLabels(Lists.newArrayList(videoLabel));
        videoResults.setShotLabels(Lists.newArrayList(shotLabel));


        String s1 = JSONObject.toJSONString(videoResults);
        System.out.println(s1);
    }
}
