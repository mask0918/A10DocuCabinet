package com.bst.pidms.utils;

import org.apache.commons.codec.binary.Hex;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Date;

/**
 * @Author: BST
 * @Date: 2019/5/2 12:20
 */
public class HashUtils {
    public static void main(String[] args) {
//        System.out.println(getMD5(new File("C:\\Users\\BST\\Documents\\EV录屏 3.7.1单文件版.zip")));
        Long uploadTime = 1358178501000L;
        Date date = new Date(uploadTime);
        Calendar cal = Calendar.getInstance();
        System.out.println(cal.get(Calendar.MONTH));

    }

    public static String getMD5(File file) {
        FileInputStream fileInputStream = null;
        try {
            MessageDigest MD5 = MessageDigest.getInstance("MD5");
            fileInputStream = new FileInputStream(file);
            byte[] buffer = new byte[8192];
            int length;
            int temp = 0, count = 0;
            while ((length = fileInputStream.read(buffer)) != -1) {
                temp++;
                MessageDigest tmp = MessageDigest.getInstance("MD5");
                MD5.update(buffer, 0, length);
                tmp.update(buffer, 0, length);
                if (temp == 1024) {
                    System.out.println("Temp MD5 " + new String(Hex.encodeHex(tmp.digest())));
                    temp = 0;
                    count++;
                }
                System.out.println("MD5 " + new String(Hex.encodeHex(MD5.digest())));

            }
            System.out.println(count);
            return new String(Hex.encodeHex(MD5.digest()));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
