package com.bst.pidms.controller;

import com.bst.pidms.entity.Comment;
import com.bst.pidms.enums.FileType;
import com.bst.pidms.entity.OwnFile;
import com.bst.pidms.esmapper.EsCommentMapper;
import com.bst.pidms.service.*;
import com.bst.pidms.utils.RedisUtils;
import com.google.common.base.Joiner;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author: BST
 * @Date: 2019/4/3 14:50
 */
@Slf4j
@RestController
public class FileController {

    @Autowired
    RedisUtils redisUtils;

    @Autowired
    OwnFileService ownFileService;

    @Autowired
    TimelineService timelineService;

    @Autowired
    CommentService commentService;

    @Autowired
    BindLabelFileService bindLabelFileService;

    @Autowired
    LabelService labelService;


    // 上传文件
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @Transactional
    public Map<String, Boolean> aaa(@RequestParam("filelocation") Integer catalogId, @RequestParam("uploadfile") MultipartFile file, @RequestParam("tag") String tags, HttpSession session) throws Exception {

        Map<String, Boolean> map = new HashMap<>();

        Object user = session.getAttribute("loginUser");
        user = "zzz";

        if (user == null || file.getSize() == 0) {
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

        OwnFile ownFile = new OwnFile();
        ownFile.setName(fileName);
        ownFile.setSize(file.getSize());
        // 设置修改时间
        ownFile.setServerTime(System.currentTimeMillis());
        // 设置上传时间(创作时间)
        ownFile.setUploadTime(System.currentTimeMillis());
        // 设置目录id
        ownFile.setCatalogId(catalogId);
        // 设置用户id
        ownFile.setUserId(1);
        // 设置文件url
        String category = FileType.mFileTypes.get(suffix) != null ? FileType.mFileTypes.get(suffix) : "OTHER";
        ownFile.setUrl(user.toString() + "/" + category + "/" + fileName);
        // 设置文件类型
        ownFile.setCategory(FileType.valueOf(category).getValue());
        if (tags != null)
            // 设置用户自定义标签
            ownFile.setTag(tags);
        // 同步添加到数据库
        ownFileService.addFile(ownFile);
        log.info("First add successfully ...");

        // 异步分析
        switch (category) {
            case "DOCUMENT":
                ownFileService.getDocumentResults(ownFile, toFile);
                break;
            case "IMAGE":
                ownFileService.getImageResults(ownFile, toFile);
            case "AUDIO":
                // TODO: 2019/4/18
                break;
            case "VIDEO":
                ownFileService.getVideoResults(ownFile, toFile);
                break;
            case "OTHER":
                ownFileService.getOtherResults(ownFile, toFile);
        }
        if (category != "IMAGE") {
            String s = ownFile.sortByMonth();
            Integer id = timelineService.addTimelineIfNotExist(s);
            timelineService.addBindTimelineFile(ownFile.getId(), id);
        }
        map.put("success", true);
        return map;
    }

    /**
     * 获取所有文件信息
     *
     * @return
     */
    @RequestMapping(value = "fileinfo", method = RequestMethod.GET)
    public List<OwnFile> getAll() {
        List<OwnFile> all = ownFileService.getAll();
        return all;
    }

    /**
     * 获取指定文件信息
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "fileinfo/{id}", method = RequestMethod.GET)
    public OwnFile getInfo(@PathVariable Integer id) {
        Map<String, Object> map = new HashMap<>();
        OwnFile file = ownFileService.getFileById(id);
        List<Comment> comments = commentService.getComments(id);
        file.setComments(comments);
        return file;
//        map.put("file", file);
//        map.put("comments", comments);
//        return map;
    }

    /**
     * 获取智能归档 文件信息
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "filefiling/{id}", method = RequestMethod.GET)
    public Map<String, List<OwnFile>> getFilingInfo(@PathVariable Integer id) {
        Map<String, List<OwnFile>> map = new HashMap<>();
        List<OwnFile> filingInfo = ownFileService.getFilingInfo(id);
        Map<Integer, List<OwnFile>> collect = filingInfo.stream().collect(Collectors.groupingBy(OwnFile::getCategory));
        collect.forEach((k, v) -> map.put(FileType.fileEnum(k).name(), v));
        return map;
    }

    /**
     * 获取所有图片信息
     *
     * @return
     */
    @RequestMapping(value = "imageinfo", method = RequestMethod.GET)
    public List<OwnFile> getAllImage() {
        List<OwnFile> all = ownFileService.getCategory(FileType.IMAGE.getValue());
        for (OwnFile ownFile : all) {
            List<Comment> comments = commentService.getComments(ownFile.getId());
            ownFile.setComments(comments);
        }
        return all;
    }

    /**
     * 获取所有文档信息
     *
     * @return
     */
    @RequestMapping(value = "docuinfo", method = RequestMethod.GET)
    public List<OwnFile> getAllDocu() {
        List<OwnFile> all = ownFileService.getCategory(FileType.DOCUMENT.getValue());
        for (OwnFile ownFile : all) {
            List<Comment> comments = commentService.getComments(ownFile.getId());
            ownFile.setComments(comments);
        }
        return all;
    }

    /**
     * 获取所有视频信息
     *
     * @return
     */
    @RequestMapping(value = "videoinfo", method = RequestMethod.GET)
    public List<OwnFile> getAllVideo() {
        List<OwnFile> all = ownFileService.getCategory(FileType.VIDEO.getValue());
        for (OwnFile ownFile : all) {
            List<Comment> comments = commentService.getComments(ownFile.getId());
            ownFile.setComments(comments);
        }
        return all;
    }

    /**
     * 获取所有音频信息
     *
     * @return
     */
    @RequestMapping(value = "audioinfo", method = RequestMethod.GET)
    public List<OwnFile> getAllAudio() {
        List<OwnFile> all = ownFileService.getCategory(FileType.AUDIO.getValue());
        for (OwnFile ownFile : all) {
            List<Comment> comments = commentService.getComments(ownFile.getId());
            ownFile.setComments(comments);
        }
        return all;
    }

    /**
     * 获取所有其他文件信息
     *
     * @return
     */
    @RequestMapping(value = "otherinfo", method = RequestMethod.GET)
    public List<OwnFile> getAllOther() {
        List<OwnFile> all = ownFileService.getCategory(FileType.OTHER.getValue());
        for (OwnFile ownFile : all) {
            List<Comment> comments = commentService.getComments(ownFile.getId());
            ownFile.setComments(comments);
        }
        return all;
    }

    /**
     * 图片时光轴
     *
     * @return
     */
    @RequestMapping(value = "sortimage", method = RequestMethod.GET)
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
    public Map<String, Boolean> setCollection(@RequestParam("id") Integer id, @RequestParam("collect") Integer collect) {
        Map<String, Boolean> map = new HashMap<>();
        ownFileService.setCollect(id, collect ^ 1);
        map.put("success", true);
        return map;
    }

    @RequestMapping(value = "deletefile", method = RequestMethod.DELETE)
    public Map<String, Object> delFile(@RequestParam("id") Integer id) {
        Map<String, Object> map = new HashMap<>();
        ownFileService.deleteFileById(id);
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
    public Map<String, Boolean> setAttention(@RequestParam("id") Integer id, @RequestParam("attention") Integer attention) {
        Map<String, Boolean> map = new HashMap<>();
        ownFileService.setAttention(id, attention ^ 1);
        map.put("success", true);
        return map;
    }

    /**
     * 预览文件
     *
     * @param name
     * @return
     */
    @RequestMapping(value = "preview/{name}", method = RequestMethod.GET)
    public String mmp3(@PathVariable String name) {
        if (name.endsWith("docx") || name.endsWith("doc") || name.endsWith("xls") || name.endsWith("xlsx") || name.endsWith("ppt") || name.endsWith("pptx"))
            name = name.substring(0, name.lastIndexOf(".") + 1).concat("pdf");
        return "testpreview/zzz/" + name;
    }

    @RequestMapping(value = "addtag", method = RequestMethod.POST)
    public Map<String, Object> addTag(@RequestParam("id") Integer id, @RequestParam("name") String name) {
        Map<String, Object> map = new HashMap<>();
        OwnFile fileById = ownFileService.getFileById(id);
        String[] split = fileById.getTag().split(" ");
        String[] split1 = name.split(" ");
        StringBuffer sb = new StringBuffer();
        for (String s : split) {
            sb.append(s).append(" ");
        }
        for (String s : split1) {
            sb.append(s).append(" ");
        }
        fileById.setTag(sb.toString());
        ownFileService.updateFile(fileById);
        map.put("success", true);
        return map;
    }

    @RequestMapping(value = "deltag", method = RequestMethod.POST)
    public Map<String, Object> delTag(@RequestParam("id") Integer id, @RequestParam("name") String name) {
        log.info("name :{}", name);
        Map<String, Object> map = new HashMap<>();
        OwnFile fileById = ownFileService.getFileById(id);
        String[] split = fileById.getTag().split(" ");
        StringBuffer sb = new StringBuffer();
        for (String s : split) {
            log.info("split :{}", s);
            if (s.equals(name)) {
                log.info("!!!!!!!!!!!!!!!");
                continue;
            }
            sb.append(s).append(" ");
        }
        fileById.setTag(sb.toString());
        ownFileService.updateFile(fileById);
        map.put("success", true);
        return map;
    }
}
