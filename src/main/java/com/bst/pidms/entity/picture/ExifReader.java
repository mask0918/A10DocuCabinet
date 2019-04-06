package com.bst.pidms.entity.picture;

import com.drew.imaging.jpeg.JpegMetadataReader;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

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


    public static void readEXIF(PicInfo picInfo) throws Exception {
        File jpegFile = new File("C:\\Users\\BST\\Desktop\\3213.JPG");
        Metadata metadata = JpegMetadataReader.readMetadata(jpegFile);
        for (Directory directory : metadata.getDirectories()) {
            for (Tag tag : directory.getTags()) {
//                if (tag.getTagName().equals("File Name")) picInfo.setName(tag.getDescription());
//                if(tag.getTagName().equals("File Size")) picInfo.setSize(tag.getDescription());

            }

        }
    }
}