package com.bst.pidms.utils;

import org.apache.commons.io.FileUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

/**
 * @Author: BST
 * @Date: 2019/4/27 13:46
 */
public class ImageUtils {

    public static final String ffmpegApp = "Z:\\ffmpeg-20190220-7e4d3db-win64-static\\bin\\ffmpeg.exe";

    public static void images2Video(String[] src) throws IOException {
        for (int i = 0; i < src.length; i++) {
            FileUtils.copyFile(new File(src[i]), new File("D:\\InsightPIDMS\\tmp\\" + i + ".jpg"));
        }
        ProcessBuilder processBuilder = new ProcessBuilder(ffmpegApp, "-y",
                "-r", "1", "-i", "D:\\InsightPIDMS\\tmp\\%d.jpg", "-vcodec", "libx264", "D:\\InsightPIDMS\\tmp\\21.mp4");
        Process process = processBuilder.start();
        // 清空该文件夹下的所有文件
        FileUtils.cleanDirectory(new File("D:\\InsightPIDMS\\tmp"));
    }


    public static void main(String args[]) throws Exception {
        // 图片制作影集
//        String[] s = new String[]{"C:\\\\Users\\\\BST\\\\Pictures\\\\1.jpg", "C:\\\\Users\\\\BST\\\\Pictures\\\\2.jpg"};
//        images2Video(s);

        // 图片合并
//        String filename = "C:\\Users\\BST\\Pictures\\";
//        try {
//            BufferedImage img1 = ImageIO.read(new File(filename + "1.jpg"));
//            BufferedImage img2 = ImageIO.read(new File(filename + "2.jpg"));
//            BufferedImage img3 = ImageIO.read(new File(filename + "3213.jpg"));
//            BufferedImage[] imgs = new BufferedImage[]{img1, img2, img3};
//            BufferedImage joinedImg = joinBufferedImages(imgs);
//            boolean success = ImageIO.write(joinedImg, "jpg", new File(filename + "joined1.jpg"));
//            System.out.println("saved success? " + success);
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
    }


    /**
     * join two BufferedImage
     * you can add a orientation parameter to control direction
     * you can use a array to join more BufferedImage
     */

    public static BufferedImage joinBufferedImage(BufferedImage img1, BufferedImage img2) {
        //do some calculate first
        int offset = 5;
//        int wid = img1.getWidth() + img2.getWidth() + offset;
//        int height = Math.max(img1.getHeight(), img2.getHeight()) + offset;
        int wid = Math.max(img1.getWidth(), img2.getWidth()) + offset;
        int height = img1.getHeight() + img2.getHeight() + offset;
        //create a new buffer and draw two image into the new image
        BufferedImage newImage = new BufferedImage(wid, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = newImage.createGraphics();
        Color oldColor = g2.getColor();
        //fill background
        g2.setPaint(Color.WHITE);
        g2.fillRect(0, 0, wid, height);
        //draw image
        g2.setColor(oldColor);
        g2.drawImage(img1, null, 0, 0);
//        g2.drawImage(img2, null, img1.getWidth() + offset, 0);
        g2.drawImage(img2, null, (wid - img2.getWidth()) / 2, img1.getHeight() + offset);
        g2.dispose();
        return newImage;
    }

    /**
     * 图片合并(垂直)
     *
     * @param imgs
     * @return
     */
    public static BufferedImage joinBufferedImages(BufferedImage[] imgs) {
        int w = 0, h_all = 0;
        for (BufferedImage img : imgs) {
            if (img.getWidth() > w) w = img.getWidth();
            h_all += img.getHeight();
        }
        int offset = 5, temp_y = offset;
        int wid = w + offset * 2;
        int height = h_all + offset * (imgs.length + 1);
        BufferedImage newImage = new BufferedImage(wid, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = newImage.createGraphics();
        Color oldColor = g2.getColor();
        //fill background
        g2.setPaint(Color.WHITE);
        g2.fillRect(0, 0, wid + offset * 2, height);
        //draw image
        g2.setColor(oldColor);
        for (BufferedImage img : imgs) {
            g2.drawImage(img, null, (wid - img.getWidth()) / 2, temp_y);
            temp_y += img.getHeight() + offset;
        }
        g2.dispose();
        return newImage;
    }
}
