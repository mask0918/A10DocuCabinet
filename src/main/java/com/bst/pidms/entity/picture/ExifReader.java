package com.bst.pidms.entity.picture;

import com.bst.pidms.entity.OwnFile;
import com.bst.pidms.utils.MapUtils;
import com.drew.imaging.ImageMetadataReader;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.MetadataReader;
import com.drew.metadata.Tag;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author: BST
 * @Date: 2019/4/6 14:53
 */
public class ExifReader {

    public static PicInfo readEXIF(File file) throws Exception {
        PicInfo picInfo = new PicInfo();
        Metadata metadata = ImageMetadataReader.readMetadata(file);
        String latitude = null, longitude = null;

        for (Directory directory : metadata.getDirectories()) {
            for (Tag tag : directory.getTags()) {
                if (tag.getTagName().equals("GPS Latitude")) {
                    latitude = tag.getDescription();
                    continue;
                }
                if (tag.getTagName().equals("GPS Longitude")) {
                    longitude = tag.getDescription();
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
//                    ownFile.setServerTime(date.getTime());
                    continue;
                }
            }
        }
        if (longitude != null) {
            String address = MapUtils.getAddress(longitude, latitude);
            picInfo.setLocation(address);
        }
        return picInfo;

    }
}