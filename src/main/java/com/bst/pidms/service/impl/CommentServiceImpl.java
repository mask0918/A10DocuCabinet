package com.bst.pidms.service.impl;

import com.bst.pidms.dao.CommentMapper;
import com.bst.pidms.entity.Comment;
import com.bst.pidms.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: BST
 * @Date: 2019/4/21 16:03
 */
@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    CommentMapper commentMapper;

    @Override
    public void addComment(Comment comment) {
        commentMapper.insert(comment);
    }

    @Override
    public void delComment(Integer id) {
        commentMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<Comment> getComments(Integer id) {
        return commentMapper.selectByFileId(id);
    }
}
