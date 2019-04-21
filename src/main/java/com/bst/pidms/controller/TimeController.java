package com.bst.pidms.controller;

import com.bst.pidms.entity.Comment;
import com.bst.pidms.entity.OwnFile;
import com.bst.pidms.entity.Timeline;
import com.bst.pidms.enums.FileType;
import com.bst.pidms.service.CommentService;
import com.bst.pidms.service.TimelineService;
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
    TimelineService timelineService;

    @Autowired
    CommentService commentService;

    @RequestMapping(value = "gettimeline", method = RequestMethod.GET)
    public List<Timeline> getTimeBook() {
        return timelineService.getAll();
    }

    @RequestMapping(value = "gettimefile", method = RequestMethod.GET)
    public Map<String, Object> getTimeBookFile(@RequestParam("id") Integer timeId) {
        Map<String, Object> map = new HashMap<>();
        List<OwnFile> list = timelineService.getFilesById(timeId);
        for (OwnFile ownFile : list) {
            List<Comment> comments = commentService.getComments(ownFile.getId());
            ownFile.setComments(comments);
        }
        Map<Integer, List<OwnFile>> collect = list.stream().collect(Collectors.groupingBy(OwnFile::getCategory));
        collect.forEach((k, v) -> map.put(FileType.fileEnum(k).name(), v));
        return map;
    }

}
