package com.bst.pidms.controller;

import com.bst.pidms.entity.Comment;
import com.bst.pidms.esmapper.EsCommentMapper;
import com.bst.pidms.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: BST
 * @Date: 2019/4/21 16:04
 */
@RestController
public class CommentController {
    @Autowired
    CommentService commentService;

    @Autowired
    EsCommentMapper esCommentMapper;

    @RequestMapping(value = "addcomment", method = RequestMethod.POST)
    public Map<String, Object> addComment(@RequestParam("id") Integer id, @RequestParam("content") String content) {
        Map<String, Object> map = new HashMap<>();
        Comment comment = new Comment();
        comment.setFileId(id);
        comment.setContent(content);
        comment.setTime(System.currentTimeMillis());
        commentService.addComment(comment);
        esCommentMapper.save(comment);
        map.put("success", true);
        return map;
    }

    @RequestMapping(value = "delcomment", method = RequestMethod.POST)
    public Map<String, Object> delComment(@RequestParam("cid") Integer id) {
        Map<String, Object> map = new HashMap<>();
        commentService.delComment(id);
        esCommentMapper.deleteById(id);
        map.put("success", true);
        return map;
    }

}
