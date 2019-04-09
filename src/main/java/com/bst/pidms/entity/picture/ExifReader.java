package com.bst.pidms.entity.picture;

import com.bst.pidms.utils.MapUtils;
import com.drew.imaging.ImageMetadataReader;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author: BST
 * @Date: 2019/4/6 14:53
 */
public class ExifReader {

//    public static void main(String[] args) {
//        PicInfo picInfo = new PicInfo();
//        MultipartFile file = null;
//        file.get
//    }

    public static void main(String[] args) throws Exception {
        readEXIF(new PicInfo());
    }

    public static void readEXIF(PicInfo picInfo) throws Exception {
        File jpegFile = new File("C:\\Users\\BST\\Desktop\\cloud.ico");
//        Metadata metadata = JpegMetadataReader.readMetadata(jpegFile);
        Metadata metadata = ImageMetadataReader.readMetadata(jpegFile);


        for (Directory directory : metadata.getDirectories()) {
            for (Tag tag : directory.getTags()) {
                System.out.println(tag);
                if (tag.getTagName().equals("GPS Latitude")) {
                    picInfo.setGpsLatitude(tag.getDescription());
                    continue;
                }
                if (tag.getTagName().equals("GPS Longitude")) {
                    picInfo.setGpsLongitude(tag.getDescription());
                    continue;
                }
                if (tag.getTagName().equals("Image Height")) {
                    picInfo.setHeight(Integer.valueOf(tag.getDescription().replace(" pixels", "")));
                    continue;
                }
                if (tag.getTagName().equals("Image Width")) {
                    picInfo.setWidth(Integer.valueOf(tag.getDescription().replace(" pixels", "")));
                    continue;
                }
                if (tag.getTagName().equals("Model")) {
                    picInfo.setModal(tag.getDescription());
                    continue;
                }
                if (tag.getTagName().equals("Aperture Value")) {
                    picInfo.setAperture(tag.getDescription());
                    continue;
                }
                if (tag.getTagName().equals("Date/Time")) {
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss");
                    Date date = simpleDateFormat.parse(tag.getDescription());
                    picInfo.setCreateTime(date.getTime());
                    continue;
                }
            }
        }
        System.out.println(picInfo);
        String address = MapUtils.getAddress(picInfo.getGpsLongitude(), picInfo.getGpsLatitude());
        System.out.println(address);



    }
}