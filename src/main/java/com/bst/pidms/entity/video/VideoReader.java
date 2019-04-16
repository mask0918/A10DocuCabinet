package com.bst.pidms.entity.video;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author: BST
 * @Date: 2019/4/15 15:46
 */
public class VideoReader {
    public static VideoInfo getInfo(String video_path, String ffmpeg_path) throws Exception {
        List<String> commands = new java.util.ArrayList<String>();
        commands.add(ffmpeg_path);
        commands.add("-i");
        commands.add(video_path);
        try {
            ProcessBuilder builder = new ProcessBuilder();
            builder.command(commands);
            final Process p = builder.start();
            //从输入流中读取视频信息
            BufferedReader br = new BufferedReader(new InputStreamReader(p.getErrorStream()));
            StringBuffer sb = new StringBuffer();
            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            br.close();

            //从视频信息中解析时长
            String regexDuration = "Duration: (.*?), ";
            String regexDuration1 = ", bitrate: (\\d*) kb\\/s";
            String regexVideo = "Video: (.*?), (.*?), (\\d*)x(\\d*)[,\\s]";
            String regexAudio = "Audio: (\\w*), (\\d*) Hz";
            Pattern pattern = Pattern.compile(regexDuration);
            Matcher m = pattern.matcher(sb.toString());
            Pattern pattern_1 = Pattern.compile(regexDuration1);
            Matcher m_1 = pattern_1.matcher(sb.toString());
            Pattern pattern1 = Pattern.compile(regexVideo);
            Matcher m1 = pattern1.matcher(sb.toString());
            Pattern pattern2 = Pattern.compile(regexAudio);
            Matcher m2 = pattern2.matcher(sb.toString());

            VideoInfo videoInfo = new VideoInfo();
            if (m.find()) {
                int time = getTimelen(m.group(1));
                videoInfo.setDuration(time);
            }
            if (m_1.find()) {
                videoInfo.setBitrate(m_1.group(1) + " kb/s");
            }
            if (m1.find()) {
                videoInfo.setCode(m1.group(1));
                videoInfo.setWidth(Integer.parseInt(m1.group(3)));
                videoInfo.setHeight(Integer.parseInt(m1.group(4)));
            }
            return videoInfo;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //格式:"00:00:10.68"
    private static int getTimelen(String timelen) {
        int min = 0;
        String strs[] = timelen.split(":");
        if (strs[0].compareTo("0") > 0) {
            min += Integer.valueOf(strs[0]) * 60 * 60;//秒
        }
        if (strs[1].compareTo("0") > 0) {
            min += Integer.valueOf(strs[1]) * 60;
        }
        if (strs[2].compareTo("0") > 0) {
            min += Math.round(Float.valueOf(strs[2]));
        }
        return min;
    }
}
