package com.bst.pidms.entity.video;

import com.bst.pidms.utils.*;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.api.gax.longrunning.OperationFuture;
import com.google.cloud.videointelligence.v1.*;
import com.google.protobuf.ByteString;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: BST
 * @Date: 2019/4/5 14:10
 */
public class GoogleVideo {

    public static VideoResults Video_Java(String filePath) throws Exception, IOException {
        String proxyHost = "127.0.0.1";
        String proxyPort = "1080";

        System.setProperty("proxyHost", proxyHost);
        System.setProperty("proxyPort", proxyPort);

        List<VideoLabel> videoLabels = new ArrayList<>();
        List<ShotLabel> shotLabels = new ArrayList<>();
        VideoResults videoResults = new VideoResults();

        // Instantiate a com.GoogleVideo.cloud.videointelligence.v1.VideoIntelligenceServiceClient
        try (VideoIntelligenceServiceClient client = VideoIntelligenceServiceClient.create()) {
            // Read file and encode into Base64
            Path path = Paths.get(filePath);
            byte[] data = Files.readAllBytes(path);

            AnnotateVideoRequest request = AnnotateVideoRequest.newBuilder()
                    .setInputContent(ByteString.copyFrom(data))
                    .addFeatures(com.google.cloud.videointelligence.v1.Feature.LABEL_DETECTION)
                    .build();

            // Create an operation that will contain the response when the operation completes.
            OperationFuture<AnnotateVideoResponse, AnnotateVideoProgress> response =
                    client.annotateVideoAsync(request);

            System.out.println("Waiting for operation to complete...");


            VideoAnnotationResults results = response.get().getAnnotationResults(0);
            // process video / segment level label annotations
            System.out.println("Locations: ");
            for (LabelAnnotation labelAnnotation : results.getSegmentLabelAnnotationsList()) {

                VideoLabel videoLabel = new VideoLabel();
                videoLabel.setVideolabel((labelAnnotation.getEntity().getDescription()));
                List<Segment> segments = new ArrayList<>();

//                // categories
//                if (labelAnnotation.getCategoryEntitiesCount() > 0) {
//                    Entity categoryEntity = labelAnnotation.getCategoryEntities(0);
//                    videoLabel.setCategory(categoryEntity.getDescription());
//                }

                // segments
                for (LabelSegment segment : labelAnnotation.getSegmentsList()) {
                    // 抛弃置信度<0.5的分割
                    if (segment.getConfidence() < 0.5) continue;
                    double startTime = segment.getSegment().getStartTimeOffset().getSeconds()
                            + segment.getSegment().getStartTimeOffset().getNanos() / 1e9;
                    double endTime = segment.getSegment().getEndTimeOffset().getSeconds()
                            + segment.getSegment().getEndTimeOffset().getNanos() / 1e9;
                    Segment segment1 = new Segment();
                    segment1.setStarttime(startTime);
                    segment1.setEndtime(endTime);
                    segment1.setConfidence(Double.parseDouble(String.valueOf(segment.getConfidence())));
                    segments.add(segment1);
                }
                if (segments.size() < 1) segments = null;
                videoLabel.setSegments(segments);
                videoLabels.add(videoLabel);
            }

            // process shot label annotations
            for (LabelAnnotation labelAnnotation : results.getShotLabelAnnotationsList()) {
                ShotLabel shotLabel = new ShotLabel();
                shotLabel.setShotlabel((labelAnnotation.getEntity().getDescription()));
                List<Segment> segments = new ArrayList<>();

//                // categories
//                if (labelAnnotation.getCategoryEntitiesCount() > 0) {
//                    Entity categoryEntity = labelAnnotation.getCategoryEntities(0);
//                    shotLabel.setCategory(categoryEntity.getDescription());
//                }

                // segments
                for (LabelSegment segment : labelAnnotation.getSegmentsList()) {
                    //                    抛弃置信度<0.5的分割
                    if (segment.getConfidence() < 0.5) continue;
                    double startTime = segment.getSegment().getStartTimeOffset().getSeconds()
                            + segment.getSegment().getStartTimeOffset().getNanos() / 1e9;
                    double endTime = segment.getSegment().getEndTimeOffset().getSeconds()
                            + segment.getSegment().getEndTimeOffset().getNanos() / 1e9;
                    Segment segment1 = new Segment();
                    segment1.setStarttime(startTime);
                    segment1.setEndtime(endTime);
                    segment1.setConfidence(Double.parseDouble(String.valueOf(segment.getConfidence())));
                    segments.add(segment1);
                }
                if (segments.size() < 1) segments = null;
                shotLabel.setSegments(segments);
                shotLabels.add(shotLabel);
            }

            videoResults.setVideoLabels(videoLabels);
            videoResults.setShotLabels(shotLabels);
        }
        System.clearProperty("proxyHost");
        System.clearProperty("proxyPort");

        List<VideoLabel> v = videoResults.getVideoLabels();
        List<ShotLabel> s = videoResults.getShotLabels();
        for (VideoLabel videoLabel : v) {
            videoLabel.setVideolabel(TranslateUtils.BaiduTranslate(videoLabel.getVideolabel()));
//            if (videoLabel.getCategory() != null)
//                videoLabel.setCategory(TranslateUtils.BaiduTranslate(videoLabel.getCategory()));
        }
        for (ShotLabel shotLabel : s) {
            shotLabel.setShotlabel(TranslateUtils.BaiduTranslate(shotLabel.getShotlabel()));
//            if (shotLabel.getCategory() != null)
//                shotLabel.setCategory(TranslateUtils.BaiduTranslate(shotLabel.getCategory()));
        }
        return videoResults;

    }
}
