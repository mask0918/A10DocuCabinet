package com.bst.pidms.controller;

import com.bst.pidms.entity.Comment;
import com.bst.pidms.entity.OwnFile;
import com.bst.pidms.enums.FileType;
import com.bst.pidms.service.CommentService;
import com.bst.pidms.service.OwnFileService;
import com.bst.pidms.utils.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author: BST
 * @Date: 2019/4/21 21:00
 */
@RestController
public class TimeController {
    @Autowired
    OwnFileService ownFileService;

    @Autowired
    CommentService commentService;

    @RequestMapping(value = "gettimeline", method = RequestMethod.GET)
    public List<String> getTimeBook() {
        Integer userId = SessionUtil.getInstance().getIdNumber();
        if (userId == -1) return null;
        return ownFileService.getTimeline(userId);
    }

    @RequestMapping(value = "gettimefile", method = RequestMethod.GET)
    public Map<String, Object> getTimeBookFile(@RequestParam("time") String time) {
        Integer userId = SessionUtil.getInstance().getIdNumber();
        if (userId == -1) return null;
        Map<String, Object> map = new HashMap<>();
        List<OwnFile> list = ownFileService.getFileByTimeline(userId, time);
        for (OwnFile ownFile : list) {
            List<Comment> comments = commentService.getComments(ownFile.getId());
            ownFile.setComments(comments);
        }
        Map<Integer, List<OwnFile>> collect = list.stream().collect(Collectors.groupingBy(OwnFile::getCategory));
        collect.forEach((k, v) -> map.put(FileType.fileEnum(k).name(), v));
        return map;
    }

}
