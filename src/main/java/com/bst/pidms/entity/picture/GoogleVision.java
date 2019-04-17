package com.bst.pidms.entity.picture;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bst.pidms.utils.TranslateUtils;
import com.google.cloud.vision.v1.*;
import com.google.cloud.vision.v1.Feature;
import com.google.protobuf.ByteString;

import java.io.File;
import java.io.FileInputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: BST
 * @Date: 2019/4/10 11:55
 */
public class GoogleVision {

    private PrintStream out;

    public static void main(String[] args) throws Exception {

    }

    public static Map<String, Object> Vision_Java(File filePath) throws Exception {

        Map<String, Object> map = new HashMap<>();

        String proxyHost = "127.0.0.1";
        String proxyPort = "1080";

        System.setProperty("proxyHost", proxyHost);
        System.setProperty("proxyPort", proxyPort);

        List<AnnotateImageRequest> requests = new ArrayList<>();

        ByteString imgBytes = ByteString.readFrom(new FileInputStream(filePath));

        Image img = Image.newBuilder().setContent(imgBytes).build();
        Feature feat = Feature.newBuilder().setType(Feature.Type.IMAGE_PROPERTIES).build();
        Feature feat2 = Feature.newBuilder().setType(Feature.Type.LABEL_DETECTION).build();
        AnnotateImageRequest request =
                AnnotateImageRequest.newBuilder().addFeatures(feat).setImage(img).build();
        AnnotateImageRequest request1 = AnnotateImageRequest.newBuilder().addFeatures(feat2).setImage(img).build();
        requests.add(request);
        requests.add(request1);

        try (ImageAnnotatorClient client = ImageAnnotatorClient.create()) {
            BatchAnnotateImagesResponse response = client.batchAnnotateImages(requests);
            List<AnnotateImageResponse> responses = response.getResponsesList();

            List<PicInfo.Color> colorList = new ArrayList<>();
            List<String> picLabel = new ArrayList<>();

            for (AnnotateImageResponse res : responses) {
                if (res.hasError()) {
                    System.out.printf("Error: %s\n", res.getError().getMessage());
                    return null;
                }

                // For full list of available annotations, see http://g.co/cloud/vision/docs
                DominantColorsAnnotation colors = res.getImagePropertiesAnnotation().getDominantColors();

                double sum = colors.getColorsList().stream().mapToDouble(ColorInfo::getScore).sum();


                for (ColorInfo color : colors.getColorsList()) {
                    double v = color.getScore() / sum;
                    float red = color.getColor().getRed();
                    float green = color.getColor().getGreen();
                    float blue = color.getColor().getBlue();
                    colorList.add(new PicInfo.Color(color.getScore() / sum, (int) red, (int) green, (int) blue));
                }

                for (EntityAnnotation annotation : res.getLabelAnnotationsList()) {
                    if (annotation.getScore() < 0.7) continue;
                    if (picLabel.size() > 8) break;
                    String s = TranslateUtils.YoudaoTranslate(annotation.getDescription());
                    picLabel.add(s);
                }
            }
            map.put("colors", colorList);
            map.put("labels", picLabel);
        }
        System.clearProperty("proxyHost");
        System.clearProperty("proxyPort");
        return map;
    }

}
