package com.bst.pidms.entity.picture;

import com.bst.pidms.utils.TranslateUtils;
import com.google.cloud.vision.v1p3beta1.*;
import com.google.protobuf.ByteString;
import lombok.extern.slf4j.Slf4j;

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
@Slf4j
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
        Feature feat4 = Feature.newBuilder().setType(Feature.Type.TEXT_DETECTION).build();
        AnnotateImageRequest request =
                AnnotateImageRequest.newBuilder().addFeatures(feat).addFeatures(feat2).addFeatures(feat4).setImage(img).build();
//        AnnotateImageRequest request1 = AnnotateImageRequest.newBuilder().addFeatures(feat2).addFeatures(feat3).addFeatures(feat4).setImage(img).build();
        requests.add(request);

        try (ImageAnnotatorClient client = ImageAnnotatorClient.create()) {
            BatchAnnotateImagesResponse response = client.batchAnnotateImages(requests);
            List<AnnotateImageResponse> responses = response.getResponsesList();

            List<PicInfo.Color> colorList = new ArrayList<>();
            List<String> picLabel = new ArrayList<>();
            List<String> objTemp = new ArrayList<>();
            StringBuffer description = new StringBuffer();

            for (AnnotateImageResponse res : responses) {
                if (res.hasError()) {
                    System.out.printf("Error: %s\n", res.getError().getMessage());
                    return null;
                }
                // For full list of available annotations, see http://g.co/cloud/vision/docs
                DominantColorsAnnotation colors = res.getImagePropertiesAnnotation().getDominantColors();

                double sum = colors.getColorsList().stream().mapToDouble(ColorInfo::getScore).sum();

                for (ColorInfo color : colors.getColorsList()) {
                    float red = color.getColor().getRed();
                    float green = color.getColor().getGreen();
                    float blue = color.getColor().getBlue();
                    colorList.add(new PicInfo.Color(color.getScore() / sum, (int) red, (int) green, (int) blue));
                }

                for (EntityAnnotation annotation : res.getLabelAnnotationsList()) {
                    if (annotation.getScore() < 0.6) continue;
                    if (picLabel.size() > 8) break;
                    String s = TranslateUtils.YoudaoTranslate(annotation.getDescription());
                    picLabel.add(s);
                }
                for (LocalizedObjectAnnotation annotation : res.getLocalizedObjectAnnotationsList()) {
                    if (objTemp.contains(annotation.getName())) continue;
                    objTemp.add(annotation.getName());
                    picLabel.add(TranslateUtils.YoudaoTranslate(annotation.getName()));
                }
                for (EntityAnnotation annotation : res.getTextAnnotationsList()) {
                    description.append(annotation.getDescription()).append("\n");
                }
            }
            map.put("ocr", description.toString());
            map.put("colors", colorList);
            map.put("labels", picLabel);
        }
        System.clearProperty("proxyHost");
        System.clearProperty("proxyPort");
        return map;
    }

}
