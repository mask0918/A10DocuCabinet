package com.bst.pidms.controller;

import com.alibaba.fastjson.JSONObject;
import com.bst.pidms.entity.FileType;
import com.bst.pidms.entity.OwnFile;
import com.bst.pidms.entity.docu.DocuInfo;
import com.bst.pidms.entity.picture.ExifReader;
import com.bst.pidms.entity.picture.GoogleVision;
import com.bst.pidms.entity.picture.PicInfo;
import com.bst.pidms.entity.reader.OfficeReader;
import com.bst.pidms.entity.video.*;
import com.bst.pidms.service.OwnFileService;
import com.google.common.base.Joiner;
import com.sun.org.apache.bcel.internal.generic.RETURN;
import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jodconverter.DocumentConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author: BST
 * @Date: 2019/4/3 14:50
 */
@Controller
public class FileController {

    static final String host = "http://fvfzrq.natappfree.cc";

    @Resource
    DocumentConverter documentConverter;

    @Autowired
    OwnFileService ownFileService;

    // 上传文件
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    @Transactional
    public Map<String, Boolean> aaa(@RequestParam("uploadfile") MultipartFile file, @RequestParam("tag") String tags, HttpSession session) throws Exception {

        Map<String, Boolean> map = new HashMap<>();

        Object user = session.getAttribute("loginUser");
        user = "zzz";

        if (user == null || file.getSize() == 0) {
            map.put("success", false);
            return map;
        }
        if (file.getSize() == 0) {
            map.put("success", false);
            return map;
        }
        String fileName = file.getOriginalFilename().toLowerCase();
        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
        // 创建用户文件夹
        File dir = new File("D:\\InsightPIDMS\\" + user.toString());
        if (!dir.exists()) dir.mkdir();
        File dir1 = new File(dir.getAbsolutePath() + "\\" + FileType.mFileTypes.get(suffix));
        if (!dir1.exists()) dir1.mkdir();
        // 上传文件
        File toFile = new File(dir1.getAbsolutePath() + "\\" + fileName);
        toFile.createNewFile();
        file.transferTo(toFile);
        String prefix = fileName.substring(0, fileName.lastIndexOf("."));

        OwnFile ownFile = new OwnFile();
        ownFile.setName(fileName);
        ownFile.setSize(file.getSize());
        // 设置修改时间
        ownFile.setServerTime(System.currentTimeMillis());
        // 设置上传时间(创作时间)
        ownFile.setUploadTime(System.currentTimeMillis());
        // 设置目录id
        ownFile.setCatalogId(0);
        // 设置用户id
        ownFile.setUserId(1);
        if (tags != null)
            // 设置用户自定义标签
            ownFile.setTag(tags.replace(" ", ","));

        String category = FileType.mFileTypes.get(suffix);
        if (category != null) {
            switch (category) {
                case "DOCUMENT":
                    ownFile.setCategory(FileType.DOCUMENT.getValue());
                    String info = OfficeReader.getInfo(toFile.getAbsolutePath(), suffix);
                    String keywords = getKeywords(host + "/keywords", info, prefix);
                    ownFile.setKeyword(keywords);
                    DocuInfo docuInfo = new DocuInfo();
                    docuInfo.setInfo(info);
                    if (suffix != "pdf") {
                        documentConverter.convert(toFile).to(new File(toFile.getParent() + "\\DOCUMENT\\" + prefix + ".pdf")).execute();
                    }
                    // 词云图片
                    docuInfo.setWordCloudUrl("http://ppxlrdgsm.bkt.clouddn.com/" + prefix + ".png");
                    ownFile.setInfo(JSONObject.toJSONString(docuInfo));
                    break;
                case "IMAGE":
                    ownFile.setCategory(FileType.IMAGE.getValue());
                    PicInfo picInfo = ExifReader.readEXIF(toFile);
                    if (picInfo.getCreateTime() != null) ownFile.setServerTime(picInfo.getCreateTime());
                    Map<String, Object> fileMap = GoogleVision.Vision_Java(toFile);
                    picInfo.setColors((List<PicInfo.Color>) fileMap.get("colors"));
                    ownFile.setKeyword(Joiner.on(",").join((List<String>) fileMap.get("labels")));
                    ownFile.setInfo(JSONObject.toJSONString(picInfo));
                    break;
                case "AUDIO":
                    ownFile.setCategory(FileType.AUDIO.getValue());
                    break;
                case "VIDEO":
                    ownFile.setCategory(FileType.VIDEO.getValue());
                    VideoInfo videoInfo = VideoReader.getInfo(toFile.getAbsolutePath(), "Z:\\ffmpeg-20190220-7e4d3db-win64-static\\bin\\ffmpeg.exe");
                    VideoResults videoResults = GoogleVideo.Video_Java(toFile.getAbsolutePath());
                    videoInfo.setVideoResults(videoResults);
                    Set set = new HashSet();
                    Set themeSet = new HashSet();
                    for (VideoLabel videoLabel : videoResults.getVideoLabels()) {
                        String videolabel = videoLabel.getVideolabel();
                        if (!set.contains(videolabel)) {
                            set.add(videolabel);
                            themeSet.add(videolabel);
                        }
                    }
                    themeSet.add(suffix);
                    for (ShotLabel shotLabel : videoResults.getShotLabels()) {
                        String shotlabel = shotLabel.getShotlabel();
                        if (!set.contains(shotlabel)) set.add(shotlabel);
                    }
                    ownFile.setKeyword(Joiner.on(",").join(themeSet));
                    getKeywords(host + "/wordcloud", Joiner.on(" ").join(set), prefix + "_wordcloud");
                    // 词云图片
                    videoInfo.setWordCloudUrl("http://ppxlrdgsm.bkt.clouddn.com/" + prefix + "_wordcloud.png");
                    ownFile.setInfo(JSONObject.toJSONString(videoInfo));
                    break;
            }
        } else ownFile.setCategory(FileType.OTHER.getValue());
        ownFile.setUrl(user.toString() + "/" + category + "/" + fileName);
        ownFileService.addFile(ownFile);
        map.put("success", true);
        return map;
    }

    public static String getKeywords(String url, String val, String title) {
        HttpClient httpclient = HttpClientBuilder.create().build();
        try {
            HttpPost httppost = new HttpPost(url + "?content=" + URLEncoder.encode(val, "UTF-8") + "&title=" + URLEncoder.encode(title, "UTF-8"));
            httppost.addHeader("Content-type", "application/json; charset=utf-8");
            HttpResponse response = httpclient.execute(httppost);

            int statusCode = response.getStatusLine().getStatusCode();

            if (statusCode == HttpStatus.SC_OK) {
                HttpEntity resEntity = response.getEntity();
                String result = EntityUtils.toString(resEntity);
                EntityUtils.consume(resEntity);
                return result;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Error";
    }

    /**
     * 预览文件
     *
     * @param name
     * @return
     */
    @RequestMapping(value = "preview/{name}", method = RequestMethod.GET)
    @ResponseBody
    public String mmp3(@PathVariable String name) {
        if (name.endsWith("docx") || name.endsWith("doc") || name.endsWith("xls") || name.endsWith("xlsx") || name.endsWith("ppt") || name.endsWith("pptx"))
            name = name.substring(0, name.lastIndexOf(".") + 1).concat("pdf");
        return "testpreview/zzz/" + name;
    }

    @RequestMapping(value = "fileinfo/{id}", method = RequestMethod.GET)
    @ResponseBody
    public OwnFile getInfo(@PathVariable Integer id) {
        OwnFile file = ownFileService.getFileById(id);
        return file;
    }

    @RequestMapping(value = "fileinfo", method = RequestMethod.GET)
    @ResponseBody
    public List<OwnFile> getAll() {
        List<OwnFile> all = ownFileService.getAll();
        return all;
    }

    @RequestMapping(value = "imageinfo", method = RequestMethod.GET)
    @ResponseBody
    public List<OwnFile> getAllImage() {
        List<OwnFile> all = ownFileService.getCategory(FileType.IMAGE.getValue());
        return all;
    }

    @RequestMapping(value = "docuinfo", method = RequestMethod.GET)
    @ResponseBody
    public List<OwnFile> getAllDocu() {
        List<OwnFile> all = ownFileService.getCategory(FileType.DOCUMENT.getValue());
        return all;
    }

    @RequestMapping(value = "videoinfo", method = RequestMethod.GET)
    @ResponseBody
    public List<OwnFile> getAllVideo() {
        List<OwnFile> all = ownFileService.getCategory(FileType.VIDEO.getValue());
        return all;
    }

    /**
     * 图片时光轴
     *
     * @return
     */
    @RequestMapping(value = "sortimage", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, List<OwnFile>> mmp() {
        List<OwnFile> ownFiles = ownFileService.getCategory(FileType.IMAGE.getValue());
        Map<String, List<OwnFile>> collect = ownFiles.stream().collect(Collectors.groupingBy(OwnFile::sortByMonth));
        return collect;
    }

    /**
     * 添加/取消收藏
     *
     * @param id
     * @param collect
     * @return
     */
    @RequestMapping(value = "collect", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Boolean> setCollection(@RequestParam("id") Integer id, @RequestParam("collect") Integer collect) {
        Map<String, Boolean> map = new HashMap<>();
        ownFileService.setCollect(id, collect ^ 1);
        map.put("success", true);
        return map;
    }

    /**
     * 添加/取消关注
     *
     * @param id
     * @param attention
     * @return
     */
    @RequestMapping(value = "attention", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Boolean> setAttention(@RequestParam("id") Integer id, @RequestParam("attention") Integer attention) {
        Map<String, Boolean> map = new HashMap<>();
        ownFileService.setCollect(id, attention ^ 1);
        map.put("success", true);
        return map;
    }
}
