package com.bst.pidms.service;

import com.bst.pidms.entity.Comment;

import java.util.List;

/**
 * @Author: BST
 * @Date: 2019/4/21 16:02
 */
public interface CommentService {
    public void addComment(Comment comment);

    public void delComment(Integer id);

    public List<Comment> getComments(Integer id);
}
