package com.bst.pidms.enums;

import java.util.HashMap;

/**
 * @Author: BST
 * @Date: 2019/4/9 13:40
 */
public enum FileType {
    DOCUMENT(1), IMAGE(2), AUDIO(3), VIDEO(4), OTHER(5);
    int value;

    FileType(int value) {
        this.value = value;
    }


    public int getValue() {
        return value;
    }

    public static final HashMap<String, String> mFileTypes = new HashMap<String, String>();

    public static FileType fileEnum(int code) {
        for (FileType fileEnum : FileType.values()) {
            if (code == fileEnum.getValue()) {
                return fileEnum;
            }
        }
        return null;
    }

    static {
        // DOCUMENT
        mFileTypes.put("pdf", "DOCUMENT");
        mFileTypes.put("txt", "DOCUMENT");
        mFileTypes.put("doc", "DOCUMENT");
        mFileTypes.put("docx", "DOCUMENT");
        mFileTypes.put("xls", "DOCUMENT");
        mFileTypes.put("xlsx", "DOCUMENT");
        mFileTypes.put("ppt", "DOCUMENT");
        mFileTypes.put("pptx", "DOCUMENT");
        mFileTypes.put("xml", "DOCUMENT");
        mFileTypes.put("md", "DOCUMENT");
        // IMAGE
        mFileTypes.put("jpg", "IMAGE");
        mFileTypes.put("jpeg", "IMAGE");
        mFileTypes.put("jpe", "IMAGE");
        mFileTypes.put("jfif", "IMAGE");
        mFileTypes.put("png", "IMAGE");
        mFileTypes.put("bmp", "IMAGE");
        mFileTypes.put("gif", "IMAGE");
        mFileTypes.put("bmp", "IMAGE");
        mFileTypes.put("ico", "IMAGE");
        mFileTypes.put("webp", "IMAGE");
        mFileTypes.put("tga", "IMAGE");
        mFileTypes.put("pcx", "IMAGE");
        mFileTypes.put("tif", "IMAGE");
        mFileTypes.put("exif", "IMAGE");
        mFileTypes.put("fpx", "IMAGE");
        mFileTypes.put("svg", "IMAGE");
        mFileTypes.put("psd", "IMAGE");
        mFileTypes.put("cdr", "IMAGE");
        mFileTypes.put("pcd", "IMAGE");
        mFileTypes.put("ai", "IMAGE");
        // AUDIO
        mFileTypes.put("wma", "AUDIO");
        mFileTypes.put("mp3", "AUDIO");
        mFileTypes.put("wav", "AUDIO");
        mFileTypes.put("flac", "AUDIO");
        mFileTypes.put("midi", "AUDIO");
        mFileTypes.put("ape", "AUDIO");
        // VIDEO
        mFileTypes.put("avi", "VIDEO");
        mFileTypes.put("mp4", "VIDEO");
        mFileTypes.put("wmv", "VIDEO");
        mFileTypes.put("flv", "VIDEO");
        mFileTypes.put("mpg", "VIDEO");
        mFileTypes.put("mov", "VIDEO");
        mFileTypes.put("m4v", "VIDEO");
        mFileTypes.put("dat", "VIDEO");
        mFileTypes.put("rmvb", "VIDEO");
        mFileTypes.put("3gp", "VIDEO");
        mFileTypes.put("mts", "VIDEO");

//        mFileTypes.put("41433130", "dwg"); // CAD
//        mFileTypes.put("7B5C727466", "rtf"); // 日记本
//        mFileTypes.put("3C3F786D6C", "xml");
//        mFileTypes.put("68746D6C3E", "html");
//        mFileTypes.put("44656C69766572792D646174653A", "eml"); // 邮件
//        mFileTypes.put("D0CF11E0", "doc");
//        mFileTypes.put("D0CF11E0", "xls");//excel2003版本文件
//        mFileTypes.put("5374616E64617264204A", "mdb");
//        mFileTypes.put("252150532D41646F6265", "ps");
//        mFileTypes.put("255044462D312E", "pdf");
//        mFileTypes.put("504B0304", "docx");
//        mFileTypes.put("504B0304", "xlsx");//excel2007以上版本文件
//        mFileTypes.put("52617221", "rar");
//        mFileTypes.put("57415645", "wav");
//        mFileTypes.put("41564920", "avi");
//        mFileTypes.put("2E524D46", "rm");
//        mFileTypes.put("000001BA", "mpg");
//        mFileTypes.put("000001B3", "mpg");
//        mFileTypes.put("6D6F6F76", "mov");
//        mFileTypes.put("3026B2758E66CF11", "asf");
//        mFileTypes.put("4D546864", "mid");
//        mFileTypes.put("1F8B08", "gz");
    }

}
