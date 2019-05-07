package com.bst.pidms.utils;

import org.apache.commons.io.FileUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @Author: BST
 * @Date: 2019/4/27 13:46
 */
public class ImageUtils {
    private static final String ffmpegApp = "Z:\\ffmpeg-20190220-7e4d3db-win64-static\\bin\\ffmpeg.exe";

    /**
     * 图片影集
     *
     * @param src
     * @throws IOException
     */
    public static void images2Video(String[] src, String des) throws IOException {
        int length = src.length;
        List<String> command = new ArrayList<>();
        command.add(ffmpegApp);
        StringBuffer sb = new StringBuffer("\"");
        for (int i = 0; i < length; i++) {
            command.add("-loop");
            command.add("1");
            command.add("-t");
            command.add("2.5");
            command.add("-i");
            command.add(src[i]);
            sb.append("[").append(i).append(":v]scale=960:540:force_original_aspect_ratio=decrease,pad=960:540:(ow-iw)/2:(oh-ih)/2:white,setsar=1,fade=t=in:st=0:d=0.8,fade=t=out:st=2:d=0.5[v").append(i).append("];");
        }
        command.add("-filter_complex");
        for (int i = 0; i < length; i++) sb.append("[v").append(i).append("]");
        sb.append("concat=n=").append(length).append(":v=1:a=0,format=yuv420p[v]");
        sb.append("\"");
        command.add(sb.toString());
        command.add("-map");
        command.add("\"[v]\"");
        String output = ffmpegApp.substring(0, ffmpegApp.lastIndexOf(File.separator) + 1) + UUID.randomUUID().toString() + ".mp4";
        command.add(output);
        System.out.println(command);
        ProcessBuilder processBuilder = new ProcessBuilder(command);
        Process process = null;
        try {
            process = processBuilder.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 使用这种方式会在瞬间大量消耗CPU和内存等系统资源，所以这里我们需要对流进行处理
        InputStream errorStream = process.getErrorStream();
        InputStreamReader inputStreamReader = new InputStreamReader(errorStream);
        BufferedReader br = new BufferedReader(inputStreamReader);
        while (br.readLine() != null) {
        }
        if (br != null) {
            br.close();
        }
        if (inputStreamReader != null) {
            inputStreamReader.close();
        }
        if (errorStream != null) {
            errorStream.close();
        }
        images2VideoFilter(output, des);
    }

    /**
     * 滤镜效果
     *
     * @param output
     * @param des
     * @throws IOException
     */
    public static void images2VideoFilter(String output, String des) throws IOException {
        List<String> command = new ArrayList<>();
        command.add(ffmpegApp);
        command.add("-i");
        command.add(output);
        command.add("-vf");
        command.add("\"split[a][b];[a]scale=1280:720,boxblur=10:5[1];[b]scale=iw:ih[2];[1][2]overlay=(W-w)/2:y=(H-h)/2\"");
        command.add(des);
        ProcessBuilder processBuilder = new ProcessBuilder(command);
        Process process = null;
        try {
            process = processBuilder.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 使用这种方式会在瞬间大量消耗CPU和内存等系统资源，所以这里我们需要对流进行处理
        InputStream errorStream = process.getErrorStream();
        InputStreamReader inputStreamReader = new InputStreamReader(errorStream);
        BufferedReader br = new BufferedReader(inputStreamReader);
        while (br.readLine() != null) {
        }
        if (br != null) {
            File file = new File(output);
            System.out.println("output : " + output);
            boolean delete = file.delete();
            System.out.println("success true ?" + delete);
            br.close();
        }
        if (inputStreamReader != null) {
            inputStreamReader.close();
        }
        if (errorStream != null) {
            errorStream.close();
        }
    }

    public static void main(String args[]) throws Exception {
//         图片制作影集
//        String[] s = new String[]{"C:\\\\Users\\\\BST\\\\Pictures\\\\1.jpg", "C:\\\\Users\\\\BST\\\\Pictures\\\\2.jpg", "C:\\\\Users\\\\BST\\\\Pictures\\\\0.jpg"};
//        images2Video(s,a);

//         图片合并
//        String filename = "C:\\Users\\BST\\Pictures\\";
//        try {
//            BufferedImage img1 = ImageIO.read(new File(filename + "1.jpg"));
//            BufferedImage img2 = ImageIO.read(new File(filename + "2.jpg"));
//            BufferedImage img3 = ImageIO.read(new File(filename + "3213.jpg"));
//            List<BufferedImage> imgs = new ArrayList<BufferedImage>();
//            imgs.add(img1);
//            imgs.add(img2);
//            imgs.add(img3);
//            BufferedImage joinedImg = joinBufferedImages(imgs);
//            boolean success = ImageIO.write(joinedImg, "jpg", new File(filename + "joined1.jpg"));
//            System.out.println("saved success? " + success);
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
    }

    /**
     * 图片合并(垂直)
     *
     * @param imgs
     * @return
     */
    public static BufferedImage joinBufferedImages(List<BufferedImage> imgs) {
        int w = 0, h_all = 0;
        for (BufferedImage img : imgs) {
            if (img.getWidth() > w) w = img.getWidth();
            h_all += img.getHeight();
        }
        int offset = 5, temp_y = offset;
        int wid = w + offset * 2;
        int height = h_all + offset * (imgs.size() + 1);
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
