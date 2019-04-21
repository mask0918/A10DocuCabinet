package com.bst.pidms.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @Author: BST
 * @Date: 2019/4/20 14:39
 */
public class ThumbUtils {

    public static final String ffmpegApp = "Z:\\ffmpeg-20190220-7e4d3db-win64-static\\bin\\ffmpeg.exe";

    public static void getThumb(String videoFilename, String thumbFilename, int width,
                                int height, int hour, int min, double sec) throws IOException,
            InterruptedException {
        ProcessBuilder processBuilder = new ProcessBuilder(ffmpegApp, "-y",
                "-i", videoFilename, "-vframes", "1", "-ss", hour + ":" + min
                + ":" + sec, "-f", "mjpeg", "-s", width + "*" + height,
                "-an", thumbFilename);

        Process process = processBuilder.start();

        InputStream stderr = process.getErrorStream();
        InputStreamReader isr = new InputStreamReader(stderr);
        BufferedReader br = new BufferedReader(isr);
        String line;
        while ((line = br.readLine()) != null) ;
        process.waitFor();
        if (br != null)
            br.close();
        if (isr != null)
            isr.close();
        if (stderr != null)
            stderr.close();
    }

}
