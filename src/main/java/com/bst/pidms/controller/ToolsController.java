package com.bst.pidms.controller;

import com.bst.pidms.entity.OwnFile;
import com.bst.pidms.entity.User;
import com.bst.pidms.enums.FileType;
import com.bst.pidms.service.OwnFileService;
import com.bst.pidms.utils.CutUtils;
import com.bst.pidms.utils.ImageUtils;
import com.bst.pidms.utils.PdfUtils;
import com.bst.pidms.utils.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.*;

/**
 * @Author: BST
 * @Date: 2019/5/2 22:19
 */
@RestController
public class ToolsController {

    @Autowired
    OwnFileService ownFileService;

    private static final String header = "D:/InsightPIDMS/";

    @PostMapping("imgmerge")
    public Map<String, Object> mergeImg(@RequestParam("urls") String[] urls) throws Exception {
        User user = SessionUtil.getInstance().getUser();
        if (user == null) return null;
        Map<String, Object> map = new HashMap<>();
        String suffix = user.getUsername() + "/IMAGE";
        List<BufferedImage> imgs = new ArrayList<>();
        for (String url : urls) {
            BufferedImage img = ImageIO.read(new File(header + url));
            imgs.add(img);
        }
        BufferedImage joinedImg = ImageUtils.joinBufferedImages(imgs);
        String fileName = UUID.randomUUID().toString() + ".jpg";
        File file = new File(header + suffix + File.separator + fileName);
        boolean success = ImageIO.write(joinedImg, "jpg", file);
        if (success) {
            map.put("success", true);
            map.put("file", fileName);
        } else map.put("success", false);
        OwnFile ownFile = new OwnFile();
        ownFile.setName(fileName);
        ownFile.setSize(file.length());
        // 设置修改时间
        ownFile.setServerTime(System.currentTimeMillis());
        // 设置上传时间(创作时间)
        ownFile.setUploadTime(System.currentTimeMillis());
        // 设置目录id
        ownFile.setCatalogId(48);
        // 设置用户id
        ownFile.setUserId(user.getId());
        // 设置文件url
        ownFile.setUrl(user.getUsername() + "/IMAGE/" + fileName);
        // 设置文件类型
        ownFile.setCategory(FileType.IMAGE.getValue());
        // 同步添加到数据库
        ownFileService.addFile(ownFile);
        ownFileService.getImageResults(ownFile, file);
        return map;
    }

    @PostMapping("imgalbum")
    public Map<String, Object> albumImg(@RequestParam("urls") String[] urls) throws Exception {
        User user = SessionUtil.getInstance().getUser();
        if (user == null) return null;
        Map<String, Object> map = new HashMap<>();
        String suffix = user.getUsername() + "/VIDEO";
        String fileName = UUID.randomUUID().toString() + ".mp4";
        for (int i = 0; i < urls.length; i++) urls[i] = header + urls[i];
        ImageUtils.images2Video(urls, header + suffix + File.separator + fileName);
        File file = new File(header + suffix + File.separator + fileName);
        map.put("success", true);
        map.put("file", fileName);
        OwnFile ownFile = new OwnFile();
        ownFile.setName(fileName);
        ownFile.setSize(file.length());
        // 设置修改时间
        ownFile.setServerTime(System.currentTimeMillis());
        // 设置上传时间(创作时间)
        ownFile.setUploadTime(System.currentTimeMillis());
        // 设置目录id
        ownFile.setCatalogId(48);
        // 设置用户id
        ownFile.setUserId(user.getId());
        // 设置文件url
        ownFile.setUrl(user.getUsername() + "/VIDEO/" + fileName);
        // 设置文件类型
        ownFile.setCategory(FileType.VIDEO.getValue());
        // 同步添加到数据库
        ownFileService.addFile(ownFile);
        ownFileService.getVideoResults(ownFile, file);
        return map;
    }

    @PostMapping("videocut")
    public Map<String, Object> cutVideo(@RequestParam("url") String url, @RequestParam("start") Integer s, @RequestParam("end") Integer e) throws Exception {
        User user = SessionUtil.getInstance().getUser();
        if (user == null) return null;
        Map<String, Object> map = new HashMap<>();
        String suffix = user.getUsername() + "/VIDEO";
        String fileName = UUID.randomUUID().toString() + ".mp4";
        File file = new File(header + suffix + File.separator + fileName);
        CutUtils.videoCut(header + url, file.getAbsolutePath(), s, e);
        map.put("success", true);
        map.put("file", fileName);
        OwnFile ownFile = new OwnFile();
        ownFile.setName(fileName);
        ownFile.setSize(file.length());
        // 设置修改时间
        ownFile.setServerTime(System.currentTimeMillis());
        // 设置上传时间(创作时间)
        ownFile.setUploadTime(System.currentTimeMillis());
        // 设置目录id
        ownFile.setCatalogId(48);
        // 设置用户id
        ownFile.setUserId(user.getId());
        // 设置文件url
        ownFile.setUrl(user.getUsername() + "/VIDEO/" + fileName);
        // 设置文件类型
        ownFile.setCategory(FileType.VIDEO.getValue());
        // 同步添加到数据库
        ownFileService.addFile(ownFile);
        ownFileService.getVideoResults(ownFile, file);
        return map;
    }

    public Map<String, Object> cutAudio() {
        // TODO: 2019/5/3
        return null;
    }

    @PostMapping("pdfmerge")
    public Map<String, Object> mergePdf(@RequestParam("urls") String[] url) throws Exception {
        User user = SessionUtil.getInstance().getUser();
        if (user == null) return null;
        Map<String, Object> map = new HashMap<>();
        String suffix = user.getUsername() + "/DOCUMENT";
        String fileName = UUID.randomUUID().toString() + ".pdf";
        File file = new File(header + suffix + File.separator + fileName);
        for (int i = 0; i < url.length; i++) url[i] = header + url[i];
        PdfUtils.mergePdf(url, file.getAbsolutePath());
        map.put("success", true);
        map.put("file", fileName);
        OwnFile ownFile = new OwnFile();
        ownFile.setName(fileName);
        ownFile.setSize(file.length());
        // 设置修改时间
        ownFile.setServerTime(System.currentTimeMillis());
        // 设置上传时间(创作时间)
        ownFile.setUploadTime(System.currentTimeMillis());
        // 设置目录id
        ownFile.setCatalogId(48);
        // 设置用户id
        ownFile.setUserId(user.getId());
        // 设置文件url
        ownFile.setUrl(user.getUsername() + "/DOCUMENT/" + fileName);
        // 设置文件类型
        ownFile.setCategory(FileType.DOCUMENT.getValue());
        // 同步添加到数据库
        ownFileService.addFile(ownFile);
        ownFileService.getDocumentResults(ownFile, file);
        return map;
    }

    @PostMapping("pdfsplit")
    public Map<String, Object> splitPdf(@RequestParam("url") String url, @RequestParam("indexs") Integer[] indexs) throws Exception {
        User user = SessionUtil.getInstance().getUser();
        if (user == null) return null;
        Map<String, Object> map = new HashMap<>();
        String suffix = user.getUsername() + "/DOCUMENT";
        String fileName = UUID.randomUUID().toString() + ".pdf";
        File file = new File(header + suffix + File.separator + fileName);
        PdfUtils.splitPdf(indexs, header + url, file.getAbsolutePath());
        map.put("success", true);
        map.put("file", fileName);
        OwnFile ownFile = new OwnFile();
        ownFile.setName(fileName);
        ownFile.setSize(file.length());
        // 设置修改时间
        ownFile.setServerTime(System.currentTimeMillis());
        // 设置上传时间(创作时间)
        ownFile.setUploadTime(System.currentTimeMillis());
        // 设置目录id
        ownFile.setCatalogId(48);
        // 设置用户id
        ownFile.setUserId(user.getId());
        // 设置文件url
        ownFile.setUrl(user.getUsername() + "/DOCUMENT/" + fileName);
        // 设置文件类型
        ownFile.setCategory(FileType.DOCUMENT.getValue());
        // 同步添加到数据库
        ownFileService.addFile(ownFile);
        ownFileService.getDocumentResults(ownFile, file);
        return map;
    }

    @PostMapping("pdftopng")
    public Map<String, Object> pdf2Png(@RequestParam("url") String url) throws Exception {
        User user = SessionUtil.getInstance().getUser();
        if (user == null) return null;
        Map<String, Object> map = new HashMap<>();
        String suffix = user.getUsername() + "/IMAGE";
        String fileName = UUID.randomUUID().toString() + ".png";
        File file = new File(header + suffix + File.separator + fileName);
        PdfUtils.pdf2Png(header + url, file.getAbsolutePath());
        map.put("success", true);
        map.put("file", fileName);
        OwnFile ownFile = new OwnFile();
        ownFile.setName(fileName);
        ownFile.setSize(file.length());
        // 设置修改时间
        ownFile.setServerTime(System.currentTimeMillis());
        // 设置上传时间(创作时间)
        ownFile.setUploadTime(System.currentTimeMillis());
        // 设置目录id
        ownFile.setCatalogId(48);
        // 设置用户id
        ownFile.setUserId(user.getId());
        // 设置文件url
        ownFile.setUrl(user.getUsername() + "/IMAGE/" + fileName);
        // 设置文件类型
        ownFile.setCategory(FileType.IMAGE.getValue());
        // 同步添加到数据库
        ownFileService.addFile(ownFile);
        ownFileService.getImageResults(ownFile, file);
        return map;
    }
}
