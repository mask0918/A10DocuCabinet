package com.bst.pidms.entity.video;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.annotation.JSONType;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author: BST
 * @Date: 2019/4/15 15:01
 */
@JSONType(orders = {"height", "width", "duration", "code", "bitrate", "wordCloudUrl", "videoResults"})
public class VideoInfo {

    Integer height;

    Integer width;

    Integer duration;

    String code;

    String bitrate;

    String wordCloudUrl;

    String thumbUrl;

    VideoResults videoResults;

    @Override
    public String toString() {
        return "VideoInfo{" +
                "height=" + height +
                ", width=" + width +
                ", duration=" + duration +
                ", code='" + code + '\'' +
                ", bitrate='" + bitrate + '\'' +
                ", wordCloudUrl='" + wordCloudUrl + '\'' +
                ", thumbUrl='" + thumbUrl + '\'' +
                ", videoResults=" + videoResults +
                '}';
    }

    public String getThumbUrl() {
        return thumbUrl;
    }

    public void setThumbUrl(String thumbUrl) {
        this.thumbUrl = thumbUrl;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getBitrate() {
        return bitrate;
    }

    public void setBitrate(String bitrate) {
        this.bitrate = bitrate;
    }

    public String getWordCloudUrl() {
        return wordCloudUrl;
    }

    public void setWordCloudUrl(String wordCloudUrl) {
        this.wordCloudUrl = wordCloudUrl;
    }

    public VideoResults getVideoResults() {
        return videoResults;
    }

    public void setVideoResults(VideoResults videoResults) {
        this.videoResults = videoResults;
    }
}
