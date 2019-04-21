package com.bst.pidms.dao;

import com.bst.pidms.entity.Comment;

import java.util.List;

public interface CommentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Comment record);

    Comment selectByPrimaryKey(Integer id);

    List<Comment> selectAll();

    int updateByPrimaryKey(Comment record);

    List<Comment> selectByFileId(Integer fileId);
}