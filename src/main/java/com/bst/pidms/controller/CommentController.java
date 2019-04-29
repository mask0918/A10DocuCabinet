package com.bst.pidms.controller;

import com.bst.pidms.entity.Comment;
import com.bst.pidms.entity.OwnFile;
import com.bst.pidms.enums.opEnum;
import com.bst.pidms.esmapper.EsCommentMapper;
import com.bst.pidms.service.CommentService;
import com.bst.pidms.service.HistoryService;
import com.bst.pidms.service.OwnFileService;
import com.bst.pidms.utils.SessionUtil;
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

    @Autowired
    HistoryService historyService;

    @Autowired
    OwnFileService ownFileService;

    @RequestMapping(value = "addcomment", method = RequestMethod.POST)
    public Map<String, Object> addComment(@RequestParam("id") Integer id, @RequestParam("content") String content) {
        Map<String, Object> map = new HashMap<>();
        Integer userId = SessionUtil.getInstance().getIdNumber();
        if (userId == -1) {
            map.put("success", false);
            return map;
        }
        Comment comment = new Comment();
        comment.setFileId(id);
        comment.setContent(content);
        comment.setTime(System.currentTimeMillis());
        commentService.addComment(comment);
        esCommentMapper.save(comment);
        map.put("success", true);
        StringBuffer sb = new StringBuffer();
        sb.append(opEnum.ADD_COMMENT.getName());
        sb.append("\"" + ownFileService.getFileById(id).getName() + "\" 的评论 ");
        sb.append("\"" + content + "\"");
        historyService.addHistory(System.currentTimeMillis(), sb.toString(), userId);
        return map;
    }

    @RequestMapping(value = "delcomment", method = RequestMethod.POST)
    public Map<String, Object> delComment(@RequestParam("cid") Integer id) {
        Map<String, Object> map = new HashMap<>();
        Integer userId = SessionUtil.getInstance().getIdNumber();
        if (userId == -1) {
            map.put("success", false);
            return map;
        }
        commentService.delComment(id);
        esCommentMapper.deleteById(id);
        map.put("success", true);
        StringBuffer sb = new StringBuffer();
        sb.append(opEnum.DELETE_COMMENT.getName());
        sb.append(commentService.getCommentById(id).getContent());
        historyService.addHistory(System.currentTimeMillis(), sb.toString(), userId);
        return map;
    }

}
