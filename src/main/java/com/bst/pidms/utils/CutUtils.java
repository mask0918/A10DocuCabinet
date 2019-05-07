package com.bst.pidms.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: BST
 * @Date: 2019/5/2 11:02
 */
public class CutUtils {
    private static final String ffmpegApp = "Z:\\ffmpeg-20190220-7e4d3db-win64-static\\bin\\ffmpeg.exe";

    public static void main(String[] args) throws IOException {
    }

    public static void videoCut(String src, String des, Integer start, Integer end) throws IOException {
        List<String> command = new ArrayList<>();
        command.add(ffmpegApp);
        command.add("-ss");
        command.add(String.valueOf(start));
        command.add("-t");
        command.add(String.valueOf(end - start));
        command.add("-i");
        command.add(src);
        command.add("-c:v");
        command.add("libx264");
        command.add("-c:a");
        command.add("aac");
        command.add("-strict");
        command.add("experimental");
        command.add("-b:a");
        command.add("98k");
        command.add(des);
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
    }

    public static void audioCut(String src, Integer start, Integer end) throws IOException {
        String des = "Z:\\ffmpeg-20190220-7e4d3db-win64-static\\bin\\1.mp4";
        List<String> command = new ArrayList<>();
        command.add("-i");
        command.add(src);
        command.add(ffmpegApp);
        command.add("-ss");
        command.add(String.valueOf(start));
        command.add("-to");
        command.add(String.valueOf(end));
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
            br.close();
        }
        if (inputStreamReader != null) {
            inputStreamReader.close();
        }
        if (errorStream != null) {
            errorStream.close();
        }
    }

}
